#include "inc/led_chars.h"

//******************************************************
// Variables & external functions
//******************************************************

extern void StartCritical(void);
extern void EndCritical(void);
extern volatile char Color;
extern volatile bool AlertRowUpdate;
extern volatile bool FaceUpdate;
extern volatile uint8_t refresh;

static char faceColor;
static bool faceEnable = false;


volatile uint8_t Row = 0;
volatile bool mix_color_flag = false;
volatile uint8_t faceNum;


GPIO_PORT *PortB = (GPIO_PORT *)PORTB;
GPIO_PORT *PortC = (GPIO_PORT *)PORTC;
GPIO_PORT *PortF = (GPIO_PORT *)PORTF;


// Loop up table for FACES
static const uint8_t LED_FACES[3][8] =
{
		{  // normal
			  0xFF                                     , // ROW 7
        0xFF                                     , // ROW 6
        LED_2_ON & LED_3_ON & LED_4_ON & LED_5_ON, // ROW 5
        0xFF                                     , // ROW 4
        0xFF                                     , // ROW 3
        0xFF                                     , // ROW 2
				LED_0_ON & LED_2_ON & LED_5_ON & LED_7_ON, // ROW 1
				LED_1_ON &                       LED_6_ON, // ROW 0
		},
		{  // depressed
				0xFF                                     , // ROW 7
				LED_1_ON &                       LED_6_ON, // ROW 6
				LED_2_ON &                       LED_5_ON, // ROW 5
				LED_3_ON &                       LED_4_ON, // ROW 4
				0xFF                                     , // ROW 3
				0xFF                                     , // ROW 2 
				LED_0_ON & LED_2_ON & LED_5_ON & LED_7_ON, // ROW 1
				LED_1_ON &                       LED_6_ON, // ROW 0
		},
		{  // smile		
        0xFF                                     , // ROW 7
				LED_3_ON &                       LED_4_ON, // ROW 6 
				LED_2_ON &                       LED_5_ON, // ROW 5
				LED_1_ON &                       LED_6_ON, // ROW 4
				0xFF                                     , // ROW 3
				0xFF                                     , // ROW 2
				LED_0_ON & LED_2_ON & LED_5_ON & LED_7_ON, // ROW 1
				LED_1_ON &                       LED_6_ON, // ROW 0
		},
};	

//*****************************************************************************
// MasterDisplayUpdate function: called by Master Device to update LCD
//*****************************************************************************
void MasterDisplayUpdate(void)
{
	uint8_t lcdData; 
	StartCritical(); 

	
	if(AlertRowUpdate)
	{
		if (Color == 'O') {
			// Disable Output /OE active low
			PortF->Data |= (1 << 4);
		}
		else {
			if (Row != 0 && !(Row == 1 && refresh == 0)){
					// Enable all the latches 
					PortC->Data = 0xF0; 
					
					// Reset the value in all latches. active low
					PortB->Data = 0xFF; 
				
					// Enable Row latch PC7 active high
					PortC->Data = (1 << 7); 
				
					// Write the data to the row latch. active low
					PortB->Data = ~(1 << Row);
					
					// Disable the row latch and clear the data in Port B
					PortC->Data = 0x00;
					PortB->Data = 0xFF;
				
					// Always falling mixed color bars; but first enable red 
					PortC->Data = (1 << 4); 
				
					// Write the active LED into the selected color latch 
					getLCDRow(Row, &lcdData, true, 0); 
					PortB->Data = lcdData; 
				
					// Disable all the latches and clear the data in PortB
					PortC->Data &= 0x00; 
					PortB->Data = 0xFF;
						
					// To mix colors, enable blue and green and set mix_color_flag.
					mix_color_flag = true;
					PortC->Data = (1 << 6) | (1<<5);
					
					// Write the active LED into the selected color latch 
					getLCDRow(Row, &lcdData, true, 0);  
					PortB->Data = lcdData; 
					
					// Disable all the latches and clear the data in PortB
					PortC->Data &= 0x00; 
					PortB->Data = 0xFF;
			
					// Enable Output 
					PortF->Data &= ~(1 << 4); 
					}
			else{
					// Enable all the latches 
					PortC->Data = 0xF0; 
				
					// Reset the value in all latches. active low
					PortB->Data = 0xFF; 
				
					// Enable Row latch PC7 active high
					PortC->Data = (1 << 7); 
				
					// Write the data to the row latch. active low
					PortB->Data = ~(1 << Row);
					
					// Disable the row latch and clear the data in Port B
					PortC->Data = 0x00;
					PortB->Data = 0xFF;
			
					// Decide which color latch to enable 
					if (Color == 'R') PortC->Data = (1 << 4); 
					else if(Color == 'G') PortC->Data = (1 << 5); 
					else if(Color == 'B') PortC->Data = (1 << 6); 
					
					// Write the active LED into the selected color latch 
					getLCDRow(Row, &lcdData, true, 0);  
					PortB->Data = lcdData; 
				
					// Disable all the latches and clear the data in PortB
					PortC->Data &= 0x00; 
					PortB->Data = 0xFF;
					
					if (Color == 'R') {
						// To mix colors, enable blue and green, and set mix_color_flag.
						mix_color_flag = true;
						PortC->Data = (1 << 6) | (1<<5);
						
						// Write the active LED into the selected color latch 
						getLCDRow(Row, &lcdData, true, 0);  
						PortB->Data = lcdData; 
						
						// Disable all the latches and clear the data in PortB
						PortC->Data &= 0x00; 
						PortB->Data = 0xFF;
					}
					
					// Enable Output 
					PortF->Data &= ~(1 << 4); 
			}
				// update row wrap
				Row = (Row+1) % 8; 
		}
			// clear AlertRowUpdate
			AlertRowUpdate = false; 
	}
	EndCritical();
	return;
}
//*****************************************************************************
// faceUpdate function: used to update faces on LCD
//*****************************************************************************
void faceUpdate()
{
	static uint8_t row = 0;
	StartCritical();
	
	if(faceEnable)
	{
			// Enable all the latches 
			PortC->Data = 0xF0; 
			
			// Reset the value in all latches. active low
			PortB->Data = 0xFF; 
		
			// Enable Row latch PC7 active high
			PortC->Data = (1 << 7); 
		
			// Write the data to the row latch. active low
			PortB->Data = ~(1 << row);
			
			// Disable the row latch and clear the data in Port B
			PortC->Data = 0x00;
			PortB->Data = 0xFF;
		
			// Decide which color latch to enable 
			if (faceColor == 'R') PortC->Data = (1 << 4); 
			else if(faceColor == 'G') PortC->Data = (1 << 5); 
			else if(faceColor == 'B') PortC->Data = (1 << 6); 
		
			// Write the active LED into the selected color latch 
			PortB->Data = LED_FACES[faceNum][row]; 
		
			// Disable all the latches and clear the data in PortB
			PortC->Data &= 0x00; 
			PortB->Data = 0xFF;
		
			// Enable Output 
			PortF->Data &= ~(1 << 4); 
		
			// update row wrap
			row = (row+1) % 8; 
			// clear AlertRowUpdate
			FaceUpdate = false; 
	}
	EndCritical();
	return;
}
//*****************************************************************************
// faceOn function: used to turn on faces.
//*****************************************************************************
void faceOn(uint8_t face)
{
		StartCritical();
		// update faceNum var
		faceNum = face;
	
		// determine which face to turn on.
		if(face == NORMAL){
			faceColor = 'R';
		}
		else if(face == DEPRESS){
			faceColor = 'B';
		}
		else{
			faceColor = 'G';
		}
		// Enable the face
		faceEnable = true;
			
		EndCritical();
}

//*****************************************************************************
// faceOff function: used to turn off faces
//*****************************************************************************
void faceOff(uint8_t face)
{
		faceEnable = false;
}
void SlaveDisplayUpdate(uint8_t number)
{
	uint8_t lcdData; 
	static uint8_t ROW = 0;
	
	StartCritical();
	
	if(AlertRowUpdate)
	{
		if (Color == 'O') {
			// Disable Output /OE active low
			PortF->Data |= (1 << 4);
		}
		else {
					// Enable all the latches 
					PortC->Data = 0xF0; 
					
					// Reset the value in all latches. active low
					PortB->Data = 0xFF; 
				
					// Enable Row latch PC7 active high
					PortC->Data = (1 << 7); 
				
					// Write the data to the row latch. active low
					PortB->Data = ~(1 << ROW);
					
					// Disable the row latch and clear the data in Port B
					PortC->Data = 0x00;
					PortB->Data = 0xFF;
				
					// Always falling mixed color bars; but first enable red 
					PortC->Data = (1 << 4); 
				
					// Write the active LED into the selected color latch 
					getLCDRow(ROW, &lcdData, false, number); 
					PortB->Data = lcdData; 
				
					// Disable all the latches and clear the data in PortB
					PortC->Data &= 0x00; 
					PortB->Data = 0xFF;
						
					// Add color green.
					PortC->Data |= (1 << 6); 
					
					// Write the active LED into the selected color latch 
					getLCDRow_ColorMix(ROW, &lcdData, 'G', number);
					PortB->Data = lcdData; 
					
					// Disable all the latches and clear the data in PortB
					PortC->Data &= 0x00; 
					PortB->Data = 0xFF;
			
					// Add color blue.
					PortC->Data |= (1<<5);
					
					// Write the active LED into the selected color latch 
					getLCDRow_ColorMix(ROW, &lcdData, 'B', number);
					PortB->Data = lcdData; 
					
					// Disable all the latches and clear the data in PortB
					PortC->Data &= 0x00; 
					PortB->Data = 0xFF;
			
					// Enable Output 
					PortF->Data &= ~(1 << 4); 

		}
			// clear AlertRowUpdate
			AlertRowUpdate = false; 
			// update row variable
			ROW = (ROW+1) % 8; 
	}
	EndCritical();
	return;
}

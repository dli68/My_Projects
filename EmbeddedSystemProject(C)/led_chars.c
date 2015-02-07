#include "inc/led_chars.h"

//extern volatile uint16_t RefreshRate;
extern volatile char Color;
extern volatile uint16_t miss_count;
extern volatile uint16_t hit_count;
extern volatile uint16_t wait_count;
extern volatile bool hit;
extern volatile uint8_t NewdutyCycle1;
extern volatile uint8_t NewdutyCycle2;
extern volatile bool mix_color_flag;

static bool scoreEnable;
static uint8_t tens = 0;
static uint8_t ones = 0;

volatile uint8_t current_patten[8] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
volatile uint8_t current_patten_2[8] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
volatile bool gameEnd = false;
volatile uint8_t refresh = 0;

// Look up table for short bars
static const uint8_t LED_SQR[3] =
	{0x03, 0x18, 0xc0};
	
// Look up table for DIGITS 
static const uint8_t LED_LUT[16][8] = 
{
    {   // Zero
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON &            LED_0_ON, // ROW 1
        LED_2_ON &            LED_0_ON, // ROW 2
        LED_2_ON &            LED_0_ON, // ROW 3
        LED_2_ON &            LED_0_ON, // ROW 4
        LED_2_ON &            LED_0_ON, // ROW 5
        LED_2_ON &            LED_0_ON, // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   // One
                              LED_0_ON, // ROW 0
                              LED_0_ON, // ROW 1
                              LED_0_ON, // ROW 2
                              LED_0_ON, // ROW 3
                              LED_0_ON, // ROW 4
                              LED_0_ON, // ROW 5
                              LED_0_ON, // ROW 6
                              LED_0_ON, // ROW 7
    },
    {   // Two
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON                      , // ROW 1
        LED_2_ON                      , // ROW 2
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 3
                              LED_0_ON, // ROW 4
                              LED_0_ON, // ROW 5
                              LED_0_ON, // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   // Three
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
                              LED_0_ON, // ROW 1
                              LED_0_ON, // ROW 2
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 3
                              LED_0_ON, // ROW 4
                              LED_0_ON, // ROW 5
                              LED_0_ON, // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   // Four
                              LED_0_ON, // ROW 0
                              LED_0_ON, // ROW 1
                              LED_0_ON, // ROW 2
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 3
        LED_2_ON &            LED_0_ON, // ROW 4
        LED_2_ON &            LED_0_ON, // ROW 5
        LED_2_ON &            LED_0_ON, // ROW 6
        LED_2_ON &            LED_0_ON, // ROW 7
    },
    {   // Five
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
                              LED_0_ON, // ROW 1
                              LED_0_ON, // ROW 2
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 3
        LED_2_ON                      , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   // Six
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON &            LED_0_ON, // ROW 1
        LED_2_ON &            LED_0_ON, // ROW 2
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 3
        LED_2_ON                      , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
     {   // Seven
                              LED_0_ON, // ROW 0
                              LED_0_ON, // ROW 1
                              LED_0_ON, // ROW 2
                              LED_0_ON, // ROW 3
                              LED_0_ON, // ROW 4
                              LED_0_ON, // ROW 5
                              LED_0_ON, // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
     },
    {   //  Eight
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON &            LED_0_ON, // ROW 1
        LED_2_ON &            LED_0_ON, // ROW 2
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 3
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 4
        LED_2_ON &            LED_0_ON, // ROW 5
        LED_2_ON &            LED_0_ON, // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   //  Nine
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
                              LED_0_ON, // ROW 1
                              LED_0_ON, // ROW 2
                              LED_0_ON, // ROW 3
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 4
        LED_2_ON &            LED_0_ON, // ROW 5
        LED_2_ON &            LED_0_ON, // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   //  10
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON                      , // ROW 1
        LED_2_ON                      , // ROW 2
        LED_2_ON & LED_1_ON           , // ROW 3
        LED_2_ON & LED_1_ON           , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   //  11
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON                      , // ROW 1
        LED_2_ON                      , // ROW 2
        LED_2_ON & LED_1_ON           , // ROW 3
        LED_2_ON & LED_1_ON           , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   //  12
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON                      , // ROW 1
        LED_2_ON                      , // ROW 2
        LED_2_ON & LED_1_ON           , // ROW 3
        LED_2_ON & LED_1_ON           , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   //  13
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON                      , // ROW 1
        LED_2_ON                      , // ROW 2
        LED_2_ON & LED_1_ON           , // ROW 3
        LED_2_ON & LED_1_ON           , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   //  14
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON                      , // ROW 1
        LED_2_ON                      , // ROW 2
        LED_2_ON & LED_1_ON           , // ROW 3
        LED_2_ON & LED_1_ON           , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
    {   //  15
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 0
        LED_2_ON                      , // ROW 1
        LED_2_ON                      , // ROW 2
        LED_2_ON & LED_1_ON           , // ROW 3
        LED_2_ON & LED_1_ON           , // ROW 4
        LED_2_ON                      , // ROW 5
        LED_2_ON                      , // ROW 6
        LED_2_ON & LED_1_ON & LED_0_ON, // ROW 7
    },
};

static uint16_t getSpeed(void);

//*****************************************************************************
// Convert a decimal number to BCD.  
//*****************************************************************************
static bool decimalToBcd(int8_t decimal, uint8_t *tens, uint8_t *ones)
{
    char msg[2];
    if(decimal > 99)
        return false;
    
    sprintf(msg, "%02d", decimal);
    
    *tens = msg[0] - 0x30;
    *ones = msg[1] - 0x30;
    
    return true;
}

//*****************************************************************************
// Generate squares corresponding to row.  
//*****************************************************************************
static uint8_t getSquare(int8_t row)
{
  // according to which level the player is at changes falling speed
	uint32_t time_slot = getSpeed();
	static uint64_t count_time = 0;
	static uint8_t buffer[9];

	
	if (count_time >= time_slot*50){ // reach time_slot
			//clear count_time & enable scoreEnable
			count_time = 0;
			//move everything on the screen downward by 1.
			//start with current_patten.
			buffer[6] = current_patten[6];
			current_patten[6] = current_patten[7];
			buffer[5] = current_patten[5];
			current_patten[5] = buffer[6];
			buffer[4] = current_patten[4];
			current_patten[4] = buffer[5];
			buffer[3] = current_patten[3];
			current_patten[3] = buffer[4];
			buffer[2] = current_patten[2];
			current_patten[2] = buffer[3];
			buffer[1] = current_patten[1];
			current_patten[1] = buffer[2];
			buffer[0] = current_patten[0];
			current_patten[0] = buffer[1];
			//then current_patten_2.
			buffer[6] = current_patten_2[6];
			current_patten_2[6] = current_patten_2[7];
			buffer[5] = current_patten_2[5];
			current_patten_2[5] = buffer[6];
			buffer[4] = current_patten_2[4];
			current_patten_2[4] = buffer[5];
			buffer[3] = current_patten_2[3];
			current_patten_2[3] = buffer[4];
			buffer[2] = current_patten_2[2];
			current_patten_2[2] = buffer[3];
			buffer[1] = current_patten_2[1];
			current_patten_2[1] = buffer[2];
			buffer[0] = current_patten_2[0];
			current_patten_2[0] = buffer[1];
		
			//check if refresh is 0; when 0, a new block should be generated on row 7, otherwise, score should be updated by checking 
		  //the hit flag.
			if(refresh == 0){
				//randomly generates a block
				buffer[7] = LED_SQR[rand() % 3];
				//generates the same block for current_patten_2 at a probability of 1/6.
				if(rand() % 6 == 3){
					buffer[8] = buffer[7];
				}
				else{
					buffer[8] = 0x00;
				}
				
				//update the score
				if(scoreEnable){
					if(hit)	{
						hit_count++;
						//if the block hit is the special block, then add two more points (neeeeeed testtttttttttttttttttttttttttt)
						if(current_patten_2[0] != 0){
								hit_count = hit_count + 2;
						}
						hit = false;
					}
					else{
						if(current_patten[0]!=0x00){
							miss_count++;
						}
					}
				}
				else{
					wait_count++;
					scoreEnable = true;
				}
			}
			else{
				if(Color != 'R'){
					Color = 'R';
				}
			}
			//assign value to row 7 of current_patten
			current_patten[7] = buffer[7];
			//assign value to row 7 of current_patten_2
			current_patten_2[7] = buffer[8];
			//update refresh
			refresh = (refresh + 1) % 2;
	}
	else
	{
		count_time++;
	}
	
	//return the acquired row value.
	//ADC0 will be used to dim color
	if(NewdutyCycle1 < (count_time % 100)){
		return ~0x00;
	}
	else{
		if(!mix_color_flag){
			return ~current_patten[row];
		}
		else{
			mix_color_flag = false;
			return ~current_patten_2[row];
		}	
	}
}

//*****************************************************************************
// getLCDRow function: called by master display updating function to attain row data
//*****************************************************************************
bool getLCDRow(int8_t row, uint8_t *lcdData, bool masterDevice, uint8_t number)
{	
	if(masterDevice){
			*lcdData = getSquare(row);
	}
	else{
		decimalToBcd(number, &tens, &ones);
		*lcdData = (LED_LUT[tens][row]<<4) | (LED_LUT[ones][row] & 0x0F);
	}
	return true;
}
//*****************************************************************************
// getLCDRow_ColorMix function: called by slave display updating function to attain row data
//*****************************************************************************
void getLCDRow_ColorMix(int8_t row, uint8_t *lcdData, char color, uint8_t number){
	//variables
	static uint64_t count_time = 0;
	//return the acquired row value.
	//ADC0 will be used to dim color
	if(color == 'G'){
			if(NewdutyCycle1 < ((count_time % 100)+1)){
					*lcdData = ~0x00;
			}
			else{
					*lcdData = (LED_LUT[tens][row]<<4) | (LED_LUT[ones][row] & 0x0F);
			}
	}
	else{
			if(NewdutyCycle2 > (count_time % 100)){
					*lcdData = ~0x00;
			}
			else{
					*lcdData = (LED_LUT[tens][row]<<4) | (LED_LUT[ones][row] & 0x0F);
			}
	}
	count_time++;
}


//*****************************************************************************
// Generate speed for the game / game plot
//*****************************************************************************
static uint16_t getSpeed(void)
{
	uint16_t total = miss_count + hit_count + wait_count;
	if(total < 10){
		return 180; 
	}
	else if(total < 50){
		scoreEnable = false;
		return 5;
	}
	else if(total < 63){
		return 120;
	}
	else if(total < 103){
		scoreEnable = false;
		return 5;
	}
	else if(total < 120){
		return 80;
	}
	else if(total < 163){
		scoreEnable = false;
		return 5;
	}
	else if(total < 185){
		return 50;
	}
	else if(total < 223){
		scoreEnable = false;
		return 5;
	}
	else if(total < 263){
		return 30;
	}
	else if(total < 303){
		scoreEnable = false;
		return 5;
	}
	else if(total < 350){
		return 20;
	}
	else{
		gameEnd = true;
		return 5;
	}
}























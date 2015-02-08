#include "inc/gpio.h"

//******************************************************
// Variables & external functions
//******************************************************
extern void StartCritical(void);
extern void EndCritical(void);
extern volatile uint8_t current_patten[8];
extern volatile uint8_t current_patten_2[8];
extern volatile bool AlertDebounce;
extern volatile bool gameEnd;

volatile char Color = 'R';
volatile bool hit = false;

static uint8_t countSW2_0;
static uint8_t countSW2_1;
static uint8_t countSW3_0;
static uint8_t countSW3_1;
static uint8_t countSW4_0;
static uint8_t countSW4_1;
static uint8_t countSW5_0;
static uint8_t countSW5_1;

#define REFdebounce 15
//*****************************************************************************
// The ISR sets AlertDebounce to true if the buttons should be examined.  
// If AlertDebounce is false, simply return
//*****************************************************************************
void examineButtons(void)
{
	GPIO_PORT *myPortA = (GPIO_PORT *)PORTA;
  GPIO_PORT *myPortD = (GPIO_PORT *)PORTD;
	
	StartCritical();
		
	if (AlertDebounce) {	
		/* Check button SW300 */
    if ( (myPortA->Data & (1<<6)) == 0 ) // get low
		{
				countSW2_0++; // increment 0s counter
				if ((countSW2_0 == REFdebounce) && (countSW2_1 == 1)) // if one high followed by 15 low
				{
					countSW2_1 = 0; // clear one high flag
					if(current_patten[0] == 0x18){
						Color = 'B';
						hit = true;
					}
					else{
						// If the special block has been pressed wrong, then game over.
						if(current_patten_2[0] != 0){
								gameEnd = true;
						}
						Color = 'G';
						hit = false;
					}
				}
		}
		else // get high
		{
			countSW2_0 = 0; // clear 0s counter
			countSW2_1 = 1; // set one high flag
		}
		
		
		
		/* Check button SW301 */
    if ( (myPortA->Data & (1<<7)) == 0 ) // get low
		{
				countSW3_0++; // increment 0s counter
				if ((countSW3_0 == REFdebounce) && (countSW3_1 == 1)) // if one high followed by 15 low
				{
					countSW3_1 = 0; // clear one high flag
					if(current_patten[0] == 0x03){
						Color = 'B';
						hit = true;
					}
					else{
						// If the special block has been pressed wrong, then game over.
						if(current_patten_2[0] != 0){
								gameEnd = true;
						}
						Color = 'G';
						hit = false;
					}
				}
		}
		else // get high
		{
			countSW3_0 = 0; // clear 0s counter
			countSW3_1 = 1; // set one high flag
		}
		
		/* Check button SW302 */
    if ( (myPortD->Data & (1<<2)) == 0 ) // get low
		{
				countSW4_0++; // increment 0s counter
				if ((countSW4_0 == REFdebounce) && (countSW4_1 == 1)) // if one high followed by 15 low
				{
					countSW4_1 = 0; // clear one high flag
					Color = 'O';
				}
		}
		else // get high
		{
			countSW4_0 = 0; // clear 0s counter
			countSW4_1 = 1; // set one high flag
		}
		
		
		
		/* Check button 303 */
   if ( (myPortD->Data & (1<<3)) == 0 ) // get low
		{
				countSW5_0++; // increment 0s counter
				if ((countSW5_0 == REFdebounce) && (countSW5_1 == 1)) // if one high followed by 15 low
				{
					countSW5_1 = 0; // clear one high flag
					if(current_patten[0] == 0xc0){
						Color = 'B';
						hit = true;
					}
					else{
						// If the special block has been pressed wrong, then game over.
						if(current_patten_2[0] != 0){
								gameEnd = true;
						}
						Color = 'G';
						hit = false;
					}
				}
		}
		else // get high
		{
			countSW5_0 = 0; // clear 0s counter
			countSW5_1 = 1; // set one high flag
		}

		
		// clear alert
		AlertDebounce = false;
	}
  // if AlertDebounce is false, simply return
	EndCritical();
	return;
}

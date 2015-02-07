#include "inc/gpio.h"

//*****************************************************************************
// Initializes all of the GPIO pins used for the LED matrix, push 
// buttons, and the left potentiometer.
//*****************************************************************************
void initializeGpioPins(void)
{
	uint16_t i;
  GPIO_PORT *myPortB = (GPIO_PORT *)PORTB;
	GPIO_PORT *myPortA = (GPIO_PORT *)PORTA;
	GPIO_PORT *myPortF = (GPIO_PORT *)PORTF;
	GPIO_PORT *myPortD = (GPIO_PORT *)PORTD;
	
	/* Config PortB */
	// Turn on the Clock Gating Register
	SYSCTL_RCGC2_R |= SYSCTL_RCGCGPIO_R1;
	i = 1000;
	while (i) { i--; }
	
	// Config PB0-7 as DATA_0-DATA_7 OUTPUTS
	myPortB->DigitalEnable |= 0xFF; // Digital enable
	myPortB->Direction |= 0xFF; // outputs
	
	/* Config PortF */
	// Turn on the Clock Gating Register
	SYSCTL_RCGC2_R |= SYSCTL_RCGCGPIO_R5;
	i = 1000;
	while (i) { i--; }
	
	// Port F has a lock register, so we need to unlock it before making modifications
  GPIO_PORTF_LOCK_R = 0x4C4F434B;
  GPIO_PORTF_CR_R = 0xFF;
	
	// Config PF4 as /OE output
	myPortF->DigitalEnable |= (1<<4); // Digital enable
	myPortF->Direction |= (1<<4); // outputs
	
	/* Config Port A */
	// Turn on the Clock Gating Register
	SYSCTL_RCGC2_R |= SYSCTL_RCGCGPIO_R0;
	i = 1000;
	while (i) { i--; }
	
	// Config PA6-7 as SW2-3
	myPortA->DigitalEnable |= 0xC0; // Digital enable
	myPortA->Direction &= 0x3F; // inputs
	myPortA->PullUpSelect |= 0xC0; // set pullup
	
	/* Config Port D */
	// Turn on the Clock Gating Register
	SYSCTL_RCGC2_R |= SYSCTL_RCGCGPIO_R3;
	i = 1000;
	while (i) { i--; }
	
	// Port D has a lock register, so we need to unlock it before making modifications
  GPIO_PORTD_LOCK_R = 0x4C4F434B;
  GPIO_PORTD_CR_R = 0xFF;
	
	// Config Port D PA2-3 as SW4-5
	myPortD->DigitalEnable |= 0x0C; // Digital enable
	myPortD->Direction &= 0xF3; // inputs
	myPortD->PullUpSelect |= 0x0C; // set pullup
  
	/* Config Port C */
  // Turn on the Clock
  SYSCTL_RCGCGPIO_R |= SYSCTL_RCGCGPIO_R2;
  i = 1000;
	while (i) { i--; }
  
  // Set Digital Enable
  GPIO_PORTC_DEN_R  = 0xFF;
  
  // Set PC7-4 as outputs
  GPIO_PORTC_DIR_R  = 0xF0;
  
  // set pull-ups for PC3-0
  GPIO_PORTC_PUR_R = 0x0F;
  
  // Set Alternate Function for PC3-0
  GPIO_PORTC_AFSEL_R = 0x0F;
  
  // Set Port Control Register for PC3-0
  GPIO_PORTC_PCTL_R = (GPIO_PCTL_PC0_TCK | GPIO_PCTL_PC1_TMS | GPIO_PCTL_PC2_TDI |GPIO_PCTL_PC3_TDO);
	
	return;
}


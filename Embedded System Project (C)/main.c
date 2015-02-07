//*****************************************************************************
//
//*****************************************************************************
#include "inc/main.h"

extern void PLL_Init(void);
extern volatile bool masterDevice;


//*****************************************************************************
//*****************************************************************************
void initializeBoard(void)
{
//	int i = 0;
	
  // Initialize the PLLs so the the main CPU frequency is 80MHz
  PLL_Init();
	
	// Initialize GPIO Pins
  initializeGpioPins();
  
  // Initialize UART0 & UART2 & UART5
  InitializeUART(UART0);
	InitializeUART(UART5);
	InitializeUART(UART2);

	
	// Initialize ADC0
  initializeADC();
	
	// Initialize the SysTick Timer
  initializeSysTick(SYSTICK_COUNT, true);
	
	// Initialize Timer0A
  initializeTimer0A(TIMERA0_COUNT, TIMERA0_PRESCALAR);
	
	// Initialize SPI0 interface
  initializeSPI0();
	
	return;
}

//*****************************************************************************
//*****************************************************************************
int main(void)
{

 	initializeBoard();

  masterDevice = detectMaster();

		if ( masterDevice)
    {
        masterApp();
    }
    else
    {
        slaveApp();
    }
}





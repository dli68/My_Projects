#include "inc/timer.h"

volatile bool AlertDebounce = false;
volatile bool AlertRowUpdate = false;
volatile bool AlertADC0 = false;
volatile bool FaceUpdate = false;

extern volatile uint8_t Row;
extern void EnableInterrupts(void);
extern void DisableInterrupts(void);
extern volatile bool OneSecond;
extern volatile bool WatchdogClear;

 /****************************************************************************
 * The SysTick Handler 
 ****************************************************************************/
void SYSTICKIntHandler(void)
{
		// variable declaration
		static uint32_t count = 0;
	  // Interrupt every 12.5 ms
		// 1 sec count checking mechenism
		static int32_t secCount = 1;
		secCount = (secCount + 1) % 80000;
		// Check to see if 1 second has passed
			if (secCount == 0)
		{
			OneSecond = true;
		}
		
    // Set AlertRowUpdate, FaceUpdate to true
		AlertRowUpdate = true;
		FaceUpdate = true;
	
    // clear the SysTick Timer Interrupt & Watchdog Timer 0.
		NVIC_ST_CURRENT_R = 0x00; 

		// insert clear watchdog signal
		if(count > 40000){
			count = 0;
			WatchdogClear = true;
		}
		count++;
		
		
}


 /****************************************************************************
 * The SysTick Handler 
 ****************************************************************************/
void Timer0AHandler(void)
{
  // Interrupt every 1ms 
	// Clear timer0A timeout flag
	TIMER0_ICR_R = TIMER_ICR_TATOCINT;
	// Check Button
	AlertDebounce = true;
	// Get Potentiometer values
	AlertADC0 = true;
}


/****************************************************************************
 * Initialize the SysTick timer to a given count.
 * Optionally turn on interrupts
 ****************************************************************************/
void initializeSysTick(uint32_t count, bool enableInterrupts)
{
	// disable SysTick timer
	NVIC_ST_CTRL_R = 0;
  // Set reload to count-1
	NVIC_ST_RELOAD_R = count - 1;
  // clear the current count
  NVIC_ST_CURRENT_R = 0;
  // If enableInterrupts is true, enable the timer with interrupts
  // else enable the timer without interrupts
	if (enableInterrupts){
		NVIC_ST_CTRL_R = (NVIC_ST_CTRL_ENABLE | NVIC_ST_CTRL_CLK_SRC | NVIC_ST_CTRL_INTEN);
	}
	else{
		NVIC_ST_CTRL_R = (NVIC_ST_CTRL_ENABLE | NVIC_ST_CTRL_CLK_SRC);
	}
}

/****************************************************************************
 * Initialize the TimerA0.
 * Always turn on interrupts
 ****************************************************************************/
void initializeTimer0A(uint32_t count, uint32_t prescalar)
{
	uint16_t delay;
	
	// Enable and provide a clk for general porpose timer0
  SYSCTL_RCGCTIMER_R |= SYSCTL_RCGCTIMER_R0;
	
	// Delay wait for clk ready
	delay = SYSCTL_RCGCTIMER_R;
	
	// Disable timer0A during setup
	TIMER0_CTL_R &= ~TIMER_CTL_TAEN;
	
	// Configure for 32-bit timer mode
	TIMER0_CFG_R = TIMER_CFG_32_BIT_TIMER;
	
	// Configure for periodic mode
	TIMER0_TAMR_R = TIMER_TAMR_TAMR_PERIOD;
	
	// Set reload value
	TIMER0_TAILR_R = count - 1; // 1ms interrupt
	
	// Set prescalar value
	TIMER0_TAPR_R = prescalar; // timer counts every 1 micro second
	
	// Clear timer0A timeout flag
	TIMER0_ICR_R = TIMER_ICR_TATOCINT;
	
	// Enable timeout interrupt
	TIMER0_IMR_R |= TIMER_IMR_TATOIM;
	
	// Enable interrupt 19 in NVIC
	NVIC_EN0_R = NVIC_EN0_INT19;
	
	// Enable timer0A
	TIMER0_CTL_R |= TIMER_CTL_TAEN;
	
	EnableInterrupts();
}

/****************************************************************************
 * Initialize Watchdog Timer 0.
 * Always turn on interrupts
 ****************************************************************************/
void initializeWatchdogTimer1(uint32_t count)
{
	
	uint16_t delay;
	
	// Enable the peripheral clock of Watchdog Timer.
  SYSCTL_RCGCWD_R |= SYSCTL_RCGCWD_R0;
	
	// Delay wait for clk ready
	delay = SYSCTL_RCGCWD_R;
	
	// Load the WDTLOAD register with desired value
	WATCHDOG0_LOAD_R = count;
	
	// Set Watchdog such that it is configured to trigger system reset.
	WATCHDOG0_CTL_R |= WDT_CTL_RESEN;
	
	// Enable Watchdog, interrupts, and lock the control register.
	WATCHDOG0_CTL_R |= WDT_CTL_INTEN;
	
	EnableInterrupts();
}


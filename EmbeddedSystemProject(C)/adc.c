#include "inc/adc.h"

//******************************************************
// Global Variables & external functions
//******************************************************
extern void StartCritical(void);
extern void EndCritical(void);
extern volatile bool AlertADC0;

volatile uint8_t NewdutyCycle1 = 0;
volatile uint8_t NewdutyCycle2 = 0;

/****************************************************************************
 * Initialization code for the ADC
 * with SS3, Software trigger, 125KHz
 ****************************************************************************/
void initializeADC(void)
{
		uint16_t i;
	
		/* Config PortE */
	  // Turn on the Clock Gating Register
		GPIO_PORT *GpioPortE = (GPIO_PORT *)PORTE;
    SYSCTL_RCGCGPIO_R |= SYSCTL_RCGC2_GPIOE;
    i = 1000;
		while (i) { i--; }
	
    // Set the direction as an input
    GpioPortE->Direction &= 0xF3;

    // Set the alternate function
		GpioPortE->AlternateFunctionSelect |= 0x0C;

    // Disable the Digital Enable
		GpioPortE->DigitalEnable &= 0xF3;

    // Enable Analog 
		GpioPortE->AnalogSelectMode |= 0x0C;    

    // Enable ADC 
    SYSCTL_RCGCADC_R |= SYSCTL_RCGCADC_R0;
		i = 1000;
		while (i){ i--;}

    // Disable Sample Sequencer #3 
    ADC0_ACTSS_R &= ~ADC_ACTSS_ASEN3;

    // Configure the sample sequencer so Sample Sequencer #3 (EM3) 
    // is triggered by the processor
    ADC0_EMUX_R = ADC_EMUX_EM3_PROCESSOR;
    
    // Clear the Sample Input Select for Sample Sequencer #3
    ADC0_SSMUX3_R = ADC_SSMUX3_MUX0_S;

    // Configure the Sample Sequencer #3 control register.  Make sure to set 
    // the 1st Sample Interrupt Enable and the End of Sequence bits
    ADC0_SSCTL3_R = ADC_SSCTL3_IE0|ADC_SSCTL3_END0;
    
    // Clear Averaging Bits
    ADC0_SAC_R = ADC_SAC_AVG_OFF; //ADC0_SAC_R=ADC_SAC_AVG_M; 

    // Average 64 samples
    ADC0_SAC_R=ADC_SAC_AVG_64X;
    
    // Do NOT enable the Sequencer after this.  This is done in GetADCval
		return;
}

//*****************************************************************************
// Get the ADC value of a given ADC Channel
//*****************************************************************************
uint32_t GetADCval(uint32_t Channel)
{
  uint32_t result;

  ADC0_SSMUX3_R = Channel;      // Set the channel
  ADC0_ACTSS_R  |= ADC_ACTSS_ASEN3; // Enable SS3
  ADC0_PSSI_R = ADC_PSSI_SS3;     // Initiate SS3

  while(0 == (ADC0_RIS_R & ADC_RIS_INR3)); // Wait for END of conversion
  result = ADC0_SSFIFO3_R & 0x0FFF;     // Read the 12-bit ADC result
  ADC0_ISC_R = ADC_ISC_IN3;         // Acknowledge completion

  return result;
}


//*****************************************************************************
// The refresh rate will go from 0-99Hz.
//*****************************************************************************
void getDutyCycle(void)
{
	uint16_t adcVal = 0;
	StartCritical(); 
	// check every 1.25ms = 1/800Hz (interrupt rate)
	if(AlertADC0){
		// Check left potentiometer current value
		adcVal = GetADCval(LEFT_POT);
		NewdutyCycle1 = (adcVal * 100)/4096;
		//Normalize NewdutyCycle1
		if(NewdutyCycle1 < 20){
				NewdutyCycle1 = 0;
		}
		
		// clear AlertADC0 interrupt
		// Check right potentiometer current value
		adcVal = GetADCval(RIGHT_POT);
		NewdutyCycle2 = (adcVal * 100)/4096;
		//Normalize NewdutyCycle2
		if(NewdutyCycle2 > 80){
				NewdutyCycle2 = 100;
		}
			
		// clear AlertADC0 interrupt
		AlertADC0 = false;
	}
	EndCritical();
	return;
}

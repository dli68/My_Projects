#include "inc/uart.h"
#include "inc/lm4f120h5qr.h"
#include "inc/gpio.h"

/******************************************************************************
* Global Variables
*****************************************************************************/
  CircularBuffer uart0_rx_buff;
	CircularBuffer uart0_tx_buff;
	
	CircularBuffer uart2_rx_buff;
	CircularBuffer uart2_tx_buff;
	
	CircularBuffer uart5_rx_buff;
	CircularBuffer uart5_tx_buff;
	
/******************************************************************************
 * Defines
 *****************************************************************************/
#define buff_size                 16

/****************************************************************************
 * Configure UART 0 & 2 & 5 for 8-n-1 with RX and TX interrupts enabled.
 * Enable the RX and TX FIFOs as well.
 ****************************************************************************/
void InitializeUART(uint32_t base)
{
  GPIO_PORT *myPort;
	UART_PERIPH *myUart;
	uint32_t delay;
		
	// Validate that a correct base address has been passed
	switch (base) 
	{
		case UART0 :
			// Set up pointers
			myUart = (UART_PERIPH *)UART0; 
			myPort = (GPIO_PORT *)PORTA;
			// Eanble the clock gating register for UART0
			// ( Not found in the UART_PERIPH struct)
			SYSCTL_RCGCUART_R |= SYSCTL_RCGCUART_R0;
			
			// delay until UART0 clk ready
			delay = 500;
			while( delay != 0)
			{
				delay--;
			}
			/*Configure GPIO PA0 and PA1 to be UART Pins*/
			// Turn on the clock gating register for GPIO port A
			// Make sure not to turn of any of the other ports
			SYSCTL_RCGCGPIO_R |= SYSCTL_RCGCGPIO_R0;   
			delay = 500;
			while(delay != 0){
				delay--;
			}
			// Set the Digital Enable
			myPort->DigitalEnable |= (1<<0) | (1<<1); 
			// Set the Alternate Function
			myPort->AlternateFunctionSelect |=  (1<<0) | (1<<1);
			// Set the Port Control Register 
			myPort->PortControl |= (1<<0) | (1<<4);    
			break;
		case UART2 :
			// Set up pointers
			myUart = (UART_PERIPH *)UART2; 
			myPort = (GPIO_PORT *)PORTD;
			// Eanble the clock gating register for UART0
			// ( Not found in the UART_PERIPH struct)
			SYSCTL_RCGCUART_R |= SYSCTL_RCGCUART_R2;
			
			// delay until UART0 clk ready
			delay = 500;
			while( delay != 0)
			{
				delay--;
			}
			/*Configure GPIO PD6 and PD7 to be UART0 Pins*/
 			// Turn on the clock gating register for GPIO port D
			// Make sure not to turn of any of the other ports
			SYSCTL_RCGCGPIO_R |= SYSCTL_RCGCGPIO_R3;   
			delay = 500;
			while(delay != 0){
				delay--;
			}
			// Port D has a lock register, so we need to unlock it before making modifications
			myPort->Lock  = 0x4C4F434B;
			myPort->Commit = 0xFF;
			// Set the Digital Enable
			myPort->DigitalEnable |= (1<<6) | (1<<7); 
			// Set the Alternate Function
			myPort->AlternateFunctionSelect |=  (1<<6) | (1<<7);
			// Set the Port Control Register 
			myPort->PortControl |= (1<<24) | (1<<28); 
      break;
		case UART5 :
			// Set up pointers
			myUart = (UART_PERIPH *)UART5; 
			myPort = (GPIO_PORT *)PORTE;
			// Eanble the clock gating register for UART5
      SYSCTL_RCGCUART_R |= SYSCTL_RCGCUART_R5;
			// delay until UART0 clk ready
			delay = 500;
			while( delay != 0)
			{
				delay--;
			}
			/*Configure GPIO PE4 and PE5 to be UART0 Pins*/
			// Turn on the clock gating register for GPIO port E
			// Make sure not to turn of any of the other ports
			SYSCTL_RCGCGPIO_R |= SYSCTL_RCGCGPIO_R4;   
			delay = 500;
			while(delay != 0){
				delay--;
			}
			// Set the Digital Enable
			myPort->DigitalEnable |= (1<<4) | (1<<5); 
			// Set the Alternate Function
			myPort->AlternateFunctionSelect |=  (1<<4) | (1<<5);
			// Set the Port Control Register 
			myPort->PortControl |= (1<<16) | (1<<20); 
			break;
	}	
	// just delay
	delay = SYSCTL_RCGC1_R;
  delay = SYSCTL_RCGC1_R;
	
	// Disable UART
  myUart->UARTControl &= ~UART_CTL_UARTEN; 
	
	// BAUD Rate pf 115200
  myUart->IntegerBaudRateDiv = 43; 
  myUart->FracBaudRateDiv = 26;
	
	// Clear TX and RX Interrupt FIFO level Fields
	myUart->IntFIFOLevelSel &= ~0x3F;

	// set Interrupt levels (transmit 1/8 empty, recieve 7/8 full)
  myUart->IntFIFOLevelSel |= UART_IFLS_RX7_8 | UART_IFLS_TX7_8;
  
	// Enable UART Interrupt
	myUart->IntMask |=  UART_IM_RTIM | UART_IM_TXIM | UART_IM_RXIM;
	
	// Configure the Line Control for 8-n-1
  myUart->LineControl |= UART_LCRH_FEN | UART_LCRH_WLEN_8;    /*enable FIFO, FEN*/             
	
	// Enable the UART - Need to enabel both TX and RX and Enable UART
  myUart->UARTControl |= UART_CTL_RXE | UART_CTL_TXE | UART_CTL_UARTEN;   
	
	
	switch (base) 
  {
		case UART0 :
			// Wait until the UART is avaiable
			while( !(SYSCTL_PRUART_R & SYSCTL_PRUART_R0 ));
			//enable interrupt 5 in NVIC
			NVIC_EN0_R = NVIC_EN0_INT5; 
			//ciculart buffer instantiation
			cBufInit(&uart0_rx_buff, buff_size);
			cBufInit(&uart0_tx_buff, buff_size);
			break;
		case UART2 :
			// Wait until the UART is avaiable
			while( !(SYSCTL_PRUART_R & SYSCTL_PRUART_R2 ));
			//enable interrupt 33 in NVIC
			NVIC_EN1_R = NVIC_EN0_INT1; 
			//ciculart buffer instantiation
			cBufInit(&uart2_rx_buff, buff_size);
			cBufInit(&uart2_tx_buff, buff_size);
			break;
		case UART5 :
			// Wait until the UART is avaiable
			while( !(SYSCTL_PRUART_R & SYSCTL_PRUART_R5 ));
			//enable interrupt 5 in NVIC
			NVIC_EN1_R = NVIC_EN0_INT29; 
			//ciculart buffer instantiation
			cBufInit(&uart5_rx_buff, buff_size);
			cBufInit(&uart5_tx_buff, buff_size);
			break;
	}	
	return;
}

/****************************************************************************
 * This function returns a single character from the Rx circular buffer.  
 * It takes one argument which indicates if the function will wait until 
 * data is found.  
 ****************************************************************************/
int uartRx(bool block, uint32_t base)
{
	char rx_char;
	bool empty;
	
	// Validate that a correct base address has been passed
	switch (base) 
  {
		case UART0 :
			//if buffer not empty
			if(cBufGetFreeCount(&uart0_rx_buff) != buff_size){ 
				DisableInterrupts();
				cBufGetChar(&uart0_rx_buff , &rx_char);
				EnableInterrupts();
				//return a single char.
				return (int)rx_char;
			}
			//if buffer empty
			else{
				empty = true;
				//if block is set. i.e will wait until data is found.
				if(block){
					while(empty){	
						if(cBufGetFreeCount(&uart0_rx_buff) != buff_size){
							empty = false;
						}
					}
					DisableInterrupts();
					cBufGetChar(&uart0_rx_buff , &rx_char);
					EnableInterrupts();
					return (int)rx_char;
				}
				//if block is not set, return immediately.
				else{
					return 200;
				}
			}
		case UART2 :
			//if buffer not empty
			if(cBufGetFreeCount(&uart2_rx_buff) != buff_size){ 
				DisableInterrupts();
				cBufGetChar(&uart2_rx_buff , &rx_char);
				EnableInterrupts();
				//return a single char.
				return (int)rx_char;
			}
			//if buffer empty
			else{
				empty = true;
				//if block is set. i.e will wait until data is found.
				if(block){
					while(empty){	
						if(cBufGetFreeCount(&uart2_rx_buff) != buff_size){
							empty = false;
						}
					}
					DisableInterrupts();
					cBufGetChar(&uart2_rx_buff , &rx_char);
					EnableInterrupts();
					return (int)rx_char;
				}
				//if block is not set, return immediately.
				else{
					return 200;
				}
			}
		case UART5 :
			//if buffer not empty
			if(cBufGetFreeCount(&uart5_rx_buff) != buff_size){ 
				DisableInterrupts();
				cBufGetChar(&uart5_rx_buff , &rx_char);
				EnableInterrupts();
				//return a single char.
				return (int)rx_char;
			}
			//if buffer empty
			else{
				empty = true;
				//if block is set. i.e will wait until data is found.
				if(block){
					while(empty){	
						if(cBufGetFreeCount(&uart5_rx_buff) != buff_size){
							empty = false;
						}
					}
					DisableInterrupts();
					cBufGetChar(&uart5_rx_buff , &rx_char);
					EnableInterrupts();
					return (int)rx_char;
				}
				//if block is not set, return immediately.
				else{
					return 200;
				}
			}
	}
	return 0;
}

/****************************************************************************
 * This function accepts a single character and places it into Tx circular 
 * buffer if there is not room in the Tx hardware FIFO.
 ****************************************************************************/
void uartTx(int data, uint32_t base)
{
	UART_PERIPH *myUart;
	
	// Validate that a correct base address has been passed
	switch (base) 
  {
		case UART0 :
			myUart = (UART_PERIPH *) UART0;
			//check if circular buffer empty or if FIFO is full
			if((cBufGetFreeCount(&uart0_tx_buff) != 16) || ((UART0_FR_R & UART_FR_TXFF) != 0)){
				//circular buffer is not empty or FIFO is full; check if circular buffer is full; if it is
				//full, wait until there is room, and put data into cicular buffer
				while (cBufGetFreeCount(&uart0_tx_buff) == 0);
				DisableInterrupts();
				cBufAddChar(&uart0_tx_buff, data);	//put into buffer
				EnableInterrupts();
			}
			else{
				//circular buffer is empty, so put data into FIFO directly.
				UART0_DR_R = data;
			}
			break;
		case UART2 :
			myUart = (UART_PERIPH *) UART2;
			//check if circular buffer empty or if FIFO is full
			if((cBufGetFreeCount(&uart2_tx_buff) != 16) || ((UART2_FR_R & UART_FR_TXFF) != 0)){
				//circular buffer is not empty or FIFO is full; check if circular buffer is full; if it is
				//full, wait until there is room, and put data into cicular buffer
				while (cBufGetFreeCount(&uart2_tx_buff) == 0);
				DisableInterrupts();
				cBufAddChar(&uart2_tx_buff, data);	//put into buffer
				EnableInterrupts();
			}
			else{
				//circular buffer is empty, so put data into FIFO directly.
				UART2_DR_R = data;
			}
			break;
		case UART5 :
			myUart = (UART_PERIPH *) UART5;
			//check if circular buffer empty or if FIFO is full
			if((cBufGetFreeCount(&uart5_tx_buff) != 16) || ((UART5_FR_R & UART_FR_TXFF) != 0)){
				//circular buffer is not empty or FIFO is full; check if circular buffer is full; if it is
				//full, wait until there is room, and put data into cicular buffer
				while (cBufGetFreeCount(&uart5_tx_buff) == 0);
				DisableInterrupts();
				cBufAddChar(&uart5_tx_buff, data);	//put into buffer
				EnableInterrupts();
			}
			else{
				//circular buffer is empty, so put data into FIFO directly.
				UART5_DR_R = data;
			}
			break;
	}
	//reenable TX empty interrupt
	myUart->IntMask |=  UART_IM_TXIM;
	return;
}



/*****************************************************************************
*
* UART0 Handler
*
*****************************************************************************/
void UART0IntHandler()
{
  char data; // buffer for transfer char btw circular buffer and hardware FIFO
	UART_PERIPH *myUart = (UART_PERIPH *) UART0;
	if(UART0_RIS_R & UART_RIS_TXRIS){	// hardware TX FIFO empty interrupt (1/8)
		UART0_ICR_R = UART_ICR_TXIC; // Clear interrupt		
		if(cBufGetFreeCount(&uart0_tx_buff) == buff_size){ // if uart0_tx_buff empty
			// Disable TX EMPTY IRQs
			myUart->IntMask &= ~UART_IM_TXIM;
		}
		else{
			//copy chars from uart0_tx_buff to hardware TX FIFO until uart0_tx_buff is empty or hardware TX FIFO is full
			while(((UART0_FR_R & UART_FR_TXFF) == 0) && (cBufGetFreeCount(&uart0_tx_buff) != buff_size)){ 
				cBufGetChar(&uart0_tx_buff, &data);
				UART0_DR_R = data;
			}
		}
	}
	if(UART0_RIS_R & UART_RIS_RXRIS){	// hardware RX FIFO full interrupt (7/8)
		UART0_ICR_R = UART_ICR_RXIC;	// Clear interrupt
		while((UART0_FR_R & UART_FR_RXFE) == 0){ // While hardware RX FIFO not empty
				while(cBufGetFreeCount(&uart0_tx_buff) == 0); // if uart0_rx_buff full wait
				// if uart0_rx_buff not full, copy from hardware RX FIFO to uart0_rx_buff until hardware RX FIFO empty
				data = UART0_DR_R; 
				cBufAddChar(&uart0_rx_buff, data);
		}
	}
	if(UART0_RIS_R & UART_RIS_RTRIS){ // receiver time out interrupt
		UART0_ICR_R = UART_ICR_RTIC; // Clear interrupt
		while((UART0_FR_R & UART_FR_RXFE) == 0){ // While hardware RX FIFO not empty
				while(cBufGetFreeCount(&uart0_tx_buff) == 0); // if uart0_rx_buff full wait
				// if uart0_rx_buff not full, copy from hardware RX FIFO to uart0_rx_buff until hardware RX FIFO empty
				data = UART0_DR_R; 
				cBufAddChar(&uart0_rx_buff, data);
		}
	}
}
/*****************************************************************************
*
* UART2 Handler
*
*****************************************************************************/
void UART2IntHandler()
{
	char data; // buffer for transfer char btw circular buffer and hardware FIFO
	UART_PERIPH *myUart = (UART_PERIPH *) UART2;
	if(UART2_RIS_R & UART_RIS_TXRIS){	// hardware TX FIFO empty interrupt (1/8)
		UART2_ICR_R = UART_ICR_TXIC; // Clear interrupt		
		if(cBufGetFreeCount(&uart2_tx_buff) == buff_size){ // if uart2_tx_buff empty
			// Disable TX EMPTY IRQs
			myUart->IntMask &= ~UART_IM_TXIM;
		}
		else{
			//copy chars from uart2_tx_buff to hardware TX FIFO until uart2_tx_buff is empty or hardware TX FIFO is full
			while(((UART2_FR_R & UART_FR_TXFF) == 0) && (cBufGetFreeCount(&uart2_tx_buff) != buff_size)){ 
				cBufGetChar(&uart2_tx_buff, &data);
				UART2_DR_R = data;
			}
		}
	}
	if(UART2_RIS_R & UART_RIS_RXRIS){	// hardware RX FIFO full interrupt (7/8)
		UART2_ICR_R = UART_ICR_RXIC;	// Clear interrupt
		while((UART2_FR_R & UART_FR_RXFE) == 0){ // While hardware RX FIFO not empty
				while(cBufGetFreeCount(&uart2_tx_buff) == 0); // if uart2_rx_buff full wait
				// if uart2_rx_buff not full, copy from hardware RX FIFO to uart2_rx_buff until hardware RX FIFO empty
				data = UART2_DR_R; 
				cBufAddChar(&uart2_rx_buff, data);
		}
	}
	if(UART2_RIS_R & UART_RIS_RTRIS){ // receiver time out interrupt
		UART2_ICR_R = UART_ICR_RTIC; // Clear interrupt
		while((UART2_FR_R & UART_FR_RXFE) == 0){ // While hardware RX FIFO not empty
				while(cBufGetFreeCount(&uart2_tx_buff) == 0); // if uart2_rx_buff full wait
				// if uart2_rx_buff not full, copy from hardware RX FIFO to uart2_rx_buff until hardware RX FIFO empty
				data = UART2_DR_R; 
				cBufAddChar(&uart2_rx_buff, data);
		}
	}
}
/*****************************************************************************
*
* UART5 Handler
*
*****************************************************************************/
void UART5IntHandler()
{
	char data; // buffer for transfer char btw circular buffer and hardware FIFO
	UART_PERIPH *myUart = (UART_PERIPH *) UART5;
	myUart = (UART_PERIPH *) UART5;
	if(UART5_RIS_R & UART_RIS_TXRIS){	// hardware TX FIFO empty interrupt (1/8)
		UART5_ICR_R = UART_ICR_TXIC; // Clear interrupt		
		if(cBufGetFreeCount(&uart5_tx_buff) == buff_size){ // if uart5_tx_buff empty
			// Disable TX EMPTY IRQs
			myUart->IntMask &= ~UART_IM_TXIM;
		}
		else{
			//copy chars from uart5_tx_buff to hardware TX FIFO until uart5_tx_buff is empty or hardware TX FIFO is full
			while(((UART5_FR_R & UART_FR_TXFF) == 0) && (cBufGetFreeCount(&uart5_tx_buff) != buff_size)){ 
				cBufGetChar(&uart5_tx_buff, &data);
				UART5_DR_R = data;
			}
		}
	}
	if(UART5_RIS_R & UART_RIS_RXRIS){	// hardware RX FIFO full interrupt (7/8)
		UART5_ICR_R = UART_ICR_RXIC;	// Clear interrupt
		while((UART5_FR_R & UART_FR_RXFE) == 0){ // While hardware RX FIFO not empty
				while(cBufGetFreeCount(&uart5_tx_buff) == 0); // if uart5_rx_buff full wait
				// if uart5_rx_buff not full, copy from hardware RX FIFO to uart5_rx_buff until hardware RX FIFO empty
				data = UART5_DR_R; 
				cBufAddChar(&uart5_rx_buff, data);
		}
	}
	if(UART5_RIS_R & UART_RIS_RTRIS){ // receiver time out interrupt
		UART5_ICR_R = UART_ICR_RTIC; // Clear interrupt
		while((UART5_FR_R & UART_FR_RXFE) == 0){ // While hardware RX FIFO not empty
				while(cBufGetFreeCount(&uart5_tx_buff) == 0); // if uart0_rx_buff full wait
				// if uart5_rx_buff not full, copy from hardware RX FIFO to uart5_rx_buff until hardware RX FIFO empty
				data = UART5_DR_R; 
				cBufAddChar(&uart5_rx_buff, data);
		}
	}
}	
//*****************************************************************************
// DO NOT MODIFY below
//*****************************************************************************

//****************************************************************************
//  This function is called from MicroLIB's stdio library.  By implementing
//  this function, MicroLIB's getchar(), scanf(), etc will now work.
// ****************************************************************************/
int fgetc(FILE* stream)
{
   int c;

   if (stream != stdin)
   {
      errno = EINVAL; // should probably be ENOSTR
      return EOF;
   }

   c = uartRx(true,UART0);

   if (c == '\r')
      c = '\n';
   
   fputc(c, stdout);

   return c;
}

//****************************************************************************
// This function is called from MicroLIB's stdio library.  By implementing
// this function, MicroLIB's putchar(), puts(), printf(), etc will now work.
// ****************************************************************************/
int fputc(int c, FILE* stream)
{
   if (stream != stdout) // bah! to stderr
   {
      errno = EINVAL; // should probably be ENOSTR
      return EOF;
   }

   uartTx(c,UART0);

   if (c == '\n')
      uartTx('\r',UART0);

   return c;
}

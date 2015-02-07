#include "inc/spi.h"
/******************************************************************************
 * Defines
 *****************************************************************************/

#define PHASE   1
#define POLARITY 1

#define WRITE 0x02
#define READ 0x03
#define WRDI 0x04
#define RDSR 0x05
#define WREN 0x06
 
//*****************************************************************************
// External Functions & Varaibles 
//*****************************************************************************

extern void mcp25AA080D_writeArray( uint16_t address, char *msg, int16_t msg_size);
extern void mcp25AA080D_readArray(uint16_t address, char *msg, int16_t msg_size);

static SPI_PERIPH *Spi0 = (SPI_PERIPH *)SSI0;

// *******************************************
// Configure SPI
// *******************************************
bool initializeSPI0(void)
{
  uint32_t delay;
	GPIO_PORT *GpioPortA = (GPIO_PORT *)PORTA;
	
	/*Initialize PortA Spi0: configure GPIO PA5-PA2 as SPI*/
	// Turn on the clock gating register for GPIO port A
  // Make sure not to turn of any of the other ports
  SYSCTL_RCGCGPIO_R |= SYSCTL_RCGCGPIO_R0;
  
  // Delay while clock starts up
  delay = SYSCTL_RCGCGPIO_R;
  
  // Set the 4 pins used for the SPI interface in the Digital Enable Register
  GpioPortA->DigitalEnable |= (1<<2 | 1<<3 | 1<<4 | 1<<5);           // ###01###
  
  // Set the 4 pins used for the SPI interface in the Alternate Function Register
  GpioPortA->AlternateFunctionSelect |=  (1<<2 | 1<<3 | 1<<4 | 1<<5); // ###02###
  
  // Set the Port Control Register ( See lm4f120h5qr.h starting at line 2045)
  GpioPortA->PortControl |= GPIO_PCTL_PA2_SSI0CLK | GPIO_PCTL_PA3_SSI0FSS | GPIO_PCTL_PA4_SSI0RX | GPIO_PCTL_PA5_SSI0TX;             // ###03###
	
	
	/*Initialize SPI*/ 
	// Turn on the Clock Gating Register
	SYSCTL_RCGCSSI_R |= SYSCTL_RCGCSSI_R0;
	
	// Delay wait for clk ready
  delay = SYSCTL_RCGCSSI_R;

  // Disable the SSI interface
  Spi0->SSICR1 &= ~(1<<1);                   // ###04###

  // Enable Master Mode
  Spi0->SSICR1 &= ~(1<<2);                   // ###05###
  
  // We hvae a 80MHz clock and want a 2MHz SPI clock
  // FSSIClk = FSysClk / (CPSDVSR * (1 + SCR))
	// 2 = 80 / (40 * (1 + 0))
  Spi0->SSICPSR |= 40;                  // ###06###
  Spi0->SSICR0  &= 0xFFFF00FF;                  // ###07###
	
  // Clear the phse and polarity bits
  Spi0->SSICR0  &=  ~(SSI_CR0_SPH | SSI_CR0_SPO);
  
  if (PHASE == 1)
      Spi0->SSICR0  |= SSI_CR0_SPH;
  
  if (POLARITY ==1)
      Spi0->SSICR0  |= SSI_CR0_SPO;

  // Freescale SPI Mode with 8-Bit data (See line 2226 of lm4f120h5qr.h)
  Spi0->SSICR0 |= SSI_CR0_DSS_8;                  // ###08###
  Spi0->SSICR0 &= ~(1<<4);
	Spi0->SSICR0 &= ~(1<<5); 
	
  //Enable SSI
  Spi0->SSICR1 |= SSI_CR1_SSE;                   // ###09###

  return true;
}

// *******************************************
// function spiTx()
// *******************************************
void spiTx(uint8_t *dataIn, int size, uint8_t *dataOut)
{
	uint8_t null;
	// Checking the parameters
	if(size == sizeof(SPI_EEPROM_BYTE_CMD)){
		SPI_EEPROM_BYTE_CMD *in = (SPI_EEPROM_BYTE_CMD *) dataIn;
		SPI_EEPROM_BYTE_CMD *out = (SPI_EEPROM_BYTE_CMD *) dataOut;
		
		// Check if the transmit has finished or not; if not, busy wait.
		while((Spi0->SSISR & SSI_SR_BSY) != 0);
		// Disable the SSI interface
		Spi0->SSICR1 &= ~(1<<1);
		// Add instruction to tx FIFO first
		Spi0->SSIDR = in->instr;
		// Add high byte of address to tx FIFO
		Spi0->SSIDR = in->addr_hi;
		// Add low byte to tx FIFO
		Spi0->SSIDR = in->addr_low;
		// Add data to tx FIFO
		Spi0->SSIDR = in->data;
		// Enable SPI 
		Spi0->SSICR1 |= SSI_CR1_SSE;
		// wait until the receive has finished
		while((Spi0->SSISR & SSI_SR_BSY) != 0);
		// read out instr byte from rx FIFO
		out->instr = Spi0->SSIDR;
		// read out high byte of address from rx FIFO
		out->addr_hi = Spi0->SSIDR;
		// read out low byte of address from rx FIFO
		out->addr_low = Spi0->SSIDR;
		// read out data byte
		out->data = Spi0->SSIDR;
	}
	else if(size == sizeof(SPI_EEPROM_CFG_CMD)){
		SPI_EEPROM_CFG_CMD *in = (SPI_EEPROM_CFG_CMD *) dataIn;
		SPI_EEPROM_CFG_CMD *out = (SPI_EEPROM_CFG_CMD *) dataOut;
		
		// Check if the transmit has finished or not; if not, busy wait.
		while((Spi0->SSISR & SSI_SR_BSY) != 0);
		// Disable the SSI interface
		Spi0->SSICR1 &= ~(1<<1);
		// Add instruction to tx FIFO first
		Spi0->SSIDR = in->instr;
		// Add data to tx FIFO
		Spi0->SSIDR = in->data;
		// Enable SPI 
		Spi0->SSICR1 |= SSI_CR1_SSE;
		// wait until the receive has finished
		while((Spi0->SSISR & SSI_SR_BSY) != 0);
		// read out instr byte from rx FIFO
		out->instr = Spi0->SSIDR;
		// read out data byte from rx FIFO
		out->data = Spi0->SSIDR;
	}
	else{
		SPI_EEPROM_EN_CMD *in = (SPI_EEPROM_EN_CMD *) dataIn;
		
		// Check if the transmit has finished or not; if not, busy wait.
		while((Spi0->SSISR & SSI_SR_BSY) != 0);
		// Disable the SSI interface
		Spi0->SSICR1 &= ~(1<<1);
		// Add instruction to tx FIFO first
		Spi0->SSIDR = in->instr;
		// read out one byte from rx FIFO
		null = Spi0->SSIDR;
		// Enable SPI 
		Spi0->SSICR1 |= SSI_CR1_SSE;
	}
	return;
}

// *******************************************
// function spi_eeprom_read_byte()
// *******************************************
uint8_t spi_eeprom_read_byte(uint16_t address)
{
	SPI_EEPROM_BYTE_CMD recv_packet;
	SPI_EEPROM_BYTE_CMD send_packet;
	
	//check if a write is in progress; if so, wait.
	spi_eeprom_wait_write_in_progress();

	//send_packet setup
	send_packet.instr = READ;
	send_packet.addr_hi = (address >> 8) & 0x00FF;
	send_packet.addr_low = address & 0x00FF;
	send_packet.data = 0x00;
	//send two packages to spiTx fucntion.
	spiTx((uint8_t *)&send_packet, sizeof(SPI_EEPROM_BYTE_CMD), (uint8_t *)&recv_packet);
	
	return recv_packet.data;
}

// *******************************************
// function spi_eeprom_write_byte()
// *******************************************
void spi_eeprom_write_byte(uint16_t address, uint8_t value)
{
	SPI_EEPROM_BYTE_CMD send_packet;
	
	//check if a write is in progress; if so, wait.
	spi_eeprom_wait_write_in_progress();
	
	//Enable Writes
	spi_eeprom_write_enable();

	//send_packet setup
	send_packet.instr = WRITE;
	send_packet.addr_hi = (address >> 8) & 0x00FF;
	send_packet.addr_low = address & 0x00FF;
	send_packet.data = value;
	//send the packages to spiTx fucntion.
	spiTx((uint8_t *)&send_packet, sizeof(SPI_EEPROM_BYTE_CMD), 0);
	
	//Disable Writes
	spi_eeprom_write_disable();
	
	return;
}

// *******************************************
// function spi_eeprom_write_enable()
// *******************************************
void spi_eeprom_write_enable(void)
{
	SPI_EEPROM_EN_CMD send_packet;
	//send_packet setup
	send_packet.instr = WREN;
	//send the packages to spiTx fucntion.
	spiTx((uint8_t *)&send_packet, sizeof(SPI_EEPROM_EN_CMD), 0);
	
	return;
}
// *******************************************
// function spi_eeprom_write_disable()
// *******************************************
void spi_eeprom_write_disable(void)
{
	SPI_EEPROM_EN_CMD send_packet;
	//send_packet setup
	send_packet.instr = WRDI;
	//send the packages to spiTx fucntion.
	spiTx((uint8_t *)&send_packet, sizeof(SPI_EEPROM_EN_CMD), 0);
	
	return;
}
// *******************************************
// function spi_eeprom_wait_write_in_progress()
// *******************************************
void spi_eeprom_wait_write_in_progress(void)
{
	uint8_t count = 0;
	//check if a write is in progress; if so, wait.
	while(count < 2){
		if((spi_eeprom_read_status() & 0x01) == 0){
				count++;
		}
	}
	return;
}
// *******************************************
// function spi_eeprom_read_status()
// *******************************************
uint8_t spi_eeprom_read_status(void)
{
	SPI_EEPROM_CFG_CMD send_packet;
	SPI_EEPROM_CFG_CMD recv_packet;
	//send_packet setup
	send_packet.instr = RDSR;
	send_packet.data = 0x00;
	spiTx((uint8_t *)&send_packet, sizeof(SPI_EEPROM_CFG_CMD), (uint8_t *)&recv_packet);
	return recv_packet.data;
}


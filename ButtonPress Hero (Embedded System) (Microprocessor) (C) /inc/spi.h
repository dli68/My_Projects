#ifndef __SPI_H__
#define __SPI_H__

#include <stdint.h>
#include <stdbool.h>
#include "gpio.h"

/*************************************************************************
 ************************************************************************/
#define SSI0 0x40008000
#define SSI1 0x40009000
#define SSI2 0x4000A000
#define SSI3 0x4000B000

/*************************************************************************
 ************************************************************************/
#define DISABLED                0
#define ENABLED                 1
#define INT_MASKED              0
#define INT_UNMASKED            1

/*************************************************************************
 ************************************************************************/
#define SSI_SLAVE_BYP           1
#define SSI_END_OF_TX           1
#define SSI_MODE_SLAVE_OD       1
#define SSI_MASTER_EN           0
#define SSI_SLAVE_EN            1
#define SSP_EN                  1
#define SSI_LOOPBK_EN           1

/*************************************************************************
 ************************************************************************/

// 4MHz = 80MHz / (CPSDVSR * (1 + SCR)
#define SPI_CLK_CPSDVSR         20
#define SSI_SPH_FIRST           0
#define SSI_SPH_SECOND          1
#define SSI_SPO_LOW             0
#define SSI_SPO_HIGH            1
#define SSI_FRF_SPI             0x0
#define SSI_FRF_TI              0x1
#define SSI_FRF_NMW             0x2
#define SSI_DSS_8BIT            0x7
#define SSI_DSS_16BIT           0xF


typedef struct {
  volatile uint32_t    SSICR0;        // + 0x000
  volatile uint32_t    SSICR1;        // + 0x004          
  volatile uint32_t    SSIDR;         // + 0x008
  volatile uint32_t    SSISR;         // + 0x00C
  volatile uint32_t    SSICPSR;       // + 0x010
  volatile uint32_t    SSIIM;         // + 0x014
  volatile uint32_t    SSIRIS;        // + 0x018
  volatile uint32_t    SSIMIS;        // + 0x01C
  volatile uint32_t    SSIICR;        // + 0x020
  volatile uint32_t    SSIDMACTL;     // + 0x024
} SPI_PERIPH; 


typedef struct {
  uint8_t     SlvBypassModeEn;        // Enable/Disable     
  uint8_t     EndOfTransEn;           // Enable/Disable
  uint8_t     SlvModeOutputDis;       // Enable/Disable
  uint8_t     MasSlvSelect;           // Enable/Disable
  uint8_t     SSPEn;                  // Enable/Disable
  uint8_t     LoopbackModeEn;         // Enable/Disable
  uint8_t     ClkPrescaleDiv;        
  uint8_t     SerialCPHA;             // Enable/Disable
  uint8_t     SerialCPOL;             // Enable/Disable
  uint8_t     FrameFormatSelect;      // Enable/Disable
  uint8_t     DataSizeSelect;         // Enable/Disable
  uint8_t     TxFifoIntMask;          // Enable/Disable
  uint8_t     RxFifoIntMask;          // Enable/Disable
  uint8_t     RxTimeoutIntMask;       // Enable/Disable
  uint8_t     RxOverrunIntMask;       // Enable/Disable
  uint8_t     TxDMAEn;                // Enable/Disable
  uint8_t     RxDMAEn;                // Enable/Disable
} SPI_CONFIG;


typedef struct {
	uint8_t instr;
	uint8_t addr_hi;
	uint8_t addr_low;
	uint8_t data;
} SPI_EEPROM_BYTE_CMD;

typedef struct {
	uint8_t instr;
} SPI_EEPROM_EN_CMD;

typedef struct {
	uint8_t instr;
	uint8_t data;
} SPI_EEPROM_CFG_CMD;

/* function prototypes -----------------------------------------*/
bool initializeSPI0(void);
void spiTx(uint8_t *dataIn, int size, uint8_t *dataOut);
uint8_t spi_eeprom_read_byte(uint16_t address);
uint8_t spi_eeprom_read_status(void);
void spi_eeprom_wait_write_in_progress(void);
void spi_eeprom_write_disable(void);
void spi_eeprom_write_enable(void);
void spi_eeprom_write_byte(uint16_t address, uint8_t value);

#endif
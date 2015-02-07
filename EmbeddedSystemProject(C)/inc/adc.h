#ifndef ADC_H__
#define ADC_H__

#include <stdint.h>
#include <stdbool.h>
#include "gpio.h"

#define RIGHT_POT       0
#define LEFT_POT        1



void initializeADC(void);
uint32_t GetADCval(uint32_t Channel);
//void updateRefreshRate(void);
void getDutyCycle(void);

#endif

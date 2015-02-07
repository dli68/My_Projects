#ifndef __LED_CHARS_H__
#define __LED_CHARS_H__

#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h> // random number
#include "gpio.h"

#define LED_3_ON ~(1 << 3)
#define LED_2_ON ~(1 << 2)
#define LED_1_ON ~(1 << 1)
#define LED_0_ON ~(1 << 0)
#define LED_7_ON ~(1 << 7)
#define LED_6_ON ~(1 << 6)
#define LED_5_ON ~(1 << 5)
#define LED_4_ON ~(1 << 4)

#define SMILE 2
#define DEPRESS 1
#define NORMAL 0

bool getLCDRow(int8_t row, uint8_t *lcdData, bool masterDevice, uint8_t number);
void getLCDRow_ColorMix(int8_t row, uint8_t *lcdData, char color, uint8_t number);

#endif

#ifndef __TIMER_H__
#define __TIMER_H__

#include <stdint.h>
#include <stdbool.h>
#include "lm4f120h5qr.h"

void SYSTICKIntHandler(void);
void initializeSysTick(uint32_t count, bool enableInterrupts);

void Timer0AHandler(void);
void initializeTimer0A(uint32_t count, uint32_t prescalar);

void initializeWatchdogTimer1(uint32_t count);

#endif

#ifndef __MAIN_H__
#define __MAIN_H__

#include "lm4f120h5qr.h"
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <string.h>
#include "UART.h"
#include "gpio.h"
#include "timer.h"
#include "adc.h"
#include "spi.h"
#include "led_chars.h"
#include "apps.h"



/******************************************************************************
 * Defines
 *****************************************************************************/
 
// Sets the value that will be loaded into the SysTick Timers Load Register.
#define SYSTICK_COUNT   1000    // 80000Hz, (1/80000)/(1/80e6)=1e3
// Sets the value that will be loaded into the TimerA0 Load Register.
#define TIMERA0_COUNT   1000    
// Sets the value that will be loaded into the TimerA0 prescalar Register.
#define TIMERA0_PRESCALAR 79
// Sets the value that will be loaded into the Watchdog Timer 0 Load Register.
#define WATCHDOGTIMER0_COUNT   400000000  // 5 /(1/80 000000) =  400000000 




#endif

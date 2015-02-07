#ifndef APPS_H__
#define APPS_H__

#include "main.h"

void masterApp(void);
void spi(bool read, char* current_gamer_name, int16_t new_score);
bool gameOverDec(void);
bool detectMaster(void);
void WatchDogClear(bool Masterdevice, bool check);
void update_score_on_slave(bool current, uint8_t number);
void start_game_fame(void);
void masterApp(void);
void slaveApp(void);

#endif

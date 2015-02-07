#include "inc/apps.h"

/******************************************************************************
 * Variables
 *****************************************************************************/
extern volatile bool gameEnd;

volatile uint16_t miss_count = 0;
volatile uint16_t hit_count = 0;
volatile uint16_t wait_count = 0;
volatile int16_t score = 0;
volatile bool OneSecond = false;
volatile bool WatchdogClear = false; // clear watchdog signal; used by both master & slave
volatile bool masterDevice = false;  // used by both master & slave
 
static bool gameActive = false; // used by slave only
static bool WatchdogEnable = false; // watchdog enalbe signal; used by both master & slave
static uint8_t last_score = 0;
static char cur_gamer_name[15];


/******************************************************************************
 * Functions
 *****************************************************************************/
//*****************************************************************************
// The spi function will be called when game starts. Specifically, it is responsible 
// for displaying last score on two boards; sending last gamer and score to UART0;
// storing new score and gamer's name. 
//***************************************************************************** 

void spi(bool read, char* current_gamer_name, int16_t new_score)
{
	// variable declaration
	uint16_t t = 0;
	char gamer_name[15];
	static char flag[2];

	if(read){
		// Read the flag from EEPROM to check if this board has been played before
		// Set the flag to all null chars first
		memset(flag, 0, 2);
		for(t=0;t<2;t++){
			flag[t]=(char)spi_eeprom_read_byte(t);
		}
		// Check the flag
		if(strncmp(flag, "DI", 2)){
			// The game has been played before on this board.
			// Read the last score stored in the EEPROM & print it on LED
			last_score = (uint8_t)spi_eeprom_read_byte(2);
			// Read the last gamer_name stored in the EEPROM & send it to UART0 along with last_score.
			// Set the string to all null chars first
			memset(gamer_name, 0, 15);
			for(t=0;t<15;t++){
				gamer_name[t]=(char)spi_eeprom_read_byte(t+3);
			}
			// Print out to the screen.
			printf("\n Last game was played by %s, and has score %u", gamer_name, last_score);	
			// Print out to slave LCD.
 			update_score_on_slave(false, last_score);
		}
		else{
			// The game has never been played before on this board.
			last_score = 0;
			printf("\n The game has never been played before on this board.");	
		}
	}
	else{
		// score and gamer's name will be written into EEPROM; if needed, also update the flag.
		// write in gamer's name
		for(t=0;t<15;t++){
			spi_eeprom_write_byte(t+3,(uint8_t)current_gamer_name[t]);
		} 
		// write in score
		spi_eeprom_write_byte(2,(uint8_t)new_score);
		// check if needed to update the flag
		if(!strncmp(flag, "DI", 2)){
			spi_eeprom_write_byte(0,(uint8_t)'I');
			spi_eeprom_write_byte(1,(uint8_t)'D');
		}
	}
}
//*****************************************************************************
// gameOverDec function; used to determine when to end the game & do post-game tasks.
//*****************************************************************************
bool gameOverDec(void)
{
	score = 5 + hit_count - 2 * miss_count;
	if (score <= 0 || gameEnd){
			// write score & gamer's name in spi.
  		//Normalize the score
			if(score < 0 ){
					spi(false, cur_gamer_name, 1);
			}
			else{
					spi(false, cur_gamer_name, score);
			}
			uartTx(UART_CMD_GAME_END, UART5);
			//Turn on depress face when score is worse; turn on smile face if score it better or equal (in reference to last score)
			if(last_score <= score)	
					faceOn(SMILE);
			else{
					faceOn(DEPRESS);
			}
			return true;
	}
	return false;
}
//*****************************************************************************
// detectMaster function: used to detect which device is master / slave.
//*****************************************************************************
 bool detectMaster(void)
 {
   char myChar = 0;
   
   while(1)
   {
     // Send discovery packets to U5
		 uartTx(UART_CMD_DISCOVERY, UART5);
     // Check for a discovery packert on U2
     myChar = uartRx(0, UART2);
     // If we recieved a discovery packert on U2, send a SLAVE_FOUND command
     // back on U2 and return false.  
     if( myChar ==  UART_CMD_DISCOVERY )
     {
       printf("SLAVE: TX UART_CMD_SLAVE_FOUND\n\r");
       uartTx(UART_CMD_SLAVE_FOUND, UART2);
       //This device is the slave
       return false;
     }
     
     // Check for a SLAVE_FOUND command on U5.  If you receive the SLAVE_FOUND
     // command, return true, else check everything again
     myChar = uartRx(0, UART5);
     if(myChar == UART_CMD_SLAVE_FOUND)
     {
       // This device is the master
       printf("MASTER: RX UART_CMD_SLAVE_FOUND\n\r");
       return true;
     }
    
    // If no messages are received on either of the UARTs,
    // wait 1 second before sending another discovery message
    printf("Discovery: Waiting 1 Second\n\r");     
    while(OneSecond == false){};
    printf("Discovery: Waiting Done\n\r");
    OneSecond=false;
  }
 }
//*****************************************************************************
// WatchDogClear function: used send watchdog clearing signal / clearing watch 
// dog on both boards( master / slave.)
//*****************************************************************************
void WatchDogClear(bool Masterdevice, bool check)
{
	//variables
	uint8_t delay;
	uint8_t times;
	char receive;
	// If master
	if(Masterdevice){
		//Send clear watchdog signal to the other board
		if(WatchdogClear && check){
			for(times = 0; times < 10; times++){
					uartTx(UART_CMD_WATCHDOG_CLEAR, UART5);
					// delay for a while for UART to transmit
					delay = 0;
					while(delay<5){
							delay++;
					}
			}	
			WatchdogClear = false;
		}
		if(!check){
			 // check if there is watch dog clear signal sent from slave
			 receive = uartRx(0, UART5);
			 if(receive == UART_CMD_WATCHDOG_CLEAR){
					WATCHDOG0_ICR_R = 5;
			 }
		}
	}
	// If slave
	else{
		if(!check){
			WATCHDOG0_ICR_R = 5;
		}
		if(check && WatchdogClear){
				 uartTx(UART_CMD_WATCHDOG_CLEAR, UART2);
				 WatchdogClear = false;
		}
	}
}
//*****************************************************************************
// update_score_on_slave function: used to handle communication btw two boards.
//*****************************************************************************
void update_score_on_slave(bool current, uint8_t number){
	char receive;
	if(masterDevice){
		// masterDevice calls this function to send current score.
		if(current){
				uartTx(score, UART5);
		}
		else if(receive < 100){				
			  uartTx(number, UART5);
		}
	}
	else{
		// slaveDevice calls this function to receive current score.	
		if(current){
				receive = uartRx(1, UART2);
				// send clear watchdog clearing signal.
				WatchDogClear(masterDevice, CHECKING);

				if(receive == UART_CMD_GAME_END){
						gameActive = false;
				}
				else if((receive == UART_CMD_WATCHDOG_CLEAR) && WatchdogEnable){
						WatchDogClear(masterDevice, false);
				}
				else{
						score = receive;
				}
		}
		else{
				receive = uartRx(0, UART2);
				if(receive == UART_CMD_GAME_START){
						gameActive = true;
				}			
				else if(receive < 100){
						score = receive;
				}	
		}
	}
}
//*****************************************************************************
// start_game_face function: used turn on welcome face for 5 seconds
//*****************************************************************************
void start_game_face(){
	//Turn on the normla face
	faceOn(NORMAL);
	spi(true, 0, 0);
	//Wait five seconds.
  OneSecond=false;
	while(OneSecond == false){
		faceUpdate();
	}
	OneSecond=false;
	while(OneSecond == false){
		faceUpdate();
	}
	OneSecond=false;
	while(OneSecond == false){
		faceUpdate();
	}
	OneSecond=false;
	while(OneSecond == false){
		faceUpdate();
	}
	OneSecond=false;
	while(OneSecond == false){
		faceUpdate();
	}
	//Turn off the face.
	faceOff(NORMAL);
}

//*****************************************************************************
// The master app operates the whole game. It sends score to slave board.  
// The master also listens to slave for watchdog clearing signal & send clearing 
// signal to slave board.
//
// The master also samples the ADC and dims the display based on the value
// read on the left most poteniometer (ADC0 Channel 1).
//*****************************************************************************

void masterApp(void)
{
//	uint32_t t = 0;
	//Turn on the welcome face for five seconds.
	start_game_face();
	
	// ask for gamer's name.
	printf("\n gamer_name: ");
	scanf("%15s", cur_gamer_name);
	// Send start UART_CMD_GAME_START to slave board
	uartTx(UART_CMD_GAME_START, UART5);

	// initialize master's watchdog
	initializeWatchdogTimer1(WATCHDOGTIMER0_COUNT);
	
  while(1)
  {	
		// Enter a critical section of code
    DisableInterrupts();
		// send clear watchdog clearing signal.
		WatchDogClear(masterDevice, CHECKING);
		// check if needed to clear the watchdog.
		WatchDogClear(masterDevice, CLEARING);
 		//for debug only
		getDutyCycle();
		examineButtons();
    MasterDisplayUpdate();
		update_score_on_slave(true, 0);
		if (gameOverDec()){
			break;
		} 
		// Exit critical section of code
    EnableInterrupts();
  }
	while(1){
			faceUpdate();
	}
}

//*****************************************************************************
// The slave board is used to update the score received from master board.   
// It also listens to slave for watchdog clearing signal & send clearing 
// signal to master board.
//
// The slave also samples ADC and change the display color based on the value
// read on both poteniometers.
//*****************************************************************************
void slaveApp(void)
{	
	while(1)
  {	
		// Enter a critical section of code
    DisableInterrupts();
		//Initialize slave's watchdog when game starts
		if((!WatchdogEnable) && gameActive){
				WatchdogEnable =  true;
				initializeWatchdogTimer1(WATCHDOGTIMER0_COUNT);
		}

		getDutyCycle();
		examineButtons();
		if(!gameActive){
				update_score_on_slave(false, 0);
		}
		else{
				update_score_on_slave(true, 0);
		}
    SlaveDisplayUpdate(score);
		// Exit critical section of code
    EnableInterrupts();
  }
}
 



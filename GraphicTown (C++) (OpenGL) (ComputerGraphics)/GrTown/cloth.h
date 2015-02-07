#ifndef CLOTH
#define CLOTH

#include "TownViewWidget.H"

//Set up variables
bool CLOTH_INIT();

//Reset the cloth
void RESET();

//Perform per-frame updates
void UPDATE();

//Render a frame
void DRAW();

#endif	//CLOTH
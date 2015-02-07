/************************************************************************/
/* FILE NAME    - circular_buffer.c                                     */
/* AUTHOR       - ECE353 Staff                                          */
/* DATE CREATED - 26-Feb-2014                                           */
/* DESCRIPTION  -                                                       */
/*                                                                      */
/* (c) ECE Department, University of Wisconsin - Madison                */
/************************************************************************/
#include "inc/circular_buffer.h"



/******************************************************************************
 * cBufInit
 *****************************************************************************/
int32_t		cBufInit(CircularBuffer *bufPtr, int16_t size)
{
		// check parameter validity 
		// pointer to the CircularBuffer should not be NULL
		// size should be a positive integer  
		if (size <= 0 || bufPtr == NULL)
			return -1;
		
		// locate a buffer in heap of given size of charactors
		bufPtr->buffer = malloc(size * sizeof(char));
		// check malloc success, pointer not NULL
		if (bufPtr->buffer == NULL)
			return -1;
		
		// initalize elements in struct
		bufPtr->size = size; // buffer size set to the given size
		bufPtr->count = 0; // initial counter, empty buffer			
		bufPtr->tailIndex = 0; // always point to the oldest element in the buffer
		return 0; 
			
}

/******************************************************************************
 * cBufAddChar
 *****************************************************************************/
int32_t		cBufAddChar(CircularBuffer *bufPtr, char c)
{
    // check parameter validity 
		// pointer to the CircularBuffer should not be NULL
		// input element should have size of one byte (8-bit)  
		if (sizeof(c) != sizeof(char) || bufPtr == NULL)
			return -1;
		
		// check whether buffer is full
		if (bufPtr->count == bufPtr->size){ // full
			*(bufPtr->buffer + bufPtr->tailIndex) = c; // put c into the buffer 
			// counter remain the same as size
			if (bufPtr->tailIndex < (bufPtr->size - 1))
				bufPtr->tailIndex += 1; // point to the next oldest
			else 
				bufPtr->tailIndex = 0; // point back to head of the buffer, wrap up
			
			return 1;
		}
		else { // not full
			// put c into the buffer
			*(bufPtr->buffer + (bufPtr->tailIndex + bufPtr->count) % bufPtr->size) = c;  
			bufPtr->count += 1; // add one char, counter increment by 1			
			// bufPtr->tailIndex unchange
			
			return 0;
    }
	
}

/******************************************************************************
 * cBufGetChar:
 *****************************************************************************/
int32_t		cBufGetChar(CircularBuffer *bufPtr, char *retChar)
{
    // check parameter validity 
		// pointer to the CircularBuffer should not be NULL
		// pointer to the retChar should not be NULL 
		if (retChar == NULL || bufPtr == NULL)
			return -1;
		
		// if buffer is empty, return a positive number
		if (bufPtr->count == 0)
			return 1;
		
		// return the oldest char in the buffer
		*retChar = *(bufPtr->buffer + bufPtr->tailIndex);
		bufPtr->count -= 1; // remove one char, count decrement by 1
		if (bufPtr->tailIndex < (bufPtr->size - 1))
				bufPtr->tailIndex += 1; // point to the next oldest
		else 
				bufPtr->tailIndex = 0; // point back to head of the buffer, wrap up
    return 0;
}

/******************************************************************************
 * cBufGetFreeCount
 *****************************************************************************/
int32_t		cBufGetFreeCount(CircularBuffer *bufPtr)
{
		// check parameter validity 
		// pointer to the CircularBuffer should not be NULL  
		if (bufPtr == NULL)
			return -1;
	
		// if valid, return the number of unused entries
		return (bufPtr->size - bufPtr->count);
}


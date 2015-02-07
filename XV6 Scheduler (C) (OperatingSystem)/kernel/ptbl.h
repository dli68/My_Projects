#ifndef _PTBL_H_
#define _PTBL_H_
#include "spinlock.h"
#include "proc.h"

struct ptbl {
   struct spinlock lock;
   struct proc proc[NPROC];
};
#endif

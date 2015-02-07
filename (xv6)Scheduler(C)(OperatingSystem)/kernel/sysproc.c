#include "types.h"
#include "x86.h"
#include "defs.h"
#include "param.h"
#include "mmu.h"
#include "proc.h"
#include "sysfunc.h"
#include "pstat.h"
#include "ptbl.h"

int
sys_fork(void)
{
  return fork();
}

int
sys_exit(void)
{
  exit();
  return 0;  // not reached
}

int
sys_wait(void)
{
  return wait();
}

int
sys_kill(void)
{
  int pid;

  if(argint(0, &pid) < 0)
    return -1;
  return kill(pid);
}

int
sys_getpid(void)
{
  return proc->pid;
}

int
sys_sbrk(void)
{
  int addr;
  int n;

  if(argint(0, &n) < 0)
    return -1;
  addr = proc->sz;
  if(growproc(n) < 0)
    return -1;
  return addr;
}

int
sys_sleep(void)
{
  int n;
  uint ticks0;
  
  if(argint(0, &n) < 0)
    return -1;
  acquire(&tickslock);
  ticks0 = ticks;
  while(ticks - ticks0 < n){
    if(proc->killed){
      release(&tickslock);
      return -1;
    }
    sleep(&ticks, &tickslock);
  }
  release(&tickslock);
  return 0;
}

// return how many clock tick interrupts have occurred
// since boot.
int
sys_uptime(void)
{
  uint xticks;
  
  acquire(&tickslock);
  xticks = ticks;
  release(&tickslock);
  return xticks;
}

int sys_reserve(void) {
   int perc;
   int sum = 0;
   struct proc *p;
   
   if (argint(0, &perc) < 0) {
      return -1;
   }
   if (perc <= 0 || perc > 100) {
      return -1;
   }

   for (p = pt->proc; p < &pt->proc[NPROC]; p++)
      if(p->state != UNUSED && p->type == RESERVED)
         sum += p->perc;
   if (perc + sum - proc->perc > 200) {
      return -1;
   }
   
   proc->perc = perc;
   proc->type = RESERVED;
   proc->bid = 100;
   return 0;
}

int sys_spot(void) {
   int bid;
   if (argint(0, &bid) < -1) {
      return -1;
   }
   if (bid < 0) {
      return -1;
   }
   proc->type = SPOT;
   proc->bid = bid;
   proc->perc = 0;
   return 0;
}

int sys_getpinfo(void) {
   int i;
   struct pstat *ps;
   int x;

   if (argint(0, &x) < -1)
      return -1;

   ps = (struct pstat*) x;
   
   for (i = 0; i < NPROC; i++) {
      ps->inuse[i] = pst->inuse[i];
      ps->pid[i] = pst->pid[i];
      ps->chosen[i] = pst->chosen[i];
      ps->time[i] = pst->time[i];
      ps->charge[i] = pst->charge[i];
   }
   return 0;
}


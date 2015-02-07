#include "types.h"
#include "stat.h"
#include "user.h"

int main(int argc, char *argv[]) {
   int i;
   struct pstat p;
   getpinfo(&p);
   for(i = 0; i < NPROC; i++){
      if (p.inuse[i] == 1){
         printf(1,"Process %d:\n", i);
         printf(1,"In Use: %d\n",p.inuse[i]);
         printf(1,"Pid: %d\n",p.pid[i]);
         printf(1,"Chosen: %d\n",p.chosen[i]);
         printf(1,"Time: %d\n",p.time[i]);
         printf(1,"Charge: %d\n",p.charge[i]);
         printf(1,"\n");
      }
   }
   exit();
}

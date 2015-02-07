#include "types.h"
#include "stat.h"
#include "user.h"

int main(int argc, char *argv[]) {
   int rc = fork();
   if (rc == 0) {
      fork();
      reserve(49);
   } else {
      printf(1,"big pid %d", getpid());
      reserve(100);
   }
   while(1){ }
   exit();
}

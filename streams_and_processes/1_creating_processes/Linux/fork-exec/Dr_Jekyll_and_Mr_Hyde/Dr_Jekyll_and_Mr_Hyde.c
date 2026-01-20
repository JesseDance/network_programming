/*
   One process can execute several different programs.
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
   int mypid;

   mypid = getpid();
   printf("My pid = %d and I wrote this story.\n", mypid);

   execl("./Dr_Jekyll", "Dr_Jekyll", argv[argc-1], (char*)NULL);
   perror("execl failed");
   exit(1);

   return 0;
}

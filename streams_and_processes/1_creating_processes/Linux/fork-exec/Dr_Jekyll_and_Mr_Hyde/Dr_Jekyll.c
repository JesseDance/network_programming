/*
   One process can execute several different programs.
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
   int mypid, count = 0;

   mypid = getpid();
   printf("My pid = %d and I'm Dr. Jekyll.\n", mypid);

   count = atoi( argv[argc-1] );
   if ( 0 < count )
   {
      sprintf(argv[argc-1], "%d", count - 1);
      execl("./Mr_Hyde", "Mr_Hyde", argv[argc-1], (char*)NULL);
      perror("execl failed");
      exit(1);
   }

   return 0;
}

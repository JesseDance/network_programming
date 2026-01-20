/*
   One process can execute several different programs.
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char *argv[])
{
   int mypid, fork_rv;

   mypid = getpid();
   printf("My pid = %d and I wrote this story.\n", mypid);

   fork_rv = fork();         /* create new process */

   if ( -1 == fork_rv )      /* check for error */
   {
      perror("fork failed");
   }
   else if ( 0 == fork_rv )  /* child */
   {
      execl("./Dr_Jekyll", "Dr_Jekyll", argv[argc-1], (char*)NULL);
      perror("execl failed");
      exit(1);
   }
   else                      /* parent */
   {
      wait(NULL);
      printf("My pid = %d and the story is over.\n", mypid);
   }

   return 0;
}

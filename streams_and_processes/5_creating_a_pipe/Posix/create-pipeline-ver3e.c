/*
   This program implements the (incorrect) algorithm described
   in the file create-pipeline-ver3e.txt.

   This version puts the first child (on which the parent process
   is waiting) on the wrong end of the pipeline.
*/
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{
   int fork_rv;

   fork_rv = fork();       /* Step 1a: create new process */
   if ( fork_rv == -1 )    /* check for error */
   {
      perror("fork");
   }
   else if ( fork_rv > 0 ) /* parent */
   {
      /* Step 1b: wait on child 1 */
      int wait_rv;
      printf("pid=%d is the parent of child 1 with pid=%d\n", getpid(), fork_rv);
      wait_rv = wait(NULL);
      printf("pid=%d is done waiting for %d. Wait returned: %d\n", getpid(), fork_rv, wait_rv);
      printf("The pipeline is done with its work.\n");
   }
   else  /* child 1 */
   {
      int fork_rv;
      int pipefd[2]; /* the pipe */

      printf("pid=%d is child 1\n", getpid());

      /* Step 2: create a pipe */
      if ( pipe(pipefd) == -1 )
      {
         perror("pipe");
      }
      printf("pid=%d got a pipe! It is file descriptors: { %d %d }\n", getpid(), pipefd[0], pipefd[1]);

      fork_rv = fork();     /* Step 3: create new process */
      if ( fork_rv == -1 )  /* check for error */
      {
         perror("fork");
      }
      else if ( fork_rv > 0 ) /* parent */
      {
         char *arglist[2];
         printf("pid=%d is the parent of child 2 with pid=%d\n", getpid(), fork_rv);

         /* Step 4: child 1 calls close(3), close(1), dup(4), close(4) */
         close(3);
         close(1);
         dup(4);
         close(4);

         /* Step 6 */
         arglist[0] = "cat";
         arglist[1] = "create-pipeline-ver1.txt";
         arglist[2] = 0 ;
         execvp( "cat" , arglist );
         perror("execvp cat");
         exit(1);
      }
      else  /* child 2 */
      {
         char *arglist[3];

         printf("pid=%d is child 2\n", getpid());

         /* Step 5: child 2 calls close(4), close(0), dup(3), close(3) */
         close(4);
         close(0);
         dup(3);
         close(3);

         /* Step 6 */
         arglist[0] = "more";
         arglist[1] = 0 ;
         execvp( "more" , arglist );
         perror("execvp more");
         exit(1);
      }
   }
   return 0;
}

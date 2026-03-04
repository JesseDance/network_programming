/*
   This program implements the (incorrect) algorithm described in the file
   create-pipeline-ver2e.txt.

   The parent process creates the pipe and the first child process.
   Then the first child creates the second child process.

   This version puts the first child (on which the parent process
   is waiting) on the wrong end of the pipeline.
*/
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{
   int pipefd[2]; /* the pipe */
   int fork_rv;

   /* Step 1: create a pipe */
   if ( pipe(pipefd) == -1 )
   {
      perror("pipe");
   }
   printf("pid=%d got a pipe! It is file descriptors: { %d %d }\n", getpid(), pipefd[0], pipefd[1]);

   fork_rv = fork();    /* Step 2: create new process */
   if ( fork_rv == -1 ) /* check for error */
   {
      perror("fork");
   }
   else if ( fork_rv > 0 )  /* parent */
   {
      int wait_rv;

      printf("pid=%d is the parent of child 1 with pid=%d\n", getpid(), fork_rv);
      /* Step 3: parent calls close() on its pipe descriptors 3 and 4 */
      close(3);
      close(4);
      wait_rv = wait(NULL);
      printf("pid=%d is done waiting for %d. Wait returned: %d\n", getpid(), fork_rv, wait_rv);
      printf("The pipeline is done with its work.\n");
   }
   else  /* child 1 */
   {
      int fork_rv;

      fork_rv = fork();    /* Step 4: create new process */
      if ( fork_rv == -1 ) /* check for error */
      {
         perror("fork");
      }
      else if ( fork_rv > 0 ) /* parent */
      {
         char *arglist[3];

         printf("pid=%d is the parent of child 2 with pid=%d\n", getpid(), fork_rv);

         printf("pid=%d is child 1\n", getpid());
         /* Step 5: child 1 calls close(3), close(1), dup(4), close(4) */
         close(3);
         close(1);
         dup(4);
         close(4);

         /* Step 7 */
         arglist[0] = "cat";
         arglist[1] = "create-pipeline-ver1.txt";
         arglist[2] = 0 ;
         execvp( "cat" , arglist );
         perror("execvp cat");
         exit(1);
      }
      else /* child 2 */
      {
         char *arglist[2];

         printf("pid=%d is child 2\n", getpid());
         /* Step 6: child 2 calls close(4), close(0), dup(3), close(3) */
         close(4);
         close(0);
         dup(3);
         close(3);

         /* Step 7 */
         arglist[0] = "more";
         arglist[1] = 0 ;
         execvp( "more" , arglist );
         perror("execvp more");
         exit(1);
      }
   }
   return 0;
}

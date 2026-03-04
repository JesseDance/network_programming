/*
   Create the following child to child pipeline.

                       parent
                   +------------+
                   |            |
        +--------->> 0        1 >>---------------------------+---->
        |          |            |                            |
        |          |          2 >>------------------------+--|----->
        |          |            |                         |  |
    ----+          +------------+                         |  |
        |                                                 |  |
        |                                                 |  |
        |       child_1                    child_2        |  |
        |   +------------+              +------------+    |  |
        |   |            |     pipe     |            |    |  |
        +-->> 0        1 >>--0======0-->> 0        1 >>------+
            |            |              |            |    |
            |          2 >>---+         |          2 >>---+
            |            |    |         |            |    |
            +------------+    |         +------------+    |
                              |                           |
                              +---------------------------+
*/
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

#define  STDIN_PIPE  0 /* pipe index for the fd to read from */
#define STDOUT_PIPE  1 /* pipe index for the fd to write to  */

int main()
{
   int pipefd[2]; /* the pipe */
   int fork_rv1;

   /* Step 1: create a pipe */
   if ( pipe(pipefd) == -1 )
   {
      perror("pipe");
   }

   fork_rv1 = fork();    /* Step 2: create new process */
   if ( fork_rv1 == -1 ) /* check for error */
   {
      perror("fork");
   }
   else if ( fork_rv1 > 0 )  /* parent */
   {
      int fork_rv2;

      fork_rv2 = fork();    /* Step 3: create another new process */
      if ( fork_rv2 == -1 ) /* check for error */
      {
         perror("fork");
      }
      else if ( fork_rv2 > 0 ) /* parent */
      {
         /* Step 4: parent calls close() on its pipe descriptors 3 and 4 */
         close( pipefd[ STDIN_PIPE] );  /* pipefd[0] == 3 */
         close( pipefd[STDOUT_PIPE] );  /* pipefd[1] == 4 */
         /* wait on both child processes */
         wait(NULL);
         wait(NULL);
      }
      else /* child 2 */
      {
         /* Step 6: child 2 calls close(4), close(0), dup(3), close(3) */
         close( pipefd[STDOUT_PIPE] ); /* pipefd[1] == 4 */
         close(STDIN_FILENO);          /* STDIN_FILENO == 0 */
         dup( pipefd[STDIN_PIPE] );    /* pipefd[0] == 3 */
         close( pipefd[STDIN_PIPE] );  /* pipefd[0] == 3 */

         /* Step 7: child 2 becomes the second stage program */
         execlp("./to_upper_case", NULL);
         perror("execlp child2");
         exit(1);

      }
   }
   else  /* child 1 */
   {
      /* Step 5: child 1 calls close(3), close(1), dup(4), close(4) */
      close( pipefd[STDIN_PIPE] );   /* pipefd[0] == 3 */
      close(STDOUT_FILENO);          /* STDOUT_FILENO == 1 */
      dup( pipefd[STDOUT_PIPE] );    /* pipefd[1] == 4 */
      close( pipefd[STDOUT_PIPE] );  /* pipefd[1] == 4 */

      /* Step 7: child 1 becomes the first stage program */
      execlp("./double", NULL);
      perror("execlp child1");
      exit(1);
   }
   return 0;
}

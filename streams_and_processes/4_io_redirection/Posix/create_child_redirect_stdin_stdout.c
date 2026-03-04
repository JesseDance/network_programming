/*
   This file shows how to create a child process that
   has its standard input and standard output streams
   redirected to files (and shares stderr with its parent)..

   When this program runs, the streams will look like this.

           create_child_redirect_stdin_stdout
              +-------------+
              |             |
        ----->> 0         1 >>------------------------>
              |             |
              |           2 >>-------------------+-->
              |             |                    |
              +-------------+                    |
                                                 |
                    double.exe                   |
                 +-------------+                 |
                 |             |                 |
   double.c ---->> 0         1 >>--> result.txt  |
                 |             |                 |
                 |           2 >>----------------+
                 |             |
                 +-------------+

   Notice how it is the child process that redirects its own standard
   streams. Of course, this is done after the fork() but before the
   exec(). So the "real" child process is not the one doing the I/O
   redirection. The key idea is that after the fork(), but before the
   exec(), we are still running "our" code. So we can do anything we
   want to prepare the process before we exec the real child program.
*/
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/wait.h>

int main()
{
   int   fd;
   int   pid ;
   int   status = -1;

   if ( (pid = fork()) == -1 )
   {
      perror("fork");
   }
   else if ( pid == 0 ) /* child process */
   {
      /* use the close-open technique to redirect stdin and stdout */
      /* redirect standard input to a file */
      close(STDIN_FILENO);                /* close the current stdin */
      fd = open("double.c", O_RDONLY);    /* fd should be 0 */
      if ( fd != STDIN_FILENO )           /* fd should be 0, i.e., stdin */
      {
         fprintf(stderr,"Could not open() fd as 0\n");
         exit(1);
      }

      /* redirect standard output to a file */
      close(STDOUT_FILENO);                  /* close the current stdout */
      fd = open("result.txt", O_CREAT|O_WRONLY);          /* fd should be 1 */
    //fd = open("result.txt", O_CREAT|O_WRONLY, S_IRWXU); /* fd should be 1 */
    //fd = open("result.txt", O_CREAT|O_RDWR);            /* fd should be 1 */
      if ( fd != STDOUT_FILENO )            /* fd should be 1, i.e., stdout */
      {
         fprintf(stderr,"Could not open() fd as 1\n");
         exit(1);
      }

      /* now exec the "real" child process */
      execl("./double", "double", (char*)NULL);
      perror("execl: cannot execute command");
      exit(1);
   }
   else /* parent process */
   {
      /* wait for the child processes to end */
      if ( wait(&status) == -1 )
      {
         perror("wait");
      }
   }
   return status;
}

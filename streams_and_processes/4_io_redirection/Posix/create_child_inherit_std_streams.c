/*
   This file shows how to create a child process that inherits
   its standard streams from its parent process.

                     parent
                +------------+
                |            |
           +--->> 0        1 >>------+
           |    |            |       |
           |    |          2 >>-->   |
           |    |            |       |
           |    +------------+       |
        ---+                         +----->
           |            child        |
           |       +------------+    |
           |       |            |    |
           +------>> 0        1 >>---+
                   |            |
                   |          2 >>-->
                   |            |
                   +------------+

   This is actually the default behavior in Linux so if you
   just create a child, it automatically inherits all three of
   its parent's standard streams.

   Be sure to run this program with its standard input and output
   redirected to files.

      > create_child_inherit_std_streams < double.c > result.txt

   Notice how the child also has its standard input and output
   redirected (inherited) to the files.
*/
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int main()
{
   int   pid ;
   int   status = -1;

   if ( (pid = fork()) == -1 )
   {
      perror("fork");
   }
   else if ( pid == 0 ) /* child process */
   {
      execl("./double", "double", (char*)NULL);
      perror("cannot execute command");
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

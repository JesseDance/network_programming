/*
   This program creates a child process and shares its
   stdin and stdout streams with that child (which is
   the default behavior for processes in Linux).

   But this pair of processes was designed to show what
   happens when two processes share an output stream. So
   neither process in the pair makes any use of the shared
   input stream.

                   parent
              +------------+
              |            |
          --->> 0        1 >>-------+
              |            |        |
              |          2 >>-->    |
              |            |        |
              +------------+        |
                                    +----> result.txt
                      child         |
                 +------------+     |
                 |            |     |
          ------>> 0        1 >>----+
                 |            |
                 |          2 >>-->
                 |            |
                 +------------+

   Run this program and see how the output is intermixed
   (parent output is lower case, child output is uppercase).

   Be sure to run this program with its standard output
   redirected to a file.

      > parent_child_share_stdout > result.txt

   Notice how the child also has its standard output
   redirected (inherited) to the file.
*/
#include <unistd.h>
#include <stdio.h>

int main()
{
   pid_t fork_rv;
   int count = 20;
   int i;

   fork_rv = fork();    /* create the child process */

   if ( fork_rv < 0 ) /* check for error   */
   {
      perror("fork");
   }
   else if ( fork_rv == 0 ) /* child process */
   {
      for (i = 0; i < count; i++)
      {
         fprintf(stdout, "THIS IS OUTPUT FORM THE CHILD PROCESS\n" );
      }
   }
   else /* parent process */
   {
      for (i = 0; i < count; i++)
      {
         fprintf(stdout, "this is output form the parent process\n" );
      }
   }

   return 0;
}

/*
   This program creates a child process and shares its
   stdin and stdout streams with that child.

               very_strange_child
                +------------+
                |            |
           +--->> 0        1 >>-------+
           |    |            |        |
           |    |          2 >>-->    |
           |    |            |        |
           |    +------------+        |
        ---+                          +----->
           |           child          |
           |       +------------+     |
           |       |            |     |
           +------>> 0        1 >>----+
                   |            |
                   |          2 >>-->
                   |            |
                   +------------+

   This program terminates immediately after creating the child!
   Run this program from the bash command-line and then try to
   use the command-line right after this program terminates.
   What happens? Explain.
   (The following picture is a hint.)

                  bash
             +------------+
             |            |
    ----+--->> 0        1 >>----------+----->
        |    |            |           |
        |    |          2 >>-->       |
        |    |            |           |
        |    +------------+           |
        |                             |
        |                             |
        |      very_strange_child     |
        |       +------------+        |
        |       |            |        |
        +------>> 0        1 >>-------+
        |       |            |        |
        |       |          2 >>-->    |
        |       |            |        |
        |       +------------+        |
        |                             |
        |                             |
        |              child          |
        |          +------------+     |
        |          |            |     |
        +--------->> 0        1 >>----+
                   |            |
                   |          2 >>--->
                   |            |
                   +------------+
*/
#include <unistd.h>
#include <stdio.h>

int main()
{
   pid_t fork_rv;
   char c;

   fork_rv = fork();    /* create the child process */

   if ( fork_rv < 0 ) /* check for error   */
   {
      perror("fork");
   }
   else if ( fork_rv == 0 ) /* child process */
   {
      while( (c = getchar()) != EOF )
         if ( c != 10 && c != 13 )
            printf("%c", c-32);
         else
            printf("%c", c);

      fprintf(stderr,"child eof");
   }
   else /* parent process */
   {
      /* don't wait for the child processes */
   }

   return 0;
}

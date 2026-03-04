/*
   This program creates a child process and shares its
   stdin and stdout streams with that child (which is
   the default behavior for processes in Linux).

                    parent
                +-------------+
                |             |
           +--->> 0         1 >>-------+
           |    |             |        |
           |    |           2 >>-->    |
           |    |             |        |
           |    +-------------+        |
        ---+                           +----->
           |            child          |
           |       +-------------+     |
           |       |             |     |
           +------>> 0         1 >>----+
                   |             |
                   |           2 >>-->
                   |             |
                   +-------------+

   But this pair of processes was designed to show what
   happens when two processes share an input stream. So
   both of these two processes use getchar() to read from
   the single shared input stream. The parent process just
   echoes what it reads from stdin to stdout. The child
   process converts what it reads from stdin to uppercase
   and then writes it to stdout (that way you can easily
   tell which of the two processes read the input character).

   What is important to realize is that any given character
   in the shared input stream is sent to only ONE of the two
   processes that share the stream (and this includes the ^D
   that denotes the end of input from the keyboard).

   Be sure to run this program with its standard input
   redirected to a file.

      > parent_child_share_stdin < double.c

   Notice how the child also has its standard input
   redirected (inherited) to the file.
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
      while( (c = getchar()) != EOF )
         printf("%c", c);

      fprintf(stderr,"parent eof");

      /* wait for the child processes to end */
      wait(NULL);
   }

   return 0;
}

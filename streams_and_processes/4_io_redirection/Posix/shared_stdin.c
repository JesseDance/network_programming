/*
   http://www.usna.edu/Users/cs/aviv/classes/ic221/s16/lec/18/lec.html
*/
#include <stdio.h>
#include <unistd.h>

int main()
{
   pid_t fork_rv;
   char c;

   fork_rv = fork();

   if ( fork_rv < 0 )     /* error */
   {
      perror("fork");
      _exit(1);
   }
   else if( fork_rv == 0 ) /* child */
   {
      // read 1 byte at a time from stdin
      while ( read(STDIN_FILENO, &c, 1) > 0)
      {
         printf("c: %c\n", c);
      }
   }
   else if ( fork_rv > 0) /* parent */
   {
      // read 1 byte at a time from stdin
      while ( read(STDIN_FILENO, &c, 1) > 0 )
      {
         printf("p: %c\n", c);
      }
   }
   return 0;
}

/*
   This program reads one character at a time from standard input,
   and then writes each character twice to standard output.
*/
#include <stdio.h>

int main()
{
   int c;
   while ( (c = getchar()) != EOF )
   {
      if ( c != 10 && c != 13 )
      {
         printf("%c%c", c, c);
      }
      else
      {
         printf("%c", c);
      }
   }
   return 0;
}

/*
   This program reads one character at a time from standard input,
   and then writes each character twice to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
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

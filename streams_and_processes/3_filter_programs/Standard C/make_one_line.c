/*
   This program concatenates all the lines of its input
   into one single output line.

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
      if ( c == 10 || c == 13 ) // replace LF or CR with space
      {
         c = ' ';
      }
      printf("%c", c);
    //fflush(stdout);
   }
   return 0;
}

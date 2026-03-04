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
      if ( c != 10 && c != 13 )  // don't double LF or CR
      {
         printf("%c", c);  // print once
      }
      printf("%c", c);     // and (maybe) print again
   }
   return 0;
}

/*
   This program reads one character at a time from standard input,
   converts all lower case letters to upper case, and writes them
   to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>

int main( )
{
   int c;
   while ( (c = getchar()) != EOF )
   {
      if ( 'a' <= c && c <= 'z' )
      {
         printf("%c", c - 32);
      }
      else
      {
         printf("%c", c);
      }
   }
   return 0;
}

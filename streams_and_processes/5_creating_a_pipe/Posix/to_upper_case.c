/*
   This program reads one character at a time from standard input,
   converts each lower case character to upper case, and writes
   each character to standard output.
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

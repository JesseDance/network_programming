/*
   This program is meant to share its stdin stream with its
   parent process.

   The purpose of this pair of programs is to demonstrate
   what happens when two processes share an input stream.
*/
#include <stdio.h>

int main( )
{
   char c;
   while( (c = getchar()) != EOF )
      if ( c != 10 && c != 13 )
         printf("%c", c-32);
      else
         printf("%c", c);
   return 0;
}
/*
   This program reads lines from standard input, removes
   all the vowels, and writes the rest to standard output.

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
      if ( c != 'a'
        && c != 'e'
        && c != 'i'
        && c != 'o'
        && c != 'u'
        && c != 'A'
        && c != 'E'
        && c != 'I'
        && c != 'O'
        && c != 'U' )
      {
         printf("%c", c);  // echo the non-vowels
      }
   }
   return 0;
}

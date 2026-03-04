/*
   This program reads one character at a time from standard input,
   shifts the character by N places in the ASCII table, and then
   writes the new character to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
   int c;
   int n = 1;   // default value for n
   // Check for a command line argument.
   if (argc > 1)
   {
      n = atoi(argv[1]);
      if (n <= 0) n = 1;
   }

   while ( (c = getchar()) != EOF )
   {
      if ( c != 10 && c != 13 ) // don't change LF or CR
      {
         c += n;
      }
      printf("%c", c);
   }
   return 0;
}

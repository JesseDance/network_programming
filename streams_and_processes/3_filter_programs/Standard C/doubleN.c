/*
   This program reads one character at a time from standard input,
   and then writes each character N times to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
   int i;
   int c;
   int n = 2;  // default value for n
   // Check for a command line argument.
   if (argc > 1)
   {
      n = atoi(argv[1]);
      if (n <= 0) n = 2;
   }

   while ( (c = getchar()) != EOF )
   {
      if ( c != 10 && c != 13 )  // don't duplicate LF or CR
      {
         for (i = 0; i < n - 1; i++)
         {
            printf("%c", c);
         }
      }
      printf("%c", c);
   }
   return 0;
}

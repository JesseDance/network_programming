/*
   This program reads characters from standard input (stdin)
   and echoes them to standard output (stdout).

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
      printf("%c", c);
   }
   return 0;
}

/*
   This program counts the number of lines in its standard
   input and writes the resulting int to its standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>

int main()
{
   int lineCount = 0;
   char oneLine [1000];

   while ( fgets(oneLine, 1000, stdin) != NULL )
   {
      lineCount++;
   }
   printf("%d", lineCount);

   return 0;
}

/*
   This program reads lines from standard input and
   echos them to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>

int main()
{
   char oneLine [1000];

   while ( fgets(oneLine, 1000, stdin) != NULL )
   {
      fputs(oneLine, stdout);
    //fflush(stdout);
   }
   return 0;
}

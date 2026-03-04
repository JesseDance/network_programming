/*
   This program reads lines from standard input and
   writes their reverse to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>

int main()
{
   char oneLine [1000];
   int i, j;

   while ( fgets(oneLine, 1000, stdin) != NULL )
   {
      // find the end of line
      for (i = 0; i < 1000; i++)
      {
         if ( oneLine[i] == '\0' || oneLine[i] == '\n' )
         {
            break;
         }
      }
      // write the reversed line
      for (j = i-1; j >= 0; j--)
      {
         printf("%c", oneLine[j]);
      }
      printf("\n");
    //fflush(stdout);
   }
   return 0;
}

/*
   This program reads two characters at a time from
   standard input, and then writes the two characters
   to standard output in their reverse order.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
#include <stdio.h>

int main()
{
   char c1 = 1, c2 = 1;
   while( c2 != EOF && (c1 = getchar()) != EOF )
   {
      if ( c1 == 10 || c1 == 13 ) // don't twidle LF or CR
      {
         printf("%c", c1);
       //fflush(stdout);
      }
      else if ( (c2 = getchar()) != EOF )
      {
         if ( c2 == 10 || c2 == 13 ) // don't twidle LF or CR
         {
            printf("%c%c", c1, c2);
          //fflush(stdout);
         }
         else
         {
            printf("%c%c", c2, c1); // twidle c1 and c2
         }
      }
      else // reading c2 caused EOF
      {
         printf("%c", c1);
      }
   }
   return 0;
}

/*
   This program demonstrates a simple use of environment variables
   (and also argc and argv).
*/
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
   int i;
   char* value;

   // walk the argv array and look for environment variables
   for (i = 1; i < argc; i++)
   {
      value = getenv( argv[i] );
      if ( ! value )
      {
         printf( "%s is not an enviromnent variable.\n", argv[i] );
      }
      else
      {
         printf( "The value of %s is %s.\n", argv[i], value );
      }
   }

   return 0;
}//main
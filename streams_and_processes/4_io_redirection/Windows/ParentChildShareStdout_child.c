/*
   This program is meant to share its stdout stream with its
   parent process.

   The purpose of this pair of programs is to demonstrate
   what happens when two processes share an output stream.
*/
#include <stdio.h>

int main( )
{
   int count = 20;
   int i;
   for (i = 0; i < count; i++)
   {
      fprintf(stdout, "THIS IS OUTPUT FORM THE CHILD PROCESS\n" );
   }
   return 0;
}
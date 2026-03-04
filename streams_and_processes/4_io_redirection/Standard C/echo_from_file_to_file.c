/*
   This program shows how a C program can open one file for
   input and another file for output and then just copy every
   character from the input file to the output file. When this
   program is running, it has the following five streams.

            echo_from_file_to_file.exe
              +------------------+
              |                  |
  keyboard--->> stdin     stdout >>------+---console window
              |                  |       |
              |           stderr >>------+
              |                  |
              |                  |
Readme.txt--->> f1            f2 >>-----result.txt
              |                  |
              +------------------+

   Notice that this program does exactly the same thing as

   C:\> redirect_standard_streams.exe

   This program also does the same thing as

   C:\> echo.exe < redirect_standard_streams.c > result.txt
*/
#include <stdio.h>

int main( )
{
   /* give this process two new file streams */
   FILE* f1 = fopen("Readme.txt", "r");
   FILE* f2 = fopen("result.txt", "w");

   int c;
   while ( (c = getc(f1)) != EOF )
   {
      fprintf(f2, "%c", c);
   }

   /* close the files */
   fclose(f1);
   fclose(f2);

   return 0;
}

/*
   This file shows how a C program can redirect
   its standard input & output streams to files.
   The Standard C function that does this is:
      freopen()

   After this program redirects stdin & stdout, it just
   reads characters from stdin and echoes them to stdout.

   After the I/O redirection, this program's standard
   streams are connected as in the following picture.

            redirect_standard_streams.exe
              +-----------------+
              |                 |
Readme.txt--->> stdin    stdout >>-----result.txt
              |                 |
              |          stderr >>-----console window
              |                 |
              +-----------------+

   Be sure you understand what happens when you execute the
   following command-line. Notice that in this command-line,
   the shell program does I/O redirection of the standard
   streams for this program and then this program does its
   own redirection of its own streams!

   C:\> redirect_standard_streams.exe < echo.c > something.txt


   Important: Notice how this program is similar to the
   following command line.

   C:\> echo.exe < Readme.txt > result.txt

   In the case of this command line, the shell program sets up the
   I/O redirections before running the program echo.exe. In the
   case of this program, the program itself does its own I/O
   redirections.
*/
#include <stdio.h>

int main( )
{
   /* redirect this process's stdin and stdout to files */
   freopen("Readme.txt", "r", stdin);
   freopen("result.txt", "w", stdout);

   int c;
   while ( (c = getchar()) != EOF )
   {
      printf("%c", c);
   }

   /* close the files */
   fclose(stdout);
   fclose(stdin);

   return 0;
}

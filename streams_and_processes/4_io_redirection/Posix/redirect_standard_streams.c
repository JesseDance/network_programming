/*
   This file shows how a Linux program can redirect
   its standard input & output streams to files.
   The Linux functions that do this are:
      close(), open(), dup(), dup2()

   After this program redirects stdin & stdout, it just
   reads characters from stdin and echoes them to stdout.

   After the I/O redirection, this program's standard
   streams are connected as in the following picture.

             redirect_standard_streams
                +-------------+
                |             |
  Readme.txt--->> 0         1 >>-----result.txt
                |             |
                |           2 >>-----console window
                |             |
                +-------------+

   Be sure you understand what happens when you execute the
   following command-line. Notice that in this command-line,
   the shell program does I/O redirection of the standard
   streams for this program and then this program does its
   own redirection of its own streams!

   > redirect_standard_streams < redirect_standard_in_ver_1.c > something.txt


   As an exercise, you should rewrite this example to use the
      "open-close-dup-close"
   and
      "open-dup2-close"
   techniques.
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>

int main( )
{
   int   fd;

   /* redirect standard input to a file */
   /* use the close-open technique */
   close(STDIN_FILENO);                /* close the current stdin */
   fd = open("Readme.txt", O_RDONLY);  /* fd should be 0 */
   if ( fd != STDIN_FILENO )           /* fd should be 0, i.e., stdin */
   {
      fprintf(stderr,"Could not open() fd as 0\n");
      exit(1);
   }


   /* redirect standard output to a file */
   /* use the close-open technique */
   close(STDOUT_FILENO);                  /* close the current stdout */
   fd = open("result.txt", O_WRONLY | O_CREAT); /* fd should be 1 */
   if ( fd != STDOUT_FILENO )            /* fd should be 1, i.e., stdout */
   {
      fprintf(stderr,"Could not open() fd as 1\n");
      exit(1);
   }


   /* echo charaters from stdin to stdout */
   char c;
   while( (c = getchar()) != EOF )
   {
      printf("%c", c);
      fflush(stdout);  // try commenting this out
   }

   return 0;
}

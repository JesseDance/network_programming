/*
   This file shows how a Linux program can redirect its
   standard input stream to a file.

   This version uses the "close-open" technique to redirect
   standard input. This uses the fundamental Linux idea that
   when you open() a file, the operating system always returns
   the lowest available file descriptor number. So if a process
   starts like this

                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

   and calls close(0), then we get this.

                   process
              +---------------+
              |               |
              |             1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

   A call to open("data.txt") then results in this (since 0
   is now the lowest available file descriptor).

                   process
              +---------------+
              |               |
  data.txt--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

   So now the data file is using the standard input file descriptor,
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>

int main()
{
   int   fd;
   char  line[100];

   /* read (and print) three lines from the
      current version of standard input */
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );


   /* redirect standard input to a file */
   /* use the close-open technique */
   close(STDIN_FILENO);                /* close the current stdin */
   fd = open("Readme.txt", O_RDONLY);  /* fd should be 0 */
   if ( fd != STDIN_FILENO )           /* fd should be 0, i.e., stdin */
   {
      fprintf(stderr,"Could not open() fd as 0\n");
      exit(1);
   }


   /* read (and print) three lines from the
      current version of standard input */
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );

   return 0;
}

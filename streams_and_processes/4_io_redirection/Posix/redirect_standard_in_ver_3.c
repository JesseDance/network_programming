/*
   This file shows how a Linux program can redirect its
   standard input stream to a file.

   This version uses the "open-dup2-close" technique to redirect
   standard input. This uses the fundamental Linux idea that when
   you open() a file, the operating system always returns the
   lowest available file descriptor number. So if a process
   starts like this

                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

   and calls open("data.txt"), then we get this (since 3 is
   the lowest available file descriptor).

                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
  data.txt--->> 3             |
              |               |
              +---------------+

   A call to dup2(3, 0) then results in this because dup2() will close
   file descriptor 0 and copy file descriptor 3 to 0.

                   process
              +---------------+
              |               |
           +->> 0           1 >>---------> console window
           |  |               |
           |  |             2 >>------> console window
  data.txt-+->> 3             |
              |               |
              +---------------+

   Finally we call close(3) since we no longer need that file descriptor.

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
   int   fd1, fd2;  /* new file descriptors */
   char  line[100];

   /* read (and print) three lines from the
      current version of standard input */
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );


   /* redirect standard input to a file */
   /* use the open-dup2-close technique */
   fd1 = open("Readme.txt", O_RDONLY);  /* fd1 should be 3 */
   fd2 = dup2(fd1, STDIN_FILENO);       /* close 0, dup fd1 to 0 */
   if ( fd2 != STDIN_FILENO )           /* fd2 should be 0, i.e., stdin */
   {
      fprintf(stderr,"Could not dup2() fd to 0\n");
      exit(1);
   }
   close(fd1);


   /* read (and print) three lines from the
      current version of standard input */
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );
   fgets( line, 100, stdin ); printf( "%s", line );

   return 0;
}

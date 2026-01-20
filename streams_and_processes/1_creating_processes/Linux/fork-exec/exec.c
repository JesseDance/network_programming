/*
   The exec() family of functions cause a new program to
   be loaded into the current process. This means that the
   code from the program that calls exec() gets (completely)
   overwritten by the code of a new program and then the new
   program is started. The exec() function expects a "command-line"
   like argument to tell it what program to run and what command
   line parameters to pass to the new program.
*/
#include <stdio.h>
#include <unistd.h>

int main()
{
   /* build a command line (a null terminated array of strings) */
   char  *arglist[3];
   arglist[0] = "ls";
   arglist[1] = "-l";
   arglist[2] = 0 ;

   printf("* * * About to exec ls -l\n");
   /* execute the command line (in this process!!) */
   execvp( "ls" , arglist );

   printf("* * * ls is done. bye\n"); /* We never get here! */
   return 0;
}

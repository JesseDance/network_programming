/*
   This program demonstrates the Linux API for creating a process
   and running a program, the fork( and execve() functions.
*/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main()
{
   int fork_rv = fork();

   if ( -1 == fork_rv )  /* check for error */
   {
      perror("fork");
      exit(1);
   }
   else if ( 0 == fork_rv )  /* child */
   {
      char* argv[] = {"notepad.exe", NULL};
      execve("/mnt/c/Windows/System32/notepad.exe", argv, NULL);
      perror("execve failed");
      exit(1);
   }
   else /* parent */
   {
      int my_pid = getpid();
      printf("Parent: my pid is %d\n", my_pid);
      printf("Child: child pid is %d.\n", fork_rv);
   }

   return 0;
}

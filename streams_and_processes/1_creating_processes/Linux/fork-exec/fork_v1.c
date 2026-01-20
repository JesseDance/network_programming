/*
   The fork() system call is the mechanism for creating new
   processes in Linux/Unix/Posix API. It creates an exact
   duplicate of the current process (the duplicate is the
   child process and the original process is the parent).
   If you want a really "different" process, then the child
   process should use a version of the exec function to replace
   this program's code with the code of the new program.
*/
#include <stdio.h>
#include <unistd.h>

int main()
{
   int pid;

   printf("Before fork: parent pid = %d\n", getpid());

   pid = fork();     /* create a new process */
                     /* child gets pid = 0, parent gets pid = child's pid */
   if ( pid == -1 )  /* check for error   */
   {
      perror("fork");
   }
   else if ( pid == 0 )
   {
      printf("I am the child, my pid = %d\n", getpid());
   }
   else
   {
      printf("I am the parent, my child has pid = %d\n", pid);
   }

   return 0;
}

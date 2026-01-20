/*
   This program creates four processes (why?).
   The four processes will be related by the
   following "family tree".

                    parent
                    /    \
                  /        \
               child1     child2
                 |
                 |
               child

   Try to identify (in terms of parents and children)
   which process created each output.
*/
#include <stdio.h>
#include <unistd.h>

int main()
{
   int pid1, pid2;

   pid1 = fork();  /* child gets pid1 = 0, parent gets pid1 = child's pid */
   if (pid1 == -1 ) perror("fork");

   pid2 = fork();  /* child gets pid2 = 0, parent gets pid2 = child's pid */
   if (pid2 == -1 ) perror("fork");

   printf("pid1 = %d and pid2 = %d (so who am I in the family tree?)\n", pid1, pid2);

   return 0;
}

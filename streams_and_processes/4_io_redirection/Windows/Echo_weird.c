/*
   This is a puzzle.

   Run
      C:\ Echo_weird.exe > result.txt
   type some junk, hit Ctrl-Z, look at the resulting file, explain.


   https://msdn.microsoft.com/en-us/library/windows/desktop/ms682499(v=vs.85).aspx
*/
#include <windows.h>
#include <stdio.h>

int main(void)
{
   HANDLE hStdout = GetStdHandle(STD_OUTPUT_HANDLE);
   HANDLE hStdin  = GetStdHandle(STD_INPUT_HANDLE);
   if ( (hStdout == INVALID_HANDLE_VALUE)
     || (hStdin  == INVALID_HANDLE_VALUE) )
   {
      ExitProcess(1);
   }

   /* Send something to this process's stdout using printf(). */
   printf("** This is a message from the Echo_weird.exe process. ** \n");
   //fflush(stdout);

   for (;;)
   {
      CHAR c;
      DWORD dwRead, dwWritten;

      /* Read from standard input and stop on error or no data. */
      BOOL bSuccess = ReadFile(hStdin, &c, 1, &dwRead, NULL);

      if (! bSuccess || dwRead == 0)
         break;

      /* Write to standard output and stop on error. */
      bSuccess = WriteFile(hStdout, &c, dwRead, &dwWritten, NULL);

      if (! bSuccess)
         break;
   }

   return 0;
}

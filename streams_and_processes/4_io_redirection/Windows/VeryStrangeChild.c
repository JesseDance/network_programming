/*
   This program creates a child process and shares its
   stdin and stdout streams with that child.

               VeryStrangeChild.exe
                +----------------+
                |                |
           +--->> stdin   stdout >>-------+
           |    |                |        |
           |    |         stderr >>-->    |
           |    |                |        |
           |    +----------------+        |
        ---+                              +----->
           |            child             |
           |       +----------------+     |
           |       |                |     |
           +------>> stdin   stdout >>----+
                   |                |
                   |         stderr >>-->
                   |                |
                   +----------------+

   This program terminates immediately after creating the child!
   Run this program from the cmd.exe command-line and then try
   to use the command-line right after this program terminates.
   What happens? Explain.
   (The following picture is a hint.)

                 cmd.exe
             +----------------+
             |                |
    ----+--->> stdin   stdout >>-----------+----->
        |    |                |            |
        |    |         stderr >>-->        |
        |    |                |            |
        |    +----------------+            |
        |                                  |
        |                                  |
        |      VeryStrangeChild.exe        |
        |       +----------------+         |
        |       |                |         |
        +------>> stdin   stdout >>--------+
        |       |                |         |
        |       |         stderr >>-->     |
        |       |                |         |
        |       +----------------+         |
        |                                  |
        |                                  |
        |                child             |
        |          +----------------+      |
        |          |                |      |
        +--------->> stdin   stdout >>-----+
                   |                |
                   |         stderr >>--->
                   |                |
                   +----------------+

*/
#include <windows.h>
#include <stdio.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   LPTSTR lpCommandLine;
   PROCESS_INFORMATION processInfo;

   STARTUPINFO startInfo;
   ZeroMemory(&startInfo, sizeof(startInfo));
   startInfo.cb = sizeof(startInfo);

   /* set up the command line */
   lpCommandLine = "ParentChildShareStdin_child.exe";

   /* create the child process */
   if(! CreateProcess(NULL, lpCommandLine, NULL, NULL, TRUE,
                      NORMAL_PRIORITY_CLASS,
                      NULL, NULL, &startInfo, &processInfo) )
   {
      char buf[100];
      sprintf(buf, "CreateProcess(%s)", lpCommandLine);
      printError( buf );
   }
   else
   {
      fprintf(stderr, "Started process %d\n", (int)processInfo.dwProcessId);
   }

   /* don't wait for the child processes */

   /* close all the handles */
   CloseHandle(processInfo.hThread);
   CloseHandle(processInfo.hProcess);

   return 0;
}



/****************************************************************
   The following function can be used to print out "meaningful"
   error messages. If you call a Win32 function and it returns
   with an error condition, then call this function right away and
   pass it a string containing the name of the Win32 function that
   failed. This function will print out a reasonable text message
   explaining the error and then (if chosen) terminate the program.
*/
void printError(char* functionName)
{
   LPVOID lpMsgBuf;
    int error_no;
    error_no = GetLastError();
    FormatMessage(
         FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM,
         NULL,
         error_no,
         MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), // Default language
         (LPTSTR) &lpMsgBuf,
         0,
         NULL
    );
    // Display the string.
    fprintf(stderr, "\n%s failed on error %d: ", functionName, error_no);
    fprintf(stderr, (const char*)lpMsgBuf);
    // Free the buffer.
    LocalFree( lpMsgBuf );
    ExitProcess(1);  // terminate the program
}//printError

/*
   This program creates a child process and shares its
   stdin and stdout streams with that child (which is
   the default behavior for processes in Windows).

   But this pair of processes was designed to show what
   happens when two processes share an output stream. So
   neither process in the pair makes any use of the shared
   input stream.

                   parent
              +----------------+
              |                |
          --->> stdin   stdout >>-------+
              |                |        |
              |         stderr >>-->    |
              |                |        |
              +----------------+        |
                                        +----> result.txt
                       child            |
                 +----------------+     |
                 |                |     |
          ------>> stdin   stdout >>----+
                 |                |
                 |         stderr >>-->
                 |                |
                 +----------------+

   Run this program and see how the output is intermixed
   (parent output is lower case, child output is uppercase).

   Be sure to run this program with its standard output
   redirected to a file.

      C:\> ParentChildShareStdout_parent.exe > result.txt

   Notice how the child also has its standard output
   redirected (inherited) to the file.
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
   lpCommandLine = "ParentChildShareStdout_child.exe";

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

   int count = 20;
   int i;
   for (i = 0; i < count; i++)
   {
      fprintf(stdout, "this is output form the parent process\n" );
   }


   /* wait for the child processes to end */
   if ( WAIT_FAILED == WaitForSingleObject(processInfo.hProcess, INFINITE) )
   {
      printError("WaitForSingleObject()");
   }

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

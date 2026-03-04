/*
   This file shows how to create a child process that inherits
   its standard streams from its parent process.

                      parent
                +----------------+
                |                |
           +--->> stdin   stdout >>-------+
           |    |                |        |
           |    |         stderr >>-->    |
           |    |                |        |
           |    +----------------+        |
        ---+                              +----->
           |             child            |
           |       +----------------+     |
           |       |                |     |
           +------>> stdin   stdout >>----+
                   |                |
                   |         stderr >>-->
                   |                |
                   +----------------+

   This is actually the default behavior in Windows so if you
   just create a child, it automatically inherits all three of
   its parent's standard streams. But this file shows a way for
   a parent to explicitly have its child inherit the standard
   streams. This explicit technique is needed in the case where
   we want a child to have a mixture of inherited and redirected
   standard streams.

   Be sure to run this program with its standard input and output
   redirected to files.

      C:\> CreateChildInheritStdStreams.exe < double.c > result.txt

   Notice how the child also has its standard input and output
   redirected (inherited) to the files.
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

   /* The parent uses the child's STARTUPINFO data structure to
      explicitly set the child's standard streams to be the same
      as the parent's. This example could be used to create a
      mixture of inherited and redirected standard streams. */
   /* Be sure to try commenting out the following four lines
      and noticing that it does not change this program. */
   startInfo.dwFlags |= STARTF_USESTDHANDLES;
   startInfo.hStdInput  = GetStdHandle(STD_INPUT_HANDLE);
   startInfo.hStdOutput = GetStdHandle(STD_OUTPUT_HANDLE);
   startInfo.hStdError  = GetStdHandle(STD_ERROR_HANDLE);

   /* Set up the child's command line. */
   lpCommandLine = "double.exe";

   /* Create the child process. */
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

   /* Make the parent wait for the child process to end. The
      parent is sharing its standard streams with the child,
      so the parent should not interfere with the child's I/O. */
   if ( WAIT_FAILED == WaitForSingleObject(processInfo.hProcess, INFINITE) )
   {
      printError("WaitForSingleObject()");
   }

   /* Close the handles we opened. */
   CloseHandle(processInfo.hProcess);
   CloseHandle(processInfo.hThread);

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

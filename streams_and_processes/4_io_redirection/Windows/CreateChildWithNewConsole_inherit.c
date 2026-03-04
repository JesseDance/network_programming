/*
    This program demonstrates the Win32 API for creating a process
    with a new console window.

    Notice that, since the child process runs in its own console
    window, the child process does not inherit its stdin, stdout,
    and stderr streams from this parent (the new console gets the
    default stdin, stdout, stderr streams). This program shows how
    to make the new console window inherit its standard streams from
    this parent process.

    Run this program with a command line like this
      C:\>CreateChildWithNewConsole_inherit.exe < double.c > result.txt
    and you will see that the child redirects its stdout to the
    file test.txt (since the child inherits it's parent's I/O
    streams).

    To help you understand what is being done by this code, look up
    the following functions and data structures in MSDN.
       CreateProcess()
       STARTUPINFO
       PROCESS_INFORMATION
       ZeroMemory()
       GetStdHandle()
       CloseHandle()
       LPTSTR
       GetLastError()
       FormatMessage()
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

   /* have the child process inherit this parent's standard streams */
   /* this is needed because of the new console */
   startInfo.dwFlags |= STARTF_USESTDHANDLES;
   startInfo.hStdInput  = GetStdHandle(STD_INPUT_HANDLE);
   startInfo.hStdOutput = GetStdHandle(STD_OUTPUT_HANDLE);
   startInfo.hStdError  = GetStdHandle(STD_ERROR_HANDLE);

   /* set up the command line */
   lpCommandLine = "double.exe";

   /* create the child process */
   if(! CreateProcess(NULL, lpCommandLine, NULL, NULL, TRUE,
                      NORMAL_PRIORITY_CLASS | CREATE_NEW_CONSOLE, /* new console */
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

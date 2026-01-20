/*
    This program demonstrates the Windows API for creating
    processes, the CreateProcess() function.

    To help you understand what is being done by this code,
    look up the following terms in MSDN.
       CreateProcess
       STARTUPINFO
       PROCESS_INFORMATION
       ZeroMemory
       CloseHandle
       LPTSTR
       GetLastError
       FormatMessage
*/
#include <windows.h>
#include <stdio.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   char commandLine[] = "C:\\Windows\\notepad.exe";
   PROCESS_INFORMATION processInfo;
   STARTUPINFO startInfo;
   ZeroMemory(&startInfo, sizeof(startInfo));
   startInfo.cb = sizeof(startInfo);

   /* create the processes */
   if(! CreateProcess(NULL,
                      commandLine,
                      NULL,
                      NULL,
                      FALSE,
                      NORMAL_PRIORITY_CLASS,
                      NULL,
                      NULL,
                      &startInfo,
                      &processInfo))
   {
       printError("CreateProcess");
   }
   else
   {
      printf("Started program Notepad with pid = %d\n\n", (int)processInfo.dwProcessId);
   }

   /* close the handles */
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
    //ExitProcess(1);  // terminate the program
}

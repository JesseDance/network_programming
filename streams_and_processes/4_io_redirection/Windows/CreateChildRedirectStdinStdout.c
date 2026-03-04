/*
   This file shows how to create a child process that
   has its standard input and standard output streams
   redirected to files (and shares stderr with its parent)..

   When this program runs, the streams will look like this.

           CreateChildRedirectStdinStdout.exe
              +----------------+
              |                |
        ----->> stdin   stdout >>------------------------>
              |                |
              |         stderr >>--------------------+-->
              |                |                     |
              +----------------+                     |
                                                     |
                    double.exe                       |
                 +----------------+                  |
                 |                |                  |
   double.c ---->> stdin   stdout >>---> result.txt  |
                 |                |                  |
                 |         stderr >>-----------------+
                 |                |
                 +----------------+
*/
#include <windows.h>
#include <stdio.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   char  inputName[] = "double.c"; /* should make these command line arguments */
   char outputName[] = "result.txt";

   LPTSTR lpCommandLine;
   PROCESS_INFORMATION processInfo;
   HANDLE hIn;
   HANDLE hOut;

   STARTUPINFO startInfo;
   ZeroMemory(&startInfo, sizeof(startInfo));
   startInfo.cb = sizeof(startInfo);

   /* The parent process opens the input file for the child process. */
   hIn = CreateFile(inputName,             // name of the file to read
                    GENERIC_READ,          // open for reading
                    0,                     // do not share
                    NULL,                  // default security
                    OPEN_EXISTING,         // open file only if it exists
                    FILE_ATTRIBUTE_NORMAL, // normal file
                    NULL);                 // no attr. template
   if (hIn == INVALID_HANDLE_VALUE)
   {
      char buf[100];
      sprintf(buf, "CreateFile(%s)", inputName);
      printError( buf );
   }
   /* To use this new handle for the child's stdin,
      the handle must be inheritable by the child. */
   if ( ! SetHandleInformation(hIn, HANDLE_FLAG_INHERIT, HANDLE_FLAG_INHERIT) )
   {
      printError("SetHandleInformation(hIn)");
   }

   /* The parent process opens the output file for the child process. */
   hOut = CreateFile(outputName,            // name of the file to write
                     GENERIC_WRITE,         // open for writing
                     0,                     // do not share
                     NULL,                  // default security
                     CREATE_ALWAYS,         // overwrite an existing file
                     FILE_ATTRIBUTE_NORMAL, // normal file
                     NULL);                 // no attr. template
   if (hOut == INVALID_HANDLE_VALUE)
   {
      char buf[100];
      sprintf(buf, "CreateFile(%s)", outputName);
      printError( buf );
   }
   /* To use this new handle for the child's stdout,
      the handle must be inheritable by the child. */
   if ( ! SetHandleInformation(hOut, HANDLE_FLAG_INHERIT, HANDLE_FLAG_INHERIT) )
   {
      printError("SetHandleInformation(hOut)");
   }

   /* The parent redirects the child's standard in and out to the files.
      This is done using the child's STARTUPINFO data structure.
      This redirection would not work if we had not set the
      new stdin and stdout handles as inheritable.
      Notice that the child will inherit the parent's stderr. */
   startInfo.dwFlags |= STARTF_USESTDHANDLES;
   startInfo.hStdInput  = hIn;
   startInfo.hStdOutput = hOut;
   startInfo.hStdError  = GetStdHandle(STD_ERROR_HANDLE);

   /* Set up the child's command line. */
   lpCommandLine = "double.exe";

   /* Create the child process. */
   if( !CreateProcess(NULL, lpCommandLine, NULL, NULL, TRUE,
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

   /* Now that the child has been created, we should close the two
      file handles that are no longer needed by the parent. */
   if (! CloseHandle(hIn))  printError("CloseHandle(hIn)");
   if (! CloseHandle(hOut)) printError("CloseHandle(hOut)");


   /* Make the parent wait for the child process to end.
      The parent is still sharing stderr with the child,
      so the parent should not interfere with the child. */
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

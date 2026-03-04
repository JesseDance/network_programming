/*
   This file shows that in Windows, when you use the Win32
   function SetStdHandle() to redirect a process's standard
   streams, you are only changing the "file handles" to the
   streams, you are not changing the C Library's "file objects".
*/
#include <windows.h>
#include <stdio.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   #if 0
   /* First, close the current standard input and output file handles. */
   if (! CloseHandle(GetStdHandle(STD_INPUT_HANDLE)))
      printError("CloseHandle(STD_INPUT_HANDLE)");
   if (! CloseHandle(GetStdHandle(STD_OUTPUT_HANDLE)))
      printError("CloseHandle(STD_OUTPUT_HANDLE)");
   #endif

   char  inputName[] = "Readme.txt"; /* should be command line arguments */
   char outputName[] = "result.txt";

   HANDLE hIn  = INVALID_HANDLE_VALUE;
   HANDLE hOut = INVALID_HANDLE_VALUE;

   /* Open an input file stream. */
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

   /* Open an output file stream. */
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

   /* Redirect this process's stdin and stdout handles to the file streams. */
   if (! SetStdHandle(STD_INPUT_HANDLE, hIn) )
   {
      printError("SetStdHandle(hIn)");
   }
   if (! SetStdHandle(STD_OUTPUT_HANDLE, hOut) )
   {
      printError("SetStdHandle(hOut)");
   }


   /* Do a simple filtering operation on stdin and write results to stdout. */
   /* NOTE: This code uses Standard C I/O functions that use "file objects"
            for stdin and stdout, not the "Win32 handles" for stdin and stdout.
            These functions do not work with the redirected handles!
   */
   char c;
   while( (c = getchar()) != EOF )
   {
      printf("%c", c);
      fflush(stdout);  // try commenting this out
   }


   /* Close the handles we opened. */
   if (! CloseHandle(hIn))  printError("CloseHandle(hIn)");
   if (! CloseHandle(hOut)) printError("CloseHandle(hOut)");

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
}//printError

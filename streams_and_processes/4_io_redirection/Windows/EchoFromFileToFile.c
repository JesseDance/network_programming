/*
   This program shows how a Windows program can open one file for
   input and another file for output and then just copy every
   line from the input file to the output file. When this
   program is running, it has the following five streams.

             EchoFromFileToFile.exe
              +-----------------+
              |                 |
  keyboard--->>stdin     stdout>>------+---console window
              |                 |      |
              |          stderr>>------+
              |                 |
Readme.txt--->> hIn       hOut >>-----result.txt
              |                 |
              +-----------------+

   Notice that this program does the same thing as

   C:\> RedirectStandardStreams.exe

   This program also does the same thing as

   C:\> Echo_weird.exe < Readme.txt > result.txt
*/
#include <windows.h>
#include <stdio.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   char  inputName[] = "Readme.txt"; /* should be command line arguments */
   char outputName[] = "result.txt";

   HANDLE hIn  = INVALID_HANDLE_VALUE;
   HANDLE hOut = INVALID_HANDLE_VALUE;

   /* Open the input file. */
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

   /* Open the output file. */
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


   /* Do a simple filtering operation on stdin and write results to stdout. */
   int n;
   char c;
   while ( ReadFile(hIn, &c, 1, (DWORD*)&n, NULL) && n > 0 )  // weird test for eof
   {
      WriteFile(hOut, &c, 1, (DWORD*)&n, NULL);
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
    ExitProcess(1);  // terminate the program
}//printError

/*
   This file shows how a Windows program can redirect
   its standard input & output streams to files.
   The Windows function that does this is:
      SetStdHandle()

   After this program redirects stdin & stdout, it just
   reads characters from stdin and echoes them to stdout.

   After the I/O redirection, this program's standard
   streams are connected as in the following picture.

            RedirectStandardStreams.exe
              +-----------------+
              |                 |
Readme.txt--->> stdin    stdout >>-----result.txt
              |                 |
              |          stderr >>-----console window
              |                 |
              +-----------------+

   Be sure you understand what happens when you execute the
   following command-line. Notice that in this command-line,
   the shell program does I/O redirection of the standard
   streams for this program and then this program does its
   own redirection of its own streams!

   C:\> RedirectStandardStreams.exe < Echo_weird.c > something.txt


   Important: Notice how this program is similar to the
   following command line.

   C:\> Echo_weird.exe < Readme.txt > result.txt

   In the case of this command line, the shell program sets up the
   I/O redirections before running the program Echo_weird.exe. In
   the case of this program, the program itself does its own I/O
   redirections.
*/
#include <windows.h>
#include <stdio.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   /* First, close the current standard input and output file handles. */
   if (! CloseHandle(GetStdHandle(STD_INPUT_HANDLE)))
      printError("CloseHandle(STD_INPUT_HANDLE)");
   if (! CloseHandle(GetStdHandle(STD_OUTPUT_HANDLE)))
      printError("CloseHandle(STD_OUTPUT_HANDLE)");

   char  inputName[] = "ReadMe.txt"; /* should be command line arguments */
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

   /* We no longer need these two temporary handles */
   /* (but it would be wrong to close them). */
   hIn  = INVALID_HANDLE_VALUE;
   hOut = INVALID_HANDLE_VALUE;


   /* Do a simple filtering operation on stdin and write results to stdout. */
   HANDLE hSTDIN  = GetStdHandle(STD_INPUT_HANDLE);
   HANDLE hSTDOUT = GetStdHandle(STD_OUTPUT_HANDLE);
   int n;
   char c;
   while ( ReadFile(hSTDIN, &c, 1, (DWORD*)&n, NULL) && n > 0 ) // weird test for eof
   {
      WriteFile(hSTDOUT, &c, 1, (DWORD*)&n, NULL);
   }

   /* Close the file handles that we opened. */
   if (! CloseHandle(hSTDIN))  printError("CloseHandle(hSTDIN)");
   if (! CloseHandle(hSTDOUT)) printError("CloseHandle(hSTDOUT)");

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

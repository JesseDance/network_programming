/*
   This file shows a way to "kind of" fix the problem
   demonstrated by the file RedirectStandardStreams_broken.c

   This program redirects stdin and stdout using the Windows
   function SetStdHandle(). RedirectStandardStreams_broken.c
   showed that this does not redirect the Standard C functions
   getchar() and printf(). This program does something weird
   with the _open_osfhandle() and _fdopen() functions to
   "fix" getchar() and printf(). The problem is that this
   program writes data into the FILE objects for stdin and
   stdout, and that is not supposed to be done.

   See
   https://jdebp.eu/FGA/redirecting-standard-io.html
*/
#include <windows.h>
#include <stdio.h>
#include<io.h>
#include<fcntl.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   /* First, close the current standard input and output file handles. */
   if (! CloseHandle(GetStdHandle(STD_INPUT_HANDLE)))
      printError("CloseHandle(STD_INPUT_HANDLE)");
   if (! CloseHandle(GetStdHandle(STD_OUTPUT_HANDLE)))
      printError("CloseHandle(STD_OUTPUT_HANDLE)");

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

   /* We no longer need these two temporary handles. */
   hIn  = INVALID_HANDLE_VALUE;
   hOut = INVALID_HANDLE_VALUE;


   // We need to fix the connection between the Win32 handles for
   // stdin, stdout and the C Library's FILE objects for stdin, stdout.

   // Here are candidates for the new stdin and stdout FILE objects.
   FILE* newIn  = _fdopen(_open_osfhandle((long)GetStdHandle(STD_INPUT_HANDLE),  _O_TEXT), "r");
   FILE* newOut = _fdopen(_open_osfhandle((long)GetStdHandle(STD_OUTPUT_HANDLE), _O_TEXT), "w");
   //https://stackoverflow.com/questions/15944051/error-fdopen-was-not-declared-found-with-g-4-that-compiled-with-g3
   //https://docs.microsoft.com/en-us/cpp/c-runtime-library/reference/open-osfhandle
   //https://docs.microsoft.com/en-us/cpp/c-runtime-library/reference/fdopen-wfdopen
   // Notice that
   //   GetStdHandle(STD_INPUT_HANDLE)  returns our original input file handle hIn,
   //   GetStdHandle(STD_OUTPUT_HANDLE) returns our original output file handle hOut.

   // We can do the following, but it is not the right thing to do!
   // See
   // https://jdebp.eu/FGA/redirecting-standard-io.html
   //
   // There should be an operating system (Win32) function that does
   // this for us and does it correctly (no memory leak, etc.).
   *stdin  = *newIn;
   *stdout = *newOut;


   /* Do a simple filtering operation on stdin and write results to stdout. */
   char c;
   while( (c = getchar()) != EOF )
   {
      printf("%c", c);
      fflush(stdout);  // try commenting this out
   }


   /* Close the FILE objects and handles we opened.
   See
   https://docs.microsoft.com/en-us/cpp/c-runtime-library/reference/fclose-fcloseall
   */
   fclose(newIn);
   fclose(newOut);

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

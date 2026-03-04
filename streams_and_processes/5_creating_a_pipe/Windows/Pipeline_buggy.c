/*
   This version of the program is modeled on the code from this web page.

   http://msdn.microsoft.com/en-us/library/windows/desktop/ms682499(v=vs.85).aspx

   Create the following child to child pipeline.

                        parent
                   +----------------+
                   |                |
        +--------->> stdin   stdout >>----------------------------+----->
        |          |                |                             |
        |          |         stderr >>-------------------------+--|---->
        |          |                |                          |  |
    ----+          +----------------+                          |  |
        |                                                      |  |
        |                                                      |  |
        |        child_1                        child_2        |  |
        |   +---------------+              +---------------+   |  |
        |   |               |     pipe     |               |   |  |
        +-->> stdin  stdout >>--0======0-->> stdin  stdout >>-----+
            |               |              |               |   |
            |        stderr >>---+         |        stderr >>--+
            |               |    |         |               |   |
            +---------------+    |         +---------------+   |
                                 |                             |
                                 +-----------------------------+
*/
#include <windows.h>
#include <stdio.h>

/* function prototype */
void printError(char* functionName);

int main( )
{
   LPTSTR lpCommandLine_1, lpCommandLine_2;
   PROCESS_INFORMATION processInfo_1, processInfo_2;
   HANDLE hPipeIn_Wr, hPipeOut_Rd; /* The pipe's input and output handles. */

   /* Needed by CreateProcess() */
   STARTUPINFO startInfo_1;
   ZeroMemory(&startInfo_1, sizeof(startInfo_1));
   startInfo_1.cb = sizeof(startInfo_1);

   STARTUPINFO startInfo_2;
   ZeroMemory(&startInfo_2, sizeof(startInfo_2));
   startInfo_2.cb = sizeof(startInfo_2);

   /* Used by CreatePipe() */
   SECURITY_ATTRIBUTES saAttr;
   saAttr.nLength = sizeof(SECURITY_ATTRIBUTES);

   /* Create a pipe to connect the first child's's stdout to the second child's stdin. */
   /* Set the bInheritHandle flag so that both pipe handles can be inherited by the children. */
   saAttr.bInheritHandle = TRUE;
   saAttr.lpSecurityDescriptor = NULL;
   if (! CreatePipe(&hPipeOut_Rd, &hPipeIn_Wr, &saAttr, 0))
   {
      printError( "CreatePipe()" );
   }

   /* Set up child_1's command line. */
   lpCommandLine_1 = "to_upper_case.exe";

   /* The parent uses the child's STARTUPINFO data structure to
      redirect the child's stdout to the pipe's input and also
      have the child inherit the parent's stdin and stderr.
      The redirection would not work if we had not set the
      pipe's handles as inheritable. */
   startInfo_1.dwFlags |= STARTF_USESTDHANDLES;
   startInfo_1.hStdInput  = GetStdHandle(STD_INPUT_HANDLE);
   startInfo_1.hStdOutput = hPipeIn_Wr;
   startInfo_1.hStdError  = GetStdHandle(STD_ERROR_HANDLE);

   /* Create the child1 process. */
   if(! CreateProcess(NULL, lpCommandLine_1, NULL, NULL, TRUE,
                      NORMAL_PRIORITY_CLASS,
                      NULL, NULL, &startInfo_1, &processInfo_1))
   {
      /* Close the pipe handles. */
      CloseHandle(hPipeIn_Wr);
      CloseHandle(hPipeOut_Rd);
      char buf[100];
      sprintf(buf, "CreateProcess(%s)", lpCommandLine_1);
      printError( buf );
   }
   else
   {
      fprintf(stderr, "Started process %d\n", (int)processInfo_1.dwProcessId);
   }

   /* The write handle to the pipe is not needed by child_2,
      so it should not be inherited by child_2.
      NOTE: If child_2 did inherit this handle, this program would
      not work properly. The write end of the pipe would stay open
      even after child_1 terminated! That would prevent child_2 from
      ever seeing an end-of-file condition. */
// if (! SetHandleInformation(hPipeIn_Wr, HANDLE_FLAG_INHERIT, 0))
// {
//    printError( "SetHandleInformation(hPipeIn_Wr)" );
// }

   /* Set up child_2's command line. */
   lpCommandLine_2 = "double.exe";

   /* The parent uses the child's STARTUPINFO data structure to
      redirect the child's stdin to the pipe's output and also
      have the child inherit the parent's stdout and stderr.
      The redirection would not work if we had not set the
      pipe's handles as inheritable. */
   startInfo_2.dwFlags |= STARTF_USESTDHANDLES;
   startInfo_2.hStdInput  = hPipeOut_Rd;
   startInfo_2.hStdOutput = GetStdHandle(STD_OUTPUT_HANDLE);
   startInfo_2.hStdError  = GetStdHandle(STD_ERROR_HANDLE);

   /* Create the child_2 process. */
   if(! CreateProcess(NULL, lpCommandLine_2, NULL, NULL, TRUE,
                      NORMAL_PRIORITY_CLASS,
                      NULL, NULL, &startInfo_2, &processInfo_2))
   {
      /* Close the pipe handles. */
      CloseHandle(hPipeIn_Wr);
      CloseHandle(hPipeOut_Rd);
      /* We should terminate the child_1 process */
      /* ???? */
      /* Close the handles we opened for child_1. */
      CloseHandle(processInfo_1.hProcess);
      CloseHandle(processInfo_1.hThread);
      char buf[100];
      sprintf(buf, "CreateProcess(%s)", lpCommandLine_2);
      printError( buf );
   }
   else
   {
      fprintf(stderr, "Started process %d\n", (int)processInfo_2.dwProcessId);
   }


   /* Now that the children have been created, we should close the pipe's
      input and output handles that are no longer needed by the parent. */
   if (! CloseHandle(hPipeIn_Wr))
   {
      printError("CloseHandle(hPipeIn_Wr)");
   }
   if (! CloseHandle(hPipeOut_Rd))
   {
      printError("CloseHandle(hPipeOut_Rd)");
   }


   /* Make the parent wait for the child_2 process to end.
      The parent is still sharing stdin and stderr with child_1
      and sharing stdout and stderr with child_2, so the parent
      should not interfer with the children's I/O. */
   if ( WAIT_FAILED == WaitForSingleObject(processInfo_2.hProcess, INFINITE) )
   {
      printError("WaitForSingleObject()");
   }

   /* Close the handles we opened. */
   CloseHandle(processInfo_1.hProcess);
   CloseHandle(processInfo_1.hThread);
   CloseHandle(processInfo_2.hProcess);
   CloseHandle(processInfo_2.hThread);

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

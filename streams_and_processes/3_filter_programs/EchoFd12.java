/*

*/

import java.util.Scanner;

/**
   This program reads lines from standard input and
   echos them to standard output and standard error.
<p>
   Recall that the standard output stream is file descriptor 1
   and the standard error stream is file descriptor 2, hence
   the program name Echo1and2.
<p>
   This program can be used to test the output redirection
   operators for the standard error stream, `2>`, `2>&1`,
   `&>`, and `|&`.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see EchoNFd12
   @see Echo
   @see EchoN
*/
public class EchoFd12
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.println( oneLine );
         System.err.println( oneLine.toUpperCase() );

         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException");
         if ( System.err.checkError() )
            throw new RuntimeException("System.err has encountered an IOException");
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private EchoFd12() {
      throw new AssertionError();
   }
}

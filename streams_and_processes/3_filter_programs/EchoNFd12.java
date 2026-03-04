/*

*/

import java.util.Scanner;

/**
   This program reads lines from standard input and
   echos each line to standard output and to standard
   error N times, where N is an optional command-line
   argument. The default value for N is 2.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see EchoFd12
   @see Echo
   @see EchoN
*/
public class EchoNFd12
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args)
   {
      int n = 2;  // Default value for n.

      // Check for an optional command-line argument.
      if (args.length >= 1)
      {
         try
         {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore the argument.
         }
         if (n <= 0) n = 1;
      }

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         for (int i = 0; i < n; ++i)
         {
            System.out.println( oneLine );
            System.err.println( oneLine.toUpperCase() );

            if ( System.out.checkError() )
               throw new RuntimeException("System.out has encountered an IOException");
            if ( System.err.checkError() )
               throw new RuntimeException("System.err has encountered an IOException");
         }
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private EchoNFd12() {
      throw new AssertionError();
   }
}

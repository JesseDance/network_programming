/*

*/

import java.util.Scanner;

/**
   This program reads lines from standard input and
   echos each line to standard output N times, where
   N is an optional command-line argument. The default
   value for N is 2.
<p>
   If there is a second optional command-line argument,
   then it is the separator between repeated strings.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see Echo
   @see EchoFd12
   @see EchoNFd12
*/
public class EchoN
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args)
   {
      int n = 2;               // Default value for n.
      String separator = "\n"; // Default line separator.

      // Check for two optional command-line arguments.
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
      if (args.length >= 2)
      {
         separator = args[1];
      }

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();

         // Fence-post loop.
         System.out.print( oneLine  );
         for (int i = 1; i < n; ++i)
         {
            System.out.print( separator + oneLine );
            if ( System.out.checkError() )
               throw new RuntimeException("System.out has encountered an IOException");
         }
         System.out.println();
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private EchoN() {
      throw new AssertionError();
   }
}

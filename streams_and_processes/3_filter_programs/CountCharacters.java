/*

*/

import java.util.Scanner;

/**
   This program counts the total number of characters from each
   line of its standard input and writes the resulting int to
   its standard output.
<p>
   This program does not count the CR and LF characters in the
   input stream.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see CountWords
   @see CountLines
*/
public class CountCharacters
{
   public static void main(String[] args)
   {
      int characterCount = 0;

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         characterCount += oneLine.length();
      }
      System.out.println( characterCount );
      if ( System.out.checkError() )
         throw new RuntimeException("System.out has encountered an IOException");
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private CountCharacters() {
      throw new AssertionError();
   }
}

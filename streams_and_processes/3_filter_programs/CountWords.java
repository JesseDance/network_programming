/*

*/

import java.util.Scanner;

/**
   This program counts the number of tokens in its standard
   input and writes the resulting int to its standard output.
<p>
   A token is a sequence of characters that does not contain
   the space character.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see CountCharacters
   @see CountLines
*/
public class CountWords
{
   public static void main(String[] args)
   {
      int wordCount = 0;

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         // Create a Scanner that breaks the input line into tokens.
         final Scanner scanner2 = new Scanner( oneLine );
         int count = 0;
         while ( scanner2.hasNext() ) // While there is another token.
         {
            scanner2.next(); // Consume the token.
            ++count;         // Count the token.
         }
         wordCount += count;
      }
      System.out.println( wordCount );
      if ( System.out.checkError() )
         throw new RuntimeException("System.out has encountered an IOException");
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private CountWords() {
      throw new AssertionError();
   }
}

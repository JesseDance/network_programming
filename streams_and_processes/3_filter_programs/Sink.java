/*

*/

import java.util.Scanner;

/**
   This program reads lines from standard input and discards them.
<p>
   Notice that the command-line
<p>
   > java Sink < input.txt > output.txt
<p>
   will create an empty output.txt file, and the command-line
   will open and read through the entire input.txt file.
<p>
   This program acts kind of like the Linux /dev/null device.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see Source
   @see Bottom
   @see Null
*/
public class Sink
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         scanner.nextLine();
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private Sink() {
      throw new AssertionError();
   }
}

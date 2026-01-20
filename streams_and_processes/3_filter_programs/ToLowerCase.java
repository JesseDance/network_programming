/**
   This program reads lines from standard input, converts all
   the letters to lower case, and writes them to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.util.Scanner;

public class ToLowerCase
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.println( oneLine.toLowerCase() );
      }
   }
}

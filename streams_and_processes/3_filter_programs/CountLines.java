/**
   This program counts the number of lines in its standard
   input and writes the resulting int to its standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.util.Scanner;

public class CountLines
{
   public static void main(String[] args)
   {
      int lineCount = 0;

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         lineCount++;
      }
      System.out.println( lineCount );
   }
}

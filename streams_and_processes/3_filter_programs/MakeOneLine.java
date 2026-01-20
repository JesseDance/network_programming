/**
   This program concatenates all the lines of its input
   into one single output line.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.util.Scanner;

public class MakeOneLine
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.print( oneLine );
      }
   }
}

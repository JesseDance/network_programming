import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TestSetInSetOut
{
   public static void main(String[] args) throws FileNotFoundException, IOException
   {
      System.out.println("Copying \"Readme.txt\" to \"ReadmeCopy.txt\".");

      // Save the original streams so that they can be restored.
      final InputStream inSaved  = System.in;
      final PrintStream outSaved = System.out;

      // Set System.in to be the file "Readme.txt".
      System.setIn( new FileInputStream( "Readme.txt" ) );

      // Set System.out to be the file "ReadmeCopy.txt.
      System.setOut( new PrintStream( new FileOutputStream( "ReadmeCopy.txt" ) ) );

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner(System.in);

      // Echo every line of input from stdin to stdout.
      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.println( oneLine );
      }

      scanner.close();
      System.in.close(); // This may be redundant.
      System.out.close();

      // Restore the original values of System.in and System.out.
      System.setIn(inSaved);
      System.setOut(outSaved);

      System.out.println("Done copying \"Readme.txt\" to \"ReadmeCopy.txt\".");
      System.out.print("Press Enter to continue ...");
      System.in.read();  // Show that keyboard input has been restored.
      System.out.println("Bye.");
   }
}

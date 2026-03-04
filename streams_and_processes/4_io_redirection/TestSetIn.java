import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
   This example shows that the System.setIn() method
   does NOT change what stream a child process inherits.

   A child process inherits the System.in stream that the
   parent process was created with, not whatever stream
   the parent process redirects System.in to!

   Run this program with the following command-lines.
      > java TestSetIn < Readme.txt
      > java TestSetIn
      > java TestSetIn < TestSetIn_data.txt
*/

class Child10
{
   public static void main(String[] args) throws IOException
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner(System.in);

      // Echo three lines of input from stdin to stdout.
      for (int i = 0; i < 3; ++i)
      {
         final String oneLine = scanner.nextLine();
         System.out.println("Child : " + oneLine);
      }

      // Close the input stream (how does this affect the parent?).
      scanner.close();
      System.in.close(); // This may be redundant.
      System.out.println("Child process done.");
   }
}



public class TestSetIn
{
   public static void main(String[] args) throws FileNotFoundException,
                                                 IOException,
                                                 InterruptedException
   {
      // Set System.in to be the file "TestSetIn_data.txt".
      System.setIn(new FileInputStream("TestSetIn_data.txt"));

      System.out.println("Starting Child10 process ...");
      final Process p = new ProcessBuilder("java", "Child10")
                             .inheritIO()
                             //.redirectInput(new java.io.File("TestSetIn_data.txt"))
                             .start();
      p.waitFor();

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner(System.in);

      // Echo three lines of input from stdin to stdout.
      for (int i = 0; i < 3; ++i)
      {
         final String oneLine = scanner.nextLine();
         System.out.println("Parent: " + oneLine);
      }

      scanner.close();
      System.in.close(); // This may be redundant.
      System.out.println("Parent process done.");
   }
}

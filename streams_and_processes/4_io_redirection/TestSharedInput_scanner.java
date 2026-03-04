import java.util.Scanner;

/**
   This program shows what happens when a parent and child
   share System.in and both try to use it at the same time.

   Run this program with the following command-lines
      > java TestSharedInput
   and
      > java TestSharedInput_scanner < TestSharedInput_data.txt
*/

class Child12
{
   public static void main(String[] args) throws Exception
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner(System.in);

      // Make the child pause.
      //try {Thread.sleep(100);}catch(Exception e){}

      // Echo lines of input from stdin to stdout.
      for (int i = 0; i < 3; ++i)
      {
         final String oneLine = scanner.nextLine();
         System.out.println("Child : " + oneLine);
      }

      // Close the input stream (how does this affect the parent?).
      scanner.close();
      System.out.println("Child process done.");
   }
}


public class TestSharedInput_scanner
{
   public static void main(String[] args) throws Exception
   {
      System.out.println("Starting Child12 process ...");
      final Process p = new ProcessBuilder("java", "Child12")
                             .inheritIO()
                             .start();

      // Do NOT wait for the child process to terminate.
      //p.waitFor();

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner(System.in);

      // Make the parent pause.
      //try {Thread.sleep(100);}catch(Exception e){}

      // Echo lines of input from stdin to stdout.
      for (int i = 0; i < 3; ++i)
      {
         final String oneLine = scanner.nextLine();
         System.out.println("Parent: " + oneLine);
      }

      // Close the input stream (how does this affect the child?).
      scanner.close();
      System.out.println("Parent process done.");

      // Wait for the child process to terminate.
      p.waitFor(); // Try commenting this out. Then the child can compete with the shell.
   }
}

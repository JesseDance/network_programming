/*
   This program uses the Java Process API
   to run several Windows programs.
   See
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ProcessBuilder.html
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Process.html
*/

import java.lang.ProcessBuilder;
import java.lang.Process;
import java.io.IOException;

public class RunPrograms
{
   public static void main(String[] args)
   {
      try
      {
         // Create a command-line for running a program.
         final ProcessBuilder pb1 = new ProcessBuilder("C:\\Windows\\system32\\notepad.exe",
                                                       "RunPrograms.java");
         // Execute the command-line.
         final Process p1 = pb1.start();
         System.out.println( "notepad pid: " + p1.pid() );
      }
      catch (IOException e)
      {
         System.out.println( e );
      }


      try
      {
         // Create a command-line for running a program.
         final ProcessBuilder pb2 = new ProcessBuilder("C:\\Windows\\system32\\calc.exe");
         // Execute the command-line.
         final Process p2 = pb2.start();
         System.out.println( "calc pid: " + p2.pid() );
      }
      catch (IOException e)
      {
         System.out.println( e );
      }


      try
      {
         // Create a command-line for running a program.
         final ProcessBuilder pb3 = new ProcessBuilder("C:\\Windows\\system32\\charmap.exe");
         // Execute the command-line.
         final Process p3 = pb3.start();
         System.out.println( "charmap pid: " + p3.pid() );
      }
      catch (IOException e)
      {
         System.out.println( e );
      }


      try
      {
         // Create a command-line for running a program.
         final ProcessBuilder pb4 = new ProcessBuilder("C:\\Windows\\system32\\mspaint.exe");
         // Execute the command-line.
         final Process p4 = pb4.start();
         System.out.println( "mspaint pid: " + p4.pid() );
      }
      catch (IOException e)
      {
         System.out.println( e );
      }


      try
      {
         // Create a command-line for running a program.
         final ProcessBuilder pb5 = new ProcessBuilder("C:\\Windows\\system32\\ipconfig.exe");
         // Have the child process share the console with this parent process.
         pb5.inheritIO();
         // Execute the command-line.
         final Process p5 = pb5.start();
         System.out.println( "ipconfig pid: " + p5.pid() );
         // Wait for the child process to terminate.
         p5.waitFor();
      }
      catch (IOException e)
      {
         System.out.println( e );
      }
      catch (InterruptedException e)
      {
         System.out.println( e );
      }


      try
      {
         // Create a command-line for running a program.
         final ProcessBuilder pb6 = new ProcessBuilder("C:\\Windows\\system32\\nslookup.exe");
         // Have the child process share the console with this parent process.
         pb6.inheritIO();
         // Execute the command-line.
         final Process p6 = pb6.start();
         System.out.println( "nslokup pid: " + p6.pid() );
         // Wait for the child process to terminate.
         // While nslookup is running, use ProcessExplorer.exe to
         // examine the parent-child relationship between cmd.exe,
         // the JVM process, and the processes this program started.
         p6.waitFor();
      }
      catch (IOException e)
      {
         System.out.println( e );
      }
      catch (InterruptedException e)
      {
         System.out.println( e );
      }
      System.out.println("Done.");
   }
}

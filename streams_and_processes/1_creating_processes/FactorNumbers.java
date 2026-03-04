/*
   This program demonstrates the Java API for creating a process.

   Make sure that you compile FactorIntoPrimes.java before running this program.

   See
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ProcessBuilder.html
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Process.html
*/

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
   This program uses the Java Process API to run
   FactorIntoPrimes.java on a couple of integers.

   The results that FactorIntoPrimes.java writes
   to its standard output are redirected to a file
   in the current directory.
*/
public class FactorNumbers
{
   public static void main(String[] args)
   {
      // Each of these numbers takes about 25 seconds to factor.
      final String number1 = "37591057185x623057848";
      final String number2 = "375910571856230578480";

      final long startTime = System.currentTimeMillis();
      try
      {
         // Create a command-line for running the factorization program.
         final ProcessBuilder pb1 = new ProcessBuilder(
                                         "java.exe",
                                         "FactorIntoPrimes",
                                         number1);
         final File out1 = new File(number1 + ".txt");
         pb1.redirectOutput(ProcessBuilder.Redirect.appendTo(out1));
         // Execute the command-line, with its output appended to file out1.
         final Process p1 = pb1.start();
         System.out.println("Factoring " + number1 + ", results will be appended to file " + out1);

         // Create a command-line for running the factorization program.
         final ProcessBuilder pb2 = new ProcessBuilder(
                                         "java.exe",
                                         "FactorIntoPrimes",
                                         number2);
         final File out2 = new File(number2 + ".txt");
         pb2.redirectOutput(ProcessBuilder.Redirect.appendTo(out2));
         // Execute the command-line, with its output appended to file out2.
         final Process p2 = pb2.start();
         System.out.println("Factoring " + number2 + ", results will be appended to file " + out2);

         System.out.println("waiting...");
         p1.waitFor();
         p2.waitFor();
         if (0 != p1.exitValue()) // Check if the first child process failed.
         {  // Read the standard error stream from the child processes.
            try (final BufferedReader err = new BufferedReader(
                                               new InputStreamReader(
                                                  p1.getErrorStream())))
            {
               String oneLine;
               while ((oneLine = err.readLine()) != null) // read up to end-of-stream
               {
                  System.out.println("number1 ===> " + oneLine);
               }
            }
         }
         if (0 != p2.exitValue()) // Check if the second child process failed.
         {  // Read the standard error stream from the child processes.
            try (final BufferedReader err = new BufferedReader(
                                               new InputStreamReader(
                                                  p2.getErrorStream())))
            {
               String oneLine;
               while ((oneLine = err.readLine()) != null) // read up to end-of-stream
               {
                  System.out.println("number2 ===> " + oneLine);
               }
            }
         }
      }
      catch (IOException e)
      {
         System.out.println( e );
      }
      catch (InterruptedException e)
      {
         System.out.println( e );
      }
      final long stopTime = System.currentTimeMillis();
      System.out.println( (stopTime - startTime) + " ms" );
   }
}

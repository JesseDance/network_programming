
import java.io.PrintStream;
import java.io.BufferedOutputStream;

/**
   This program tests the speed of the default, unbufffered
   System.out PrintStream, and a buffered System.out PrintStream.

   The buffered System.out stream should be about 10 times faster
   than the unbuffered stream.
*/
public class TestSystemOutSpeed
{
   public static void main(String[] args)
   {
      // Use the default version of System.out.
      final long startTime1 = System.currentTimeMillis();
      for (int i = 0; i < 50_000; ++i)
      {
         System.out.println("This is a timed test of the speed of the System.out stream.");
      }
      final long stopTime1 = System.currentTimeMillis();

      // Create a buffered version of System.out.
      System.setOut(new PrintStream(
                           new BufferedOutputStream(System.out,
                                                    4096),
                           false));
      long startTime2 = System.currentTimeMillis();
      for (int i = 0; i < 50_000; ++i)
      {
         System.out.println("This is a timed test of the speed of the buffered System.out stream.");
      }
      final long stopTime2 = System.currentTimeMillis();

      System.out.printf("Wall-clock time: %,d milliseconds\n", stopTime1 - startTime1);
      System.out.printf("Wall-clock time: %,d milliseconds\n", stopTime2 - startTime2);
      System.out.flush(); // Buffered streams must be flushed.
   }
}

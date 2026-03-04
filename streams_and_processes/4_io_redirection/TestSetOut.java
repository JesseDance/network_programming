import java.io.PrintStream;
import java.io.IOException;

/**
   Try to draw a reasonable picture of what this
   process, its child processes, and all of their
   streams, look like when this program runs.

   Notice that this program works properly
   when its output is redirected.
      > java TestSetOut > temp.txt
*/

public class TestSetOut
{
   public static void main(String[] args) throws IOException,
                                                 InterruptedException
   {
      System.out.println("This should print normally.");

      // Save the original output stream so that it can be restored.
      final PrintStream outSaved = System.out;

      final Process p1 = new ProcessBuilder("java", "-cp", "filters.jar", "ToUpperCase")
                              .redirectInput(ProcessBuilder.Redirect.PIPE)
                              .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                              .redirectError(ProcessBuilder.Redirect.INHERIT)
                              .start();
      System.setOut(new PrintStream(p1.getOutputStream()));
      System.out.println("This should print in all upper case.");
      System.out.close(); // Try commenting this out. Or try replacing close with flush.
      p1.waitFor();       // Try commenting this out.

      final Process p2 = new ProcessBuilder("java", "-cp", "filters.jar", "Reverse")
                              .redirectInput(ProcessBuilder.Redirect.PIPE)
                              .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                              .redirectError(ProcessBuilder.Redirect.INHERIT)
                              .start();
      System.setOut(new PrintStream(p2.getOutputStream()));
      System.out.println("This should print in reverse.");
      System.out.close(); // Try commenting this out.
      p2.waitFor();       // Try commenting this out.

      final Process p3 = new ProcessBuilder("java", "-cp", "filters.jar", "OneWordPerLine")
                              .redirectInput(ProcessBuilder.Redirect.PIPE)
                              .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                              .redirectError(ProcessBuilder.Redirect.INHERIT)
                              .start();
      System.setOut(new PrintStream(p3.getOutputStream()));
      System.out.println("This should print one word-per-line.");
      System.out.close(); // Try commenting this out.
      p3.waitFor();       // Try commenting this out.

      // Restore the original value of System.out.
      System.setOut(outSaved);

      System.out.println("This should print normally again.");
   }
}

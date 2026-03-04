
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
                  TestInternalPipeline
                +-----------------------+
                |                       |
   keyboard >-->> stdin          stdout >>---------+---> console window
                |                       |          |
                |                stderr >>---------+
                |                       |          |
                |    out          in    |          |
                +----\|/---------/|\----+          +------------+
                      |           |                             |
            pipe      |           |          pipe               |
    +------0====0-----+           +---------0====0---------+    |
    |                                                      |    |
    |      ToUpperCase                     DoubleN         |    |
    |   +----------------+            +----------------+   |    |
    |   |                |    pipe    |                |   |    |
    +-->> stdin   stdout >>--0====0-->> stdin   stdout >>--+    |
        |                |            |                |        |
        |         stderr >>--+        |         stderr >>-------+
        |                |   |        |                |        |
        +----------------+   |        +----------------+        |
                             |                                  |
                             +----------------------------------+
*/
public class TestInternalPipeline
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      final ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", "filters.jar", "ToUpperCase")
                                     .redirectError(ProcessBuilder.Redirect.INHERIT);

      final ProcessBuilder pb2 = new ProcessBuilder("java", "-cp", "filters.jar", "DoubleN", "3")
                                     .redirectError(ProcessBuilder.Redirect.INHERIT);

      final List<ProcessBuilder> builders = java.util.Arrays.asList(pb1, pb2);
      final List<Process> pipeline = ProcessBuilder.startPipeline(builders);

      final OutputStream out = pipeline.get(0).getOutputStream();
      final InputStream  in  = pipeline.get(1).getInputStream();

      final Scanner stdin = new Scanner(System.in);
      final Scanner in2 = new Scanner(in);

      while ( stdin.hasNextLine() )
      {
         // Send each line from stdin to the stream called out.
         final String oneLine1 = stdin.nextLine();
         out.write( (oneLine1 + "\n").getBytes() );
         out.flush(); // Try commenting this line out.

         // Send each line from the stream called in to stdout.
         final String oneLine2 = in2.nextLine();
         System.out.println( oneLine2 );
      }

      out.close();
      // What happens if we remove this loop that waits on the child processes?
      for (final Process p : pipeline)
      {
         p.waitFor();
      }
   }
}

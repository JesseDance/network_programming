
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

/**
                        TestPipeline
                      +----------------+
                      |                |
   keyboard >---+---->> stdin   stdout >>---------+----> console window
                |     |                |          |
                |     |         stderr >>---------+
                |     |                |          |
                |     +----------------+          |
       +--------+                                 +--------------+
       |                                                         |
       |      ToUpperCase                       Reverse          |
       |   +----------------+              +----------------+    |
       |   |                |     pipe     |                |    |
       +-->> stdin   stdout >>--0======0-->> stdin   stdout >>---+
           |                |              |                |    |
           |         stderr >>---+         |         stderr >>---+
           |                |    |         |                |    |
           +----------------+    |         +----------------+    |
                                 |                               |
                                 +-------------------------------+
*/
public class TestPipeline
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      final ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", "filters.jar", "ToUpperCase")
                                     .redirectInput(ProcessBuilder.Redirect.INHERIT)
                                     .redirectError(ProcessBuilder.Redirect.INHERIT);

      final ProcessBuilder pb2 = new ProcessBuilder("java", "-cp", "filters.jar", "Reverse")
                                     .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                                     .redirectError(ProcessBuilder.Redirect.INHERIT);

      final List<ProcessBuilder> builders = Arrays.asList(pb1, pb2);
      final List<Process> pipeline = ProcessBuilder.startPipeline(builders);

      // What happens if we remove this loop that waits on the child processes?
      for (final Process p : pipeline)
      {
         p.waitFor();
      }
   }
}

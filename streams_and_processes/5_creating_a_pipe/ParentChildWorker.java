
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
                  ParentChildWorker
                 +------------------+
                 |                  |
    keyboard >-->> stdin     stdout >>-----+---> console window
                 |                  |      |
                 |           stderr >>-----+
                 |                  |      |
                 |   out      in    |      |
                 +---\ /------/|\---+      +--------+
                      |        |                    |
    +-----------------+        +----------------+   |
    |                                           |   |
    |                  DoubleN                  |   |
    |             +---------------+             |   |
    |    pipe     |               |     pipe    |   |
    +--0======0-->> stdin  stdout >>--0======0--+   |
                  |               |                 |
                  |        stderr >-----------------+
                  |               |
                  +---------------+
*/
public class ParentChildWorker
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      final Process p = new ProcessBuilder("java", "-cp", "filters.jar", "DoubleN", "3")
                            .redirectError(ProcessBuilder.Redirect.INHERIT)
                            .start();
      final OutputStream out = p.getOutputStream();
      final InputStream  in  = p.getInputStream();

      // Send all input from the standard input stream to the stream called out.
      final Scanner stdin = new Scanner(System.in);
      while ( stdin.hasNextLine() )
      {
         final String oneLine = stdin.nextLine();
         out.write( (oneLine + "\n").getBytes() );
      }
      out.close();

      // Send all the input from the stream called in to the standard output stream.
      final Scanner in2 = new Scanner(in);
      while ( in2.hasNextLine() )
      {
         final String oneLine = in2.nextLine();
         System.out.println( oneLine );
      }
   }
}
/*
   If you run this program with a very large file as its input,
   then this program will fill up all of its pipe buffers while
   the program is in its input stage and then deadlock.

   > java InternalPipeline < VerLargeFile.txt
*/

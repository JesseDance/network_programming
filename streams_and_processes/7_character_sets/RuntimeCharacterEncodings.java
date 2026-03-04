
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.nio.charset.Charset;

/**
   This program lets you select the encoding used at
   runtime when printing to the standard output stream.
<p>
   Run this program from the command-line and
   redirect its standard output stream to a file.
<p>
   > java RuntimeCharacterEncodings > out1.txt
<p>
   Run this program nine times and each time
   use a different character encoding and use
   a different name for the output file.
<p>
   Notice that the output files change size
   with the changed encodings. Why?
<p>
   Open the output files with a hex dump program and
   look at the different encodings of this program's
   output characters.

   @see RuntimeDefaultEncoding
*/
public class RuntimeCharacterEncodings
{
   public static void main(String[] args) throws Exception
   {
      final PrintWriter out = new PrintWriter(
                                 new FileOutputStream(FileDescriptor.out),
                                 true,
                                 Charset.forName("UTF-8")); // Try ISO-8859-1, UTF-8,
                                                            // UTF-16, UTF-16LE ,UTF-16BE,
                                                            // UTF-32, UTF-32LE, UTF-32BE.

      out.print("hello ");  // Five ASCII characters.
      out.print("\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087"); // Eight Extended ASCII or Unicode chars.
      out.print("\n"); // Two ASCII characters on Windows, one ASCII character on Linux.
      out.flush();
   }
}

/*
   https://docs.oracle.com/en/java/javase/21//docs/api/java.base/java/nio/charset/Charset.html#standard
*/

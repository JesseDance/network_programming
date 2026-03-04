/**
   This program tells you the default encoding used by
   the JVM while it is running.
<p>
   If you compile and run this program, it will print
   to stdout the default character encoding of the JVM.
   This default depends on both the version of Java and
   the operating system.
<p>
   If run this program with the following command-line,
   then you are telling the JVM to make the UTF-32LE
   charset the default encoding.
<p>
      character_sets> java -Dfile.encoding="UTF-32LE" RuntimeDefaultEncoding
<p>
   The JVM reports that its default encoding is UTF-32LE,
   just as we told it to use. But is it really using
   UTF-32LE? To find out, use this command-line to
   save the output text.
<p>
      character_sets> java -Dfile.encoding="UTF-32LE" RuntimeDefaultEncoding > temp.txt
<p>
    The 10 characters in the string "UTF-32LE\n" should
    be encoded in the file temp.txt using 40 bytes (four
    bytes per character). Use a hex dump program to verify
    that the bytes are in little endian order. Notice that
    all the extra 0x00 bytes are interpreted as ASCII null
    bytes (by the console), so that are just skipped over.

   @see RuntimeCharacterEncodings
*/
public class RuntimeDefaultEncoding
{
   public static void main(String[] args)
   {
      // The println() method USES the default encoding
      // as it tells us WHAT the default coding is.
      System.out.println( System.getProperty("file.encoding") );
   }
}

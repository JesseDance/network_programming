
import java.io.DataInputStream;
import java.io.IOException;
public class IntClient
{
   public static void main(String[] args) throws IOException
   {
      // Wrap a DataInputStream object around System.in.
      final DataInputStream in = new DataInputStream( System.in );

      // Read four byte values as an int value.
      final int n = in.readInt(); // readInt() can throw IOException and EOFException.

      // Print the int value as a a String of digits.
      System.out.println(n);
   }
}


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
public class IntServer
{
   public static void main(String[] args) throws IOException
   {
      // Wrap a DataOutputStream object around System.out.
      final DataOutputStream out = new DataOutputStream(System.out);
      // Wrap a Scanner object around System.in.
      final Scanner in = new Scanner(System.in);

      // Read a String of digits as an int value.
      final int i = in.nextInt();

      // Write the int value as four byte values.
      out.writeInt(i); // writeInt() can throw IOException.
      out.flush();     // Try commenting out this line.
   }
}

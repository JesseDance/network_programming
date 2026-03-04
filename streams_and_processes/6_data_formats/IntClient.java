
import java.nio.ByteBuffer;
import java.io.IOException;
public class IntClient
{
   public static void main(String[] args) throws IOException
   {
      final int b0 = System.in.read(); // read() can throw an IOException.
      final int b1 = System.in.read();
      final int b2 = System.in.read();
      final int b3 = System.in.read();
      final byte[] bytes = {(byte)b0,
                            (byte)b1,
                            (byte)b2,
                            (byte)b3};
      final int n = ByteBuffer.wrap(bytes)
                              .getInt();
      System.out.println(n);
   }
}

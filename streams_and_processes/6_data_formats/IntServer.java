
import java.nio.ByteBuffer;
public class IntServer
{
   public static void main(String[] args)
   {
      final int n = new java.util.Scanner(System.in).nextInt();
      byte[] bytes = ByteBuffer.allocate(Integer.BYTES)
                               .putInt(n)
                               .array();
      System.out.write(bytes[0]);
      System.out.write(bytes[1]);
      System.out.write(bytes[2]);
      System.out.write(bytes[3]);
      System.out.flush(); // Try commenting this out.
   }
}

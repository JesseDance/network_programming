public class WeirdBytes
{
   public static void main(String[] args)
   {
      byte b0 =  0xFF;        // Does this look like a byte?
      byte b1 =  0xFFFFFFFF;  // Does this look like a byte?
      byte b2 =  0x80;        // Does this look like a byte?
      byte b3 =  0xFFFFFF80;  // Does this look like a byte?
      byte b4 =  0xFFFFFF00;  // Does this look like a byte?
      byte b5 = -0x01;        // What byte is this?
      byte b6 = -0x80;        // What byte is this?

      byte b7 =  0x88;        // Does this look like a byte?
      byte b8 =  0xFFFFFF88;  // What byte is this?
      byte b9 = (byte)0x88;   // What byte is this?
      byte b10 = -0x78;       // What byte is this?
      System.out.printf("0x%X", b10);
   }
}

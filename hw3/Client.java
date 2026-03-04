/*
   Course: CS 33600
   Name: Jesse Dance
   Email: dancej@pnw.edu
   Assignment: HW3
*/

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

class Client
{
   public static void main(String[] args)
   {
      final BufferedInputStream in = new BufferedInputStream(System.in);
      int byteCounter = 0;

      try {
         byte header = in.read();

         //base case eof byte = -1
         //or end transmission byte 0x80

         //text message
         //most significant bit is 1, but byte is not 0x80

         //numeric message
         //most significant bit is 0

         //remaining header bits
            //0 for each float
            //1 for each long



      } catch (IOException e) {
         System.out.printf("IO exception %s", e.getMessage());
      }

      System.out.printf("\nRead %d bytes from standard input.\n", byteCounter);
   }
}

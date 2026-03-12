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

      final int MSB_1_MASK = 0x80;
      final int REMAINING_BITS_1_MASK = 0x7F;

      try {

         while(true) {

            int header = in.read();

            //BASE CASE interrupt byte = -1
            if(header == -1) {
               handleUnexpectedEOF(byteCounter);
            }

            byteCounter++;

            //BASE CASE end transmission byte 0x80
            if(header == MSB_1_MASK) {
               break;
            }

            //CASE 1 text message
            //most significant bit is 1, but byte is not 0x80
            //using 0x80 (100000000) as a mask for the MSB
            if((header & MSB_1_MASK) != 0) {

               //using 0x7f (01111111) to get the remaining bits
               int textLength = header & REMAINING_BITS_1_MASK;

               for(int i = 0; i < textLength; i++) {
                  int asciiVal = in.read();

                  if(asciiVal==-1) {
                     handleUnexpectedEOF(byteCounter);
                  } 
                  
                  System.out.print((char) asciiVal);
                  byteCounter++;
               }
               //new line after text message
               System.out.println();

            }

            //CASE 2 numeric message
            //most significant bit is 0
            else {
               //remaining header bits
               //0 for each float
               //1 for each long
               for(int i = 0; i < 7; i++) {
                  boolean isLong = ((header >> i) & 1) == 1;

                  if (isLong) {
                     byte[] bytes = new byte[Long.BYTES];

                     for(int j = 0; j < Long.BYTES; j++) {
                        int partOfLong = in.read();

                        if(partOfLong==-1) {
                           handleUnexpectedEOF(byteCounter);
                        } 
                        //swapping from little endian to big endian here
                        bytes[Long.BYTES-j-1] = (byte) partOfLong;
                        byteCounter++;
                     }

                     long reconstructedLong = ByteBuffer.wrap(bytes).getLong();
                     System.out.println(reconstructedLong);
                  } else { //is float 
                     byte[] bytes = new byte[Float.BYTES];

                     for(int j = 0; j < Float.BYTES; j++) {
                        int partOfFloat = in.read();

                        if(partOfFloat==-1) {
                           handleUnexpectedEOF(byteCounter);
                        }
                        bytes[j] = (byte) partOfFloat;
                        byteCounter++;
                     }
                     //received bytes are in weird order
                     //reordering to big endian here
                     byte[] reordered = new byte[4];
                     reordered[0] = bytes[2]; //most significant
                     reordered[1] = bytes[3]; //second most
                     reordered[2] = bytes[0]; //second least
                     reordered[3] = bytes[1]; //least

                     float reconstructedFloat = ByteBuffer.wrap(reordered).getFloat();
                     System.out.printf("%.10f\n",reconstructedFloat);

                  }

               }
            } 
         }


      } catch (IOException e) {
         System.out.printf("IO exception %s", e.getMessage());
      }

      System.out.printf("\nRead %d bytes from standard input.\n", byteCounter);
   }

   private static void handleUnexpectedEOF(int byteCount) {
      System.out.println("\nERROR: Unexpected end-of-file.");
      System.out.printf("Read %d bytes from standard input.\n", byteCount);
      System.exit(1);
   }
}

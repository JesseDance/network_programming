import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class slop_client {

    public static void main(String[] args) {
        InputStream in = System.in;
        long byteCount = 0;

        try {
            while (true) {

                int header = in.read();

                if (header == -1) {
                    System.out.println("\nERROR: Unexpected end-of-file.");
                    System.out.println("Total bytes received: " + byteCount);
                    return;
                }

                byteCount++;

                // End-of-transmission message (0x80)
                if (header == 0x80) {
                    // System.out.println("\nEnd of transmission.");
                    System.out.printf("Read %d bytes from standard input", byteCount);
                    return;
                }

                // Text message (MSB = 1, but not 0x80)
                if ((header & 0x80) != 0) {

                    int length = header & 0x7F;

                    for (int i = 0; i < length; i++) {
                        int ch = in.read();
                        if (ch == -1) {
                            System.out.println("\nERROR: Unexpected end-of-file.");
                            System.out.println("Total bytes received: " + byteCount);
                            return;
                        }
                        byteCount++;
                        System.out.print((char) ch);
                    }
                    System.out.println();
                }

                // Numeric message (MSB = 0)
                else {

                    for (int bit = 0; bit < 7; bit++) {

                        boolean isLong = ((header >> bit) & 1) == 1;

                        if (isLong) {
                            byte[] bytes = new byte[Long.BYTES];

                            for (int i = 0; i < Long.BYTES; i++) {
                                int b = in.read();
                                if (b == -1) {
                                    System.out.println("\nERROR: Unexpected end-of-file.");
                                    System.out.println("Total bytes received: " + byteCount);
                                    return;
                                }
                                byteCount++;
                                bytes[i] = (byte) b;
                            }

                            // Convert little-endian to big-endian
                            for (int i = 0; i < 4; i++) {
                                byte temp = bytes[i];
                                bytes[i] = bytes[7 - i];
                                bytes[7 - i] = temp;
                            }

                            long value = ByteBuffer.wrap(bytes).getLong();
                            System.out.println(value);
                        }

                        else {  // float
                            byte[] bytes = new byte[Float.BYTES];

                            for (int i = 0; i < Float.BYTES; i++) {
                                int b = in.read();
                                if (b == -1) {
                                    System.out.println("\nERROR: Unexpected end-of-file.");
                                    System.out.println("Total bytes received: " + byteCount);
                                    return;
                                }
                                byteCount++;
                                bytes[i] = (byte) b;
                            }

                            // Convert weird-endian to big-endian
                            // Weird order: 2nd least, least, most, 2nd most
                            byte[] reordered = new byte[4];
                            reordered[0] = bytes[2]; // most significant
                            reordered[1] = bytes[3]; // second most
                            reordered[2] = bytes[0]; // second least
                            reordered[3] = bytes[1]; // least

                            float value = ByteBuffer.wrap(reordered).getFloat();
                            System.out.println(value);
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }
}
/*

*/

import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
   This program demonstrates that a stream can be in the eof state,
   then change to not being in the eof state, then change back to
   being in the eof state.
<p>
   We do this using a random access file. If we move the file position
   to the end of the file's data, then the file's input stream is in the
   eof state. If we move the file position to the beginning of the file
   (or anywhere else inside the file), then the file's stream is no longer
   in the end-of-file state.
*/
public class End_of_file_Demo
{
   public static void main(String[] args) throws FileNotFoundException, IOException
   {
      final RandomAccessFile file = new RandomAccessFile("filters.jar", "r");

      final long n = file.length();

      file.seek(n); // Move to the end of the file's contents.

      int b = file.read();
      if (-1 == b) // Are we in the eof state?
      {
         System.out.println("eof_1");
      }
      else
      {
         System.out.println(b);
      }

      file.seek(0); // Move to the beginning of the file's contents.

      b = file.read();
      if (-1 == b) // Are we in the eof state?
      {
         System.out.println("eof_2");
      }
      else
      {
         System.out.println(b);
      }

      file.seek(n); // Move back to the end of the file's contents.

      b = file.read();
      if (-1 == b) // Are we in the eof state?
      {
         System.out.println("eof_3");
      }
      else
      {
         System.out.println(b);
      }
   }
}

/*
   https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/RandomAccessFile.html
*/

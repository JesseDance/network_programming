/*

*/

import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.io.IOException;

/**
   This program reads bytes from the standard input stream
   and write them to a ZipOutputStream that is wrapped
   around the standard output stream.
<p>
   Use this program with a command-line like this to
   zip a file.
<p>
    > java ZipServer < inputFile > outputFile.zip
<p>
   Run this program with the following command-line to zip up
   whatever you type into this program's standard input stream.
<p>
    > java ZipServer > data.zip
*/
public class ZipServer
{
   public static void main(String[] args) throws IOException
   {
      final ZipOutputStream zip = new ZipOutputStream(System.out);

      zip.putNextEntry(new ZipEntry("data"));

      int b = 0;
      while (-1 != (b = System.in.read()))
      {
         final byte[] bytes = { (byte)b };
         zip.write(bytes, 0, 1);
      }
      zip.close();
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ZipServer() {
      throw new AssertionError();
   }
}

/*
   https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/zip/ZipOutputStream.html
*/

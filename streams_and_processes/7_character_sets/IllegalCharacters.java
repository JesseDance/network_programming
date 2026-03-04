/**
   This program demonstrates issues with compiling
   a source file that contains byte values that are
   illegal in some character encodings.
<p>
   There are five illegal characters in the Cp1252 code
   page (or, to put it another way, five byte values
   with no character assigned to them).
<p>
      https://en.wikipedia.org/wiki/Windows-1252#Codepage_layout
<p>
   This program uses each of those five byte values in a String literal.
<p>
   Also, the first and last characters in the string literal do not
   exist in the Cp1252 code page.
<p>
   All seven characters used in the string literal are of the
   form 10______ (in binary) so they are all illegal in UTF-8
   as Byte 1 of an encoding.
<p>
      https://en.wikipedia.org/wiki/UTF-8#Description
<p>
   So the following string literal is "illegal" in Cp1252 and
   it is "illegal" in UTF-8.
<p>
   Compiling  and running this program is not trivial.
   The Java compiler does not like all those "illegal"
   characters. And neither does the JVM. See the
   IllegalCharacters.cmd script file.

   @see RuntimeDefaultEncoding
   @see RuntimeCharacterEncodings
*/
public class IllegalCharacters
{
   public static void main(String[] args)
   {
      // This string contains five illegal Cp1252 characters.
      // The first and last characters do not exist in Cp1252.
      // All seven characters are illegal UTF-8 characters.
      System.out.println("µÅçèêù∂");
   }
}

/**
   This program prints a Unicode string, but the string is encoded
   using ascii escape sequences, so this file does not contain any
   Unicode characters.
*/
public class UnicodeCharacters
{
   public static void main(String[] args)
   {
      // This string contaims currency symbols using Unicode ASCII escape sequences.
      System.out.println("\u20AC\u20AD\u20AE\u20AF\u20B0\u20B1\u20B2\u20B3\u20B4\u20B5");
   }
}
// https://www.compart.com/en/unicode/block/U+20A0

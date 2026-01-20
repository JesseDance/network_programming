/*

*/

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;

/**
   This program takes a single command-line argument which is an integer.

   This program creates a list of the integer's prime factors
   and then prints that list to standard output. The first item
   in the output list is the given integer to be factored and
   the last item in the output list is the number of milliseconds
   that it took to so the factorization.

   Try  37591057185623057848 (approximately 30 seconds)
        37591057185623757848 (approximately 80 seconds)
*/
public class FactorIntoPrimes
{
   public static void main(String[] args)
   {
      if (args.length == 0)
      {
         System.err.println("Usage: java FactorPrimes <big integer>");
         System.exit(0);
      }

      BigInteger i = new BigInteger(args[0]);

      System.out.println(factorize(i));
   }


   /**
      A naive factorization method. Take integer n, return list of factors.
   */
   public static List<BigInteger> factorize(BigInteger n)
   {
      final List<BigInteger> factors = new ArrayList<>();

      factors.add(n); // The integer to factor.

      final long startTime = System.currentTimeMillis();

      if (n.compareTo(BigInteger.valueOf(2)) < 0)
         return factors;

      BigInteger p = BigInteger.valueOf(2); // 2 is the first prime number.

      boolean done = false;
      while (! done)
      {
         if ( n.equals(BigInteger.ONE) )
         {
            done = true;
         }
         else
         {
            final BigInteger r = n.remainder(p);
            if ( r.equals(BigInteger.ZERO) )
            {
               factors.add(p); // Found a prime factor.
               n = n.divide(p);
            }
            else if (p.pow(2).compareTo(n) >= 0) // If p^2 > n.
            {
               factors.add(n); // The last prime factor.
               done = true;
            }
            else if (p.compareTo(BigInteger.valueOf(2)) > 0)
            {
               p = p.add(BigInteger.valueOf(2)); // Advance in steps of 2 over odd numbers.
            }
            else
            {
               p = BigInteger.valueOf(3); // If p == 2, go to 3, the second prime.
            }
         }
      }
      factors.add(BigInteger.ZERO); // Separate the prime factors from the time.
      final long stopTime = System.currentTimeMillis();
      factors.add(BigInteger.valueOf(stopTime - startTime));
      return factors;
   }
}

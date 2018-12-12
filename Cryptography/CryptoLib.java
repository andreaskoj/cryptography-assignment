package Cryptography;

// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

public class CryptoLib {


    // implementation of gcd
    private static int gcd(int n1, int n2) {
        if (n2 != 0)
            return gcd(n2, n1 % n2);
        else
            return n1;
    }

    // computing powers in modulars
    private static int power_modulo(int a, int b, int m) {
        int x, i;

        x = a % m;
        for (i = 1; i < b; i++) {
            x = x * a;
            x = x % m;
        }
        return x;
    }

    /**
     * Returns an array "result" with the values "result[0] = gcd",
     * "result[1] = s" and "result[2] = t" such that "gcd" is the greatest
     * common divisor of "a" and "b", and "gcd = a * s + b * t".
     **/

    public static int[] EEA(int a, int b) {
        // Note: as you can see in the test suite,s
        // your function should work for any (positive) value of a and b.
        int c;

        boolean swaped = false;

        if (b >= a) {
            //swap places
            c = b;
            b = a;
            a = c;
            swaped = true;
        }

        int quotient = 0;

        int t = 1;
        int s = 0;
        int r = b;

        int oldS = 1;
        int oldT = 0;
        int oldR = a;

        int tempR;
        int tempS;
        int tempT;

        //initialize quotient
        quotient = oldR / r;

        while (r != 0) {

            tempS = s;
            tempT = t;
            s = oldS - s * quotient;
            t = oldT - t * quotient;
            oldT = tempT;
            oldS = tempS;

            tempR = r;
            r = oldR % r;

            oldR = tempR;

            if (r == 0) break;
            quotient = oldR / r;
        }

        if (swaped) {
            s = oldT;
            t = oldS;
        } else {
            t = oldT;
            s = oldS;
        }

        int gcd = oldR;

        int[] result = new int[3];
        result[0] = gcd;
        result[1] = s;
        result[2] = t;
        return result;
    }

    /**
     * Returns Euler's Totient for value "n".
     **/

    public static int EulerPhi(int n) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (gcd(n, i) == 1) result++;
        }
        return result;
    }

    /**
     * Returns the value "v" such that "n*v = 1 (mod m)". Returns 0 if the
     * modular inverse does not exist.
     **/
    public static int ModInv(int n, int m) {
        int arrayRes[];
        int mv;

        //if n is negative change to non-negative
        if (n <= 0) n = n + m;

        if (gcd(n, m) != 1)
            return 0;

        arrayRes = EEA(n, m);

        //if inverse is negative change to non-negative
        if (arrayRes[1] < 0)
            mv = arrayRes[1] + m;
        else
            mv = arrayRes[1];
        return mv;
    }

    /**
     * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
     * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
     * <p>
     * Instead of picking random values a to test the primality of a number n,
     * make a start from 2 and increment it by 1 at each new iteration, until you have tested all the values below n/3.
     **/

    public static int FermatPT(int n) {
        int numberOfTests = 0;
        int testOK = 0;


        for (int a = 2; a < n / 3; a++) {

            // probably prime
            if (power_modulo(a, n - 1, n) == 1) {
                testOK++;
            }

            //not prime // collect lowest witness
            else {
                return a;
            }
            numberOfTests++;
        }

        if (numberOfTests == testOK) return 0;

        return -1;
    }

    /**
     * Returns the probability that calling a perfect hash function with
     * "n_samples" (uniformly distributed) will give one collision (i.e. that
     * two samples result in the same hash) -- where "size" is the number of
     * different output values the hash function can produce.
     **/
    public static double HashCP(double n_samples, double size) {
        double p_tmp, result;
        int i;

        if (n_samples > size)
            return 0;

        p_tmp = 1;
        for (i = 1; i < n_samples; i++) {
            p_tmp *= (double) 1 - (i / size);
        }

        result = 1 - p_tmp;

        return result;
    }

}

package Cryptography;

/**
 * Personnummer: 199110264935
 */

public class CryptoLib {


    /** EEA
     * We are applying the method used on the lecture.
     * 1. We swapping places to have higher number on 1 position.
     * 2. We are computing t,s until we get lowest gcd > 0.
     * 3. If we swapped places in step 1 we swap it back.
     *
     * @param a - number 1
     * @param b - number 2
     * @return - returns gcd and Bézout coefficients t,s
     */

    public static int[] EEA(int a, int b) {

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
     * 1. We are computing all gcd in range 0 to n
     * 2. If the gcd equals to 1 we increment result variable.
     * 3. We implemented gcd method bellow EulerPhi
     *
     * @param n - number on which we have to compute Eulers Totient
     * @return Eulers Totient
     */

    public static int EulerPhi(int n) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (gcd(n, i) == 1) result++;
        }
        return result;
    }

    // implementation of gcd
    private static int gcd(int n1, int n2) {
        if (n2 != 0)
            return gcd(n2, n1 % n2);
        else
            return n1;
    }

    /**
     * 1. if n is negative add modulo to switch it to non-negative
     * 2. Computing gcd, if gcd is not 1 there is no modular inverse
     * 3. Using EEA algorithm to find modular inverse, second parameter of the result is s parameter which is
     * modular inverse of our n
     * 4. if modular inverse is negative we change to positive by adding modulo
     *
     * @param n - number on which we compute modular inverse
     * @param m - modulo
     * @return
     */

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
     * 1. To check if the numbers is probably prime we apply Fermat's little theorem a^n-1 = 1 mod p.
     * 2. If Fermat's little theorem returns number different that 1 this is Fermat witness.
     * 3. If we get in all tests (a < n/3) number 1 we return 0 (this is probably prime number).
     * 4. We implemented extra method power_modulo (method from assignment 2) to computer high powers without using
     * BigInteger.
     *
     * @param n - number which we test if it's prime
     * @return  - returns lowest witness or 0 if it's probably prime
     */

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

    // computing powers in modulo
    public static int power_modulo(int a, int b, int m) {
        int x, i;

        x = a % m;
        for (i = 1; i < b; i++) {
            x = x * a;
            x = x % m;
        }
        return x;
    }

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

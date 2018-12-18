package Cryptography;

/**
 * Personnummer: 199110264935
 */

public class CryptoLib {

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

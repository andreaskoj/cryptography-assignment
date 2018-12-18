/*
 * REPORT
 *
 * Personnummer: 199110264935
 *
 * To recover the message we first needed to recover the random number r, where R = r^2. To do that we exploited the
 * way in which a message is encrypted in ElGamal: c1 = g^r. So we created all the possible r values iterating over all
 * possible milliseconds values, until the latter equation is satisfied.
 *
 * Once we found the correct r we used the following: c2 = m * y^r. From the latter we can retrieve m = c2 * y^-r.
 *
 * Recovered message:
 * 171583025435590940701381043129867429519017355171788527397403780978355685958207634415007492102872317662611675832109792326702
 *
 * Decoded text:
 * Bruce Schneier knows Alice and Bob's shared secret.
 *
 */

package Cryptography;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;


public class ElGamal {

    public static String decodeMessage(BigInteger m) {
        return new String(m.toByteArray());
    }

    public static void main(String[] arg) {

        String filename = "Cryptography/inputELG.txt";

        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));

            BigInteger p = new BigInteger(br.readLine().split("=")[1]);
            BigInteger g = new BigInteger(br.readLine().split("=")[1]);
            BigInteger y = new BigInteger(br.readLine().split("=")[1]);

            String line = br.readLine().split("=")[1];

            String date = line.split(" ")[0];
            String time = line.split(" ")[1];

            int year = Integer.parseInt(date.split("-")[0]);
            int month = Integer.parseInt(date.split("-")[1]);
            int day = Integer.parseInt(date.split("-")[2]);

            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1]);
            int second = Integer.parseInt(time.split(":")[2]);

            BigInteger c1 = new BigInteger(br.readLine().split("=")[1]);
            BigInteger c2 = new BigInteger(br.readLine().split("=")[1]);

            br.close();

            BigInteger m = recoverSecret(p, g, y, year, month, day, hour, minute,
                    second, c1, c2);

            System.out.println("\nRecovered message:\n" + m);
            System.out.println("\nDecoded text:\n" + decodeMessage(m));

        } catch (Exception err) {

            System.err.println("Error handling file.");
            err.printStackTrace();
            System.exit(1);

        }
    }

    public static BigInteger recoverSecret(BigInteger p, BigInteger g, BigInteger y, int year, int month, int day,
                                           int hour, int minute, int second, BigInteger c1, BigInteger c2) {

        BigInteger r = BigInteger.ZERO;
        Boolean found = false;

        for( int i = 0; i <= 1000; i++ ){

            r = createRandomNumber(year, month, day, hour, minute, second, i, p);

            if ((g.modPow(r, p)).equals(c1)){

                found = true;
                break;
            }
        }

        if( !found ){
            System.out.println("Error, the correct random value doesn't exist");
            System.exit(1);
        }

        BigInteger pow = y.modPow(r, p);

        return c2.multiply( pow.modInverse(p) ).mod(p);
    }

    public static BigInteger createRandomNumber(int year, int month, int day, int hour, int minute, int second,
                                                int millisecond, BigInteger p) {

        BigInteger y, mon, d, h, min, s, mil;

        y = BigInteger.valueOf(year);
        mon = BigInteger.valueOf(month);
        d = BigInteger.valueOf(day);
        h = BigInteger.valueOf(hour);
        min = BigInteger.valueOf(minute);
        s = BigInteger.valueOf(second);
        mil = BigInteger.valueOf(millisecond);

        BigInteger b10, b8, b6, b4, b2;

        b10 = BigInteger.valueOf(10);
        b8 = BigInteger.valueOf(8);
        b6 = BigInteger.valueOf(6);
        b4 = BigInteger.valueOf(4);
        b2 = BigInteger.valueOf(2);

        BigInteger yMul, monMul, dMul, hMul, minMul;

        yMul = y.multiply(b10.modPow(b10, p)).mod(p);
        monMul = mon.multiply(b10.modPow(b8, p)).mod(p);
        dMul = d.multiply(b10.modPow(b6, p)).mod(p);
        hMul = h.multiply(b10.modPow(b4, p)).mod(p);
        minMul = min.multiply(b10.modPow(b2, p)).mod(p);

        BigInteger sum = yMul.add(monMul).add(dMul).add(hMul).add(minMul).add(s).add(mil);

        return sum.mod(p);
    }

}

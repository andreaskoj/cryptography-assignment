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

        BigInteger exp = p.subtract(BigInteger.valueOf(1)).subtract(r);

        BigInteger pow = y.modPow(exp, p);

        return c2.multiply(pow).mod(p);
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

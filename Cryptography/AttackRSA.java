package Cryptography;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

public class AttackRSA {

    public static void main(String[] args) {

        String filename = "Cryptography/inputRSA.txt";
        BigInteger[] N = new BigInteger[3];
        BigInteger[] e = new BigInteger[3];
        BigInteger[] c = new BigInteger[3];

        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));

            for (int i = 0; i < 3; i++) {

                String line = br.readLine();
                //System.out.println(line);
                String[] elem = line.split(",");
                N[i] = new BigInteger(elem[0].split("=")[1]);
                e[i] = new BigInteger(elem[1].split("=")[1]);
                c[i] = new BigInteger(elem[2].split("=")[1]);
            }

            br.close();

        } catch (Exception err) {

            System.err.println("Error handling file.");
            err.printStackTrace();

        }

        BigInteger m = recoverMessage(N, e, c);
        System.out.println("Recovered message: " + m);
        System.out.println("Decoded text: " + decodeMessage(m));

    }

    public static String decodeMessage(BigInteger m) {
        return new String(m.toByteArray());
    }

    /**
     * Tries to recover the message based on the three intercepted cipher texts.
     * In each array the same index refers to same receiver. I.e. receiver 0 has
     * modulus N[0], public key d[0] and received message c[0], etc.
     *
     * @param N The modulus of each receiver.
     * @param e The public key of each receiver (should all be 3).
     * @param c The cipher text received by each receiver.
     * @return The same message that was sent to each receiver.
     */

    private static BigInteger recoverMessage(BigInteger[] N, BigInteger[] e, BigInteger[] c) {

        // TODO Solve assignment.

        BigInteger t1, t2, t3, ctmp, Nmul, Ntmp, Ninv, sum;

        ctmp = c[0];
        Nmul = N[1].multiply(N[2]);
        Ntmp = N[0];

        Ninv = Nmul.modInverse(Ntmp);
        t1 = ctmp.multiply(Nmul).multiply(Ninv);

        ctmp = c[1];
        Nmul = N[0].multiply(N[2]);
        Ntmp = N[1];

        Ninv = Nmul.modInverse(Ntmp);
        t2 = ctmp.multiply(Nmul).multiply(Ninv);

        ctmp = c[2];
        Nmul = N[0].multiply(N[1]);
        Ntmp = N[2];

        Ninv = Nmul.modInverse(Ntmp);
        t3 = ctmp.multiply(Nmul).multiply(Ninv);

        Nmul = N[0].multiply(N[1]).multiply(N[2]);
        sum = t1.add(t2).add(t3);

        return CubeRoot.cbrt(sum.mod(Nmul));
    }

}

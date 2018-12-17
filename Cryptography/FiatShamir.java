package Cryptography;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

/**
 * Personnummer: 199110264935
 */

public class FiatShamir {

    public static class ProtocolRun {

        public final BigInteger R;
        public final int c;
        public final BigInteger s;

        public ProtocolRun(BigInteger R, int c, BigInteger s) {
            this.R = R;
            this.c = c;
            this.s = s;
        }

    }

    public static void main(String[] args) {

        String filename = "Cryptography/inputFS.txt";
        BigInteger N = BigInteger.ZERO;
        BigInteger X = BigInteger.ZERO;
        ProtocolRun[] runs = new ProtocolRun[10];

        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));
            N = new BigInteger(br.readLine().split("=")[1]);
            X = new BigInteger(br.readLine().split("=")[1]);

            for (int i = 0; i < 10; i++) {

                String line = br.readLine();
                String[] elements = line.split(",");

                BigInteger R = new BigInteger(elements[0].split("=")[1]);
                Integer c = Integer.parseInt(elements[1].split("=")[1]);
                BigInteger s = new BigInteger(elements[2].split("=")[1]);

                runs[i] = new ProtocolRun(R, c, s);
            }

            br.close();

        } catch (Exception err) {

            System.err.println("Error handling file.");
            err.printStackTrace();
            System.exit(1);
        }

        BigInteger m = recoverSecret(N, X, runs);
        System.out.println("Recovered message: " + m);
        System.out.println("Decoded text: " + decodeMessage(m));

    }

    public static String decodeMessage(BigInteger m) {
        return new String(m.toByteArray());
    }

    /**
     * Recovers the secret used in this collection of Fiat-Shamir protocol runs.
     *
     * @param N    The modulus
     * @param X    The public component
     * @param runs Ten runs of the protocol.
     * @return We eavesdropped on a number of Fiat-Shamir protocol runs and we found that the same nonce was used twice!
     * Due to the special soundness property you should now be able to retrieve the secret key used in the protocol.
     * <p>
     * Less
     * <p>
     * The Fiat-Shamir protocol is a zero-knowledge protocol, briefly explained here.
     * The text box below displays (n), the modulus used, and (X) the public key of the prover.
     * #We know that X = x2 and your task is to find x.
     * The rest of the input is a sequence of runs of the Fiat Shamir protocol.
     * R is the random value sent to the verifier, c is the challenge sent to the prover,
     * and s is the proof sent back to the verifier.
     * <p>
     * Look for runs with the same random value, i.e. the same value of R.
     */
    private static BigInteger recoverSecret(BigInteger N, BigInteger X, ProtocolRun[] runs) {

        // TODO. Recover the secret value x such that x^2 = X (mod N).

        Boolean found = false;
        int i, j;
        BigInteger Ri, Rj, si, sj, sModInverse;
        Integer ci, cj;

        ci = j = 0;

        for (i = 0; i < runs.length - 1; i++) {

            Ri = runs[i].R;
            ci = runs[i].c;

            for (j = i + 1; j < runs.length; j++) {

                Rj = runs[j].R;
                cj = runs[j].c;

                if ( Ri.equals(Rj) && !ci.equals(cj) )
                {
                    found = true;
                    break;
                }
            }

            if( found )
                break;
        }

        if( found )
        {
            if( ci == 0 )
            {
                System.out.println(i);
                System.out.println(j);
                si = runs[i].s;
                sj = runs[j].s;
            }
            else
            {
                sj = runs[i].s;
                si = runs[j].s;
            }

            sModInverse = si.modInverse(N);


            return sj.multiply(sModInverse).mod(N);
        }
        else
            return BigInteger.ZERO;
    }
}

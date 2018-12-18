/* REPORT
 *
 * Personnummer: 199110264935
 *
 * We check each possible pair of runs until we find two runs i and j that have Ri = Rj = R and ci != cj.
 * Then we exploit the following two equations:
 *      si^2 = R * Xi^ci
 *      sj^2 = R * Xj^cj
 *
 *  From there, assuming that ci = 0 and cj = 1, we obtain the following. (If cj =1 and ci = 1 we simply swap.)
 *      x = sj * si^-1
 *
 * Recovered message:
 * 243584202333895510512748939572531656001716982622777463126342293818818172090977126351505204422855740631128135876472239966512128651207085058705617891925475269562599814349152186456774650008879811381953600431903702127019287037418902544655579295253975934311378610300379058765444713594762838663972697910426712556202290606952819754101860
 *
 * Decoded text:
 * It's amazing that the amount of news that happens in the world every day always just exactly fits the newspaper. --------- Jerry Seinfeld
 *
 */

package Cryptography;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

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

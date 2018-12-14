package Cryptography;

import com.sun.deploy.util.ArrayUtil;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;


public class CBCXor {



	public static void main(String[] args) {
		String filename = "Cryptography/inputCBC.txt";
		byte[] first_block = null;
		byte[] encrypted = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			first_block = br.readLine().getBytes();
			encrypted = DatatypeConverter.parseHexBinary(br.readLine());
			br.close();
		} catch (Exception err) {
			System.err.println("Error handling file.");
			err.printStackTrace();
			System.exit(1);
		}
		String m = recoverMessage(first_block, encrypted);
		System.out.println("Recovered message: " + m);
	}

	/**
	 * Recover the encrypted message (CBC encrypted with XOR, block size = 12).
	 *
	 * @param first_block We know that this is the value of the first block of plain
	 *                    text.
	 * @param encrypted   The encrypted text, of the form IV | C0 | C1 | ... where each
	 *                    block is 12 bytes long.
	 *                    <p>
	 *                    We intercepted a message that was encrypted using cypher-block chaining.
	 *                    We also know the plain-text value of the first block. Can you reconstruct the complete plain-text message?
	 *                    <p>
	 *                    First, have a re-cap on how CBC works. In this case the encryption function is simply a XOR (+)
	 *                    operation with the key, i.e. Ci = K + (Mi + Ci-1), where C0 = IV.
	 *                    <p>
	 *                    In the text box below you find the known first block (= the 12 digit number your provided)
	 *                    and the encrypted message. You know that the block size is 12.
	 */
	private static String recoverMessage(byte[] first_block, byte[] encrypted) {
		byte[] ivMsg = new byte[12];
		byte[] key = new byte[12];
		byte[] nextInput = new byte[12];
		byte[] buffer = new byte[12];
		byte[] encryptedTempContainer = new byte[96];
		byte[] ct0;
		byte[] currentDecryptedBlock;
		byte[] previousDecryptedBlock;

		int blockSize = first_block.length;
		int encryptedSize = encrypted.length;
		int blocksNo = encryptedSize / blockSize;
		int destPos = 0;

		byte[] iv = Arrays.copyOfRange(encrypted, 0, 12);
		ct0 = Arrays.copyOfRange(encrypted, 12 ,24);

		//first step to get key... iv xor m1
		for (int i = 0; i < blockSize; i++) {
			ivMsg[i] = (byte)(iv[i] ^ first_block[i]);
		}

		//to get key
		for (int i = 0; i < blockSize; i++) {
			key[i] = (byte)(ivMsg[i] ^ ct0[i]);
		}

		previousDecryptedBlock = ct0;

		//decrypting blocks blocks starting from 2 because we already have c0 and the first block is IV
		for(int i = 2; i < blocksNo; i++ ) {
			currentDecryptedBlock = Arrays.copyOfRange(encrypted, blockSize * i  , (blockSize * i + blockSize));

			for (int y = 0; y < blockSize; y++) {
				nextInput[y] = (byte)(key[y] ^ currentDecryptedBlock[y]);
			}

			for (int y = 0; y < blockSize; y++) {

				buffer[y] = (byte)(previousDecryptedBlock[y] ^ nextInput[y]);
				// 48 - ascii 0
				if (buffer[y] == 48) {
					// to get rid off zeros on the end of the message // 32 - ascii space sign
					buffer[y] = 32;
				}
			}
			previousDecryptedBlock = currentDecryptedBlock.clone();

			System.arraycopy(buffer,0,encryptedTempContainer,destPos,12);
			destPos += blockSize;
		}
		encrypted = encryptedTempContainer.clone();

		return new String(encrypted);
	}
}

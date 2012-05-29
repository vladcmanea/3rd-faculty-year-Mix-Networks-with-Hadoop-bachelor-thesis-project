package org.manea.vlad.moderator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Class ElGamal
 * 
 * @author Vlad Manea
 */
public class ElGamal {

	// ElGamal parameters
	protected BigInteger p, alpha, alphaA;
	private BigInteger a;
	private int bits;
	protected Random random = new SecureRandom();

	/**
	 * generateQ generates prime q
	 * 
	 * @return q
	 */
	private BigInteger generateQ() {
		// generate q as a prime
		BigInteger q = BigInteger.probablePrime(bits - 1, random);
		// while 2*q+1 is not a prime in itself
		while (!q.multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(1))
				.isProbablePrime(10)) {
			// generate q again
			q = BigInteger.probablePrime(bits - 1, random);
			System.out.println("q");
		}
		// return a prime q
		return q;
	}

	/**
	 * generates alpha
	 */
	private void generateAlpha() {
		// generate new random element
		alpha = new BigInteger(p.bitLength() - 1, random);
		// while alpha is not in Zp*
		while (p.gcd(alpha).compareTo(BigInteger.valueOf(1)) > 0
				|| alpha.modPow(BigInteger.valueOf(2), p).compareTo(
						BigInteger.valueOf(1)) == 0
				|| alpha.modPow(
						p.subtract(
								BigInteger.valueOf(1).divide(
										BigInteger.valueOf(2))).mod(
								p.subtract(BigInteger.valueOf(1))), p)
						.compareTo(BigInteger.valueOf(1)) == 0) {
			// generate new random element
			alpha = new BigInteger(p.bitLength(), random);
			System.out.println("alpha");
		}
	}

	/**
	 * generatePAlpha generates parameters p and alpha
	 */
	private void generatePAlpha() {
		// generate a random prime q = ans[1] of 1023 bits
		BigInteger q = generateQ();
		// now you know p = 2*q+1 = 2*ans[1]+1 where p-1 = 2^1*q^1
		p = q.multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(1));
		// choose a random element in Zp*
		generateAlpha();
	}

	/**
	 * generateA generates parameter a
	 */
	private void generateA() {
		// generate a random element
		a = generateK();
	}

	/**
	 * generateK generates parameter k
	 * 
	 * @return k
	 */
	private BigInteger generateK() {
		// generate a random element
		BigInteger k = new BigInteger(p.bitLength(), random);
		// while it is not in [1, p-2]
		while (BigInteger.ONE.compareTo(k) > 0
				|| p.subtract(BigInteger.valueOf(2)).compareTo(k) < 0) {
			// generate new random element
			k = new BigInteger(p.bitLength(), random);
		}
		// return it
		return k;
	}

	/**
	 * generateAlphaA generates alpha^a mod p
	 */
	private void generateAlphaA() {
		alphaA = alpha.modPow(a.mod(p.subtract(BigInteger.valueOf(1))), p);
	}

	/**
	 * deciphers encrypted text
	 * 
	 * @param cipherText
	 *            the cipher text
	 * @return deciphered text
	 */
	String decrypt(String cipherText) throws Exception {
		StringTokenizer stringTokenizer = new StringTokenizer(cipherText, "!");
		// test for existence
		if (!stringTokenizer.hasMoreTokens())
			throw new Exception();
		// set gamma
		BigInteger gamma = new BigInteger(stringTokenizer.nextToken());
		// test for existence
		if (!stringTokenizer.hasMoreTokens())
			throw new Exception();
		// set delta
		BigInteger delta = new BigInteger(stringTokenizer.nextToken());
		// return deciphered text
		return delta
				.multiply(
						gamma.modPow(p.subtract(BigInteger.ONE).subtract(a), p))
				.mod(p).toString();
	}

	/**
	 * encrypt encrypts plain text
	 * 
	 * @param plainText
	 *            the plain text
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String plainText) throws Exception {
		BigInteger m = new BigInteger(plainText);
		// test plain text validity
		if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(p) >= 0) {
			// throw invalid exception
			throw new Exception("message is not in the interval [0, p-1]");
		}
		// generate k as a random in [1, p-2]
		BigInteger k = generateK();
		// set gamma
		BigInteger gamma = alpha.modPow(k, p);
		// set delta
		BigInteger delta = m.multiply(alphaA.modPow(k, p)).mod(p);
		// return encrypted text
		return gamma.toString() + "!" + delta.toString();
	}

	/**
	 * re-encrypt re-encrypts cipher text
	 * 
	 * @param cipherText
	 *            the cipher text
	 * @return
	 * @throws Exception
	 */
	public String reencrypt(String cipherText) throws Exception {
		StringTokenizer stringTokenizer = new StringTokenizer(cipherText, "!");
		// test for existence
		if (!stringTokenizer.hasMoreTokens()) {
			System.out.println("first problem for string " + cipherText);
			throw new Exception();
		}
		// set gamma
		BigInteger gamma = new BigInteger(stringTokenizer.nextToken());
		// test for existence
		if (!stringTokenizer.hasMoreTokens()) {
			System.out.println("second problem for string " + cipherText);
			throw new Exception();
		}
		// set delta
		BigInteger delta = new BigInteger(stringTokenizer.nextToken());
		// generate k as a random in [1, p-2]
		BigInteger k = generateK();
		// set gamma as gamma alpha^k
		gamma = gamma.multiply(alpha.modPow(k, p)).mod(p);
		// set delta as delta alphaA^k
		delta = delta.multiply(alphaA.modPow(k, p)).mod(p);
		// return encrypted text
		return gamma.toString() + "!" + delta.toString();
	}

	/**
	 * constructor
	 * 
	 * @param bits
	 *            number of bits
	 * @throws Exception
	 */
	ElGamal(int bits) throws Exception {
		// are bits enough?
		if (bits <= 0) {
			// throw invalid exception
			throw new Exception("Number of bits is too small");
		}
		// set number of bits
		this.bits = bits;
		// generate p and alpha
		generatePAlpha();
		// generate a
		generateA();
		// compute alpha^a
		generateAlphaA();
		// System.out.println(p.toString());
		// System.out.println(alpha.toString());
		// System.out.println(a.toString());
	}

	ElGamal(String fileName) throws IOException {

		// create buffered reader
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				fileName));

		// create line
		String line;

		// test line
		if ((line = bufferedReader.readLine()) == null)
			throw new IOException();

		// get p
		p = new BigInteger(line);

		// test line
		if ((line = bufferedReader.readLine()) == null)
			throw new IOException();

		// get alpha
		alpha = new BigInteger(line);

		// test line
		if ((line = bufferedReader.readLine()) == null)
			throw new IOException();

		// get a
		a = new BigInteger(line);

		// generate alphaA
		generateAlphaA();

		// close reader
		bufferedReader.close();

	}

	/**
	 * encrypts a long plain text
	 * 
	 * @param plainText
	 *            plain text
	 * @return
	 */
	public String encryptLongString(String plainText) {

		// set final content
		String content = "";

		// set initial simplePlain
		BigInteger simplePlain = BigInteger.ZERO;

		// iterate all bytes
		for (int index = 0; index < plainText.length(); ++index) {

			// reset simplePlain
			if (index % 56 == 0)
				simplePlain = BigInteger.ZERO;

			// add value to simple plain
			simplePlain = simplePlain.or(new BigInteger(new Integer(
					(int) plainText.charAt(index)).toString())
					.shiftLeft(index % 56 * 16));

			// is it time to pack?
			if (index % 56 == 55 || index == plainText.length() - 1) {

				// add size to simple plain
				simplePlain = simplePlain.or(new BigInteger(new Integer(
						index % 56).toString()).shiftLeft(896));

				// add random bits to simple plain
				simplePlain = simplePlain.or((new BigInteger(120, random))
						.shiftLeft(902));

				try {

					// set content with this new number
					content += "#"
							+ new String(encrypt(simplePlain.toString()));

				} catch (Exception exception) {

				}

			}

		}

		// return content
		return content.substring(1);

	}

	/**
	 * decrypts long ciphertext
	 * 
	 * @param cipherText
	 *            cipher text
	 * @return
	 * @throws Exception
	 */
	public String decryptLongString(String cipherText) throws Exception {

		// reset content
		String content = "";

		// set complicated text
		BigInteger complicatedText;

		// set number of characters
		int numberOfCharacters;

		// add # token extractor
		StringTokenizer hashTokenizer = new StringTokenizer(cipherText, "#");

		// iterate all tokens
		while (hashTokenizer.hasMoreTokens()) {

			// decipher token
			complicatedText = new BigInteger(decrypt(hashTokenizer.nextToken()));

			// get number of characters
			numberOfCharacters = Integer.parseInt(complicatedText
					.shiftRight(896)
					.and(new BigInteger(new Integer("63").toString()))
					.toString());

			// iterate for number of characters
			for (int jndex = 0; jndex <= numberOfCharacters; ++jndex) {

				// set character
				char character = (char) Integer.parseInt(complicatedText
						.shiftRight(jndex * 16)
						.and(new BigInteger(new Integer("65535").toString()))
						.toString());

				// add byte
				content = content + character;

			}

		}

		// return content
		return content;

	}

	/**
	 * re-encrypts long ciphertext
	 * 
	 * @param cipherText
	 *            cipher text
	 * @return
	 * @throws Exception
	 */
	public String reencryptLongString(String cipherText) throws Exception {

		// reset content
		String content = "";

		// add # token extractor
		StringTokenizer hashTokenizer = new StringTokenizer(cipherText, "#");

		// iterate all tokens
		while (hashTokenizer.hasMoreTokens()) {

			// set content
			content = content + "#" + reencrypt(hashTokenizer.nextToken());

		}

		// return content
		return content.substring(1);

	}

}
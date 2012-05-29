package org.manea.vlad.moderator;

import java.io.*;
import java.util.StringTokenizer;

/**
 * receives messages
 * 
 * @author administrator
 */
public abstract class Recipient {

	// create elGamal instance
	private static ElGamal elGamal;

	/**
	 * registers elGamal
	 * 
	 * @param fileName
	 *            file name
	 * @throws IOException
	 * @throws Exception
	 */
	public static void setElGamal(String fileName) throws IOException,
			Exception {

		// test for null string
		if (fileName == null)
			throw new Exception();

		// set algorithm
		elGamal = new ElGamal(fileName);

	}

	/**
	 * gets ElGamal
	 * 
	 * @return elGamal system
	 */
	public static ElGamal getElGamal() {

		// get elGamal system
		return elGamal;

	}

	/**
	 * deciphers a file
	 * 
	 * @param inputFileName
	 *            the input file name
	 * @param outputFileName
	 *            the output file name
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void decryptFileToFile(String inputFileName,
			String outputFileName) throws FileNotFoundException, IOException,
			Exception {

		// create input file
		File inputFile = new File(inputFileName);

		// check if input file is not directory
		if (!inputFile.isDirectory())
			throw new IOException();

		// create print writer
		PrintWriter printWriter = new PrintWriter(new FileWriter(
				outputFileName, false));

		// get all children of the file
		File[] children = new File(inputFileName)
				.listFiles(new MinimizedFileFilter());

		// iterate all children
		for (int index = 0; index < children.length; ++index) {

			// create buffered reader
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					children[index].getAbsolutePath()));

			// create line string and content
			String line;

			// read all lines
			while ((line = bufferedReader.readLine()) != null) {

				// add space token extractor
				StringTokenizer spaceTokenizer = new StringTokenizer(line);

				// check for initial number existence
				if (!spaceTokenizer.hasMoreTokens())
					throw new Exception();

				// lose the first number
				spaceTokenizer.nextToken();

				// check for second number existence
				if (!spaceTokenizer.hasMoreTokens())
					throw new Exception();

				// add content to answer
				printWriter.println(elGamal.decryptLongString(spaceTokenizer
						.nextToken()));

				// print writer flush
				printWriter.flush();

			}

			// close buffered reader
			bufferedReader.close();

		}

		// close print writer
		printWriter.close();

	}

}

package org.manea.vlad.moderator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public abstract class Sender {

	/**
	 * ciphers a file
	 * 
	 * @param inputFileName
	 *            the input file name
	 * @param outputFileName
	 *            the output file name
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void encryptFileToFile(String inputFileName,
			String outputFileName) {

		// create file input and output handlers
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;

		// create vector of words
		Vector<String> words = new Vector<String>();

		try {

			// create buffered reader
			bufferedReader = new BufferedReader(new FileReader(inputFileName));

			// create line
			String line;

			// read all lines
			while ((line = bufferedReader.readLine()) != null) {

				// add content to words
				words.add("0 " + Recipient.getElGamal().encryptLongString(line));

			}

		} catch (Exception exception) {

			// write warning
			System.out.println("WARN: could not open initial file.");

		} finally {

			try {

				// close reader
				if (bufferedReader != null)
					bufferedReader.close();

			} catch (IOException exception) {

				// do nothing with exception

			}

		}

		try {

			// create print writer
			printWriter = new PrintWriter(new FileWriter(outputFileName, false));

			// iterate words
			for (int index = 0; index < words.size(); ++index) {

				// put print writer
				printWriter.println(words.get(index));

			}

			// print writer flush
			printWriter.flush();

		} catch (Exception exception) {

			// write warning
			System.out.println("WARN: could not open input file.");

		} finally {

			// close reader
			if (printWriter != null)
				printWriter.close();

		}

	}

}

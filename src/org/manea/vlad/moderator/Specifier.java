package org.manea.vlad.moderator;

import java.io.File;

import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * it specifies all parameters for the moderator and mixes
 * 
 * @author administrator
 */
public class Specifier {

	// constant string names
	private static final String mixesTagName = "mixes";
	private static final String queuesTagName = "queues";
	private static final String operationsTagName = "operations";
	private static final String inputTagName = "input";
	private static final String selectedTagName = "selected";
	private static final String nextTagName = "next";
	private static final String assignedTagName = "assigned";
	private static final String countedTagName = "counted";
	private static final String minimizedTagName = "minimized";
	private static final String roundTagName = "round";
	private static final String mixTagName = "mix";
	private static final String initialTagName = "initial";
	private static final String finalTagName = "final";
	private static final String elgamalTagName = "elgamal";
	private static final String inputFileName = "/input";

	// delete file warning message
	public static final String deleteFileWarningMessage = "WARN: could not delete file: ";

	/**
	 * deletes possibly non empty file
	 * 
	 * @param file
	 *            the file
	 * @return true if and only if file was deleted
	 */
	public static boolean deleteFile(File file) {

		// check if the file is a directory
		if (file.isDirectory()) {

			// get files located in the folder
			String[] children = file.list();

			// iterate all children
			for (int index = 0; index < children.length; index++) {

				// delete the file recursively and return false on failure
				if (!deleteFile(new File(file, children[index])))
					return false;

			}

		}

		// return the deletion of the file which is now surely empty
		return file.delete();

	}

	/**
	 * main method
	 * 
	 * @param args
	 *            arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// test if argument strings are correct
		if (args == null)
			throw new NullPointerException();

		// test if argument string contains the file name
		if (args.length < 1)
			throw new Exception();

		// create file
		File XMLfile = new File(args[0]);

		// create document builder factory
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();

		// create document builder
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();

		// create document
		Document document = documentBuilder.parse(XMLfile);

		// normalize document root element
		document.getDocumentElement().normalize();

		// set number of mixes
		Moderator.INSTANCE.setNumberOfMixes(Integer.parseInt(document
				.getDocumentElement().getElementsByTagName(mixesTagName)
				.item(0).getChildNodes().item(0).getNodeValue()));

		// set number of queues
		Moderator.INSTANCE.setNumberOfQueues(Integer.parseInt(document
				.getDocumentElement().getElementsByTagName(queuesTagName)
				.item(0).getChildNodes().item(0).getNodeValue()));

		// set number of operations
		Moderator.INSTANCE.setNumberOfOperations(Integer.parseInt(document
				.getDocumentElement().getElementsByTagName(operationsTagName)
				.item(0).getChildNodes().item(0).getNodeValue()));

		// set input file
		Moderator.INSTANCE.setInputFile(document.getDocumentElement()
				.getElementsByTagName(inputTagName).item(0).getChildNodes()
				.item(0).getNodeValue());

		// set assigned file
		Moderator.INSTANCE.setAssignedFile(document.getDocumentElement()
				.getElementsByTagName(assignedTagName).item(0).getChildNodes()
				.item(0).getNodeValue());

		// set counted file
		Moderator.INSTANCE.setCountedFile(document.getDocumentElement()
				.getElementsByTagName(countedTagName).item(0).getChildNodes()
				.item(0).getNodeValue());

		// set counted file
		Moderator.INSTANCE.setNextFile(document.getDocumentElement()
				.getElementsByTagName(nextTagName).item(0).getChildNodes()
				.item(0).getNodeValue());

		// set minimized file
		Moderator.INSTANCE.setMinimizedFile(document.getDocumentElement()
				.getElementsByTagName(minimizedTagName).item(0).getChildNodes()
				.item(0).getNodeValue());

		// set selected file
		Moderator.INSTANCE.setSelectedFile(document.getDocumentElement()
				.getElementsByTagName(selectedTagName).item(0).getChildNodes()
				.item(0).getNodeValue());

		// get nodes for round files
		NodeList roundFileNodes = document.getDocumentElement()
				.getElementsByTagName(roundTagName);

		// create round file names
		String[] roundFileNodeNames = new String[roundFileNodes.getLength()];

		// iterate all round file nodes
		for (int index = 0; index < roundFileNodes.getLength(); ++index)
			roundFileNodeNames[index] = roundFileNodes.item(index)
					.getChildNodes().item(0).getNodeValue();

		// set round files if correct
		Moderator.INSTANCE.setRoundFiles(roundFileNodeNames,
				1 + 2 * Moderator.INSTANCE.getNumberOfOperations());

		// get nodes for mix files
		NodeList mixFileNodes = document.getDocumentElement()
				.getElementsByTagName(mixTagName);

		// create mix file names
		String[] mixFileNodeNames = new String[mixFileNodes.getLength()];

		// iterate all mix file nodes
		for (int index = 0; index < mixFileNodes.getLength(); ++index)
			mixFileNodeNames[index] = mixFileNodes.item(index).getChildNodes()
					.item(0).getNodeValue();

		// set round files if correct
		Moderator.INSTANCE.setMixFiles(mixFileNodeNames,
				Moderator.INSTANCE.getNumberOfMixes());

		// set receiver algorithm
		Recipient.setElGamal(document.getDocumentElement()
				.getElementsByTagName(elgamalTagName).item(0).getChildNodes()
				.item(0).getNodeValue());

		// encrypt data
		Sender.encryptFileToFile(document.getDocumentElement()
				.getElementsByTagName(initialTagName).item(0).getChildNodes()
				.item(0).getNodeValue(), document.getDocumentElement()
				.getElementsByTagName(inputTagName).item(0).getChildNodes()
				.item(0).getNodeValue()
				+ inputFileName);

		// run moderator
		Moderator.INSTANCE.run();

		// decipher data
		Recipient.decryptFileToFile(
				roundFileNodeNames[roundFileNodeNames.length - 1], document
						.getDocumentElement()
						.getElementsByTagName(finalTagName).item(0)
						.getChildNodes().item(0).getNodeValue());

	}

}
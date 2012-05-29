package org.manea.vlad.moderator;

import java.io.*;
import java.util.StringTokenizer;

import org.manea.vlad.assignedmixcounter.AssignedMixCounterEntryPoint;
import org.manea.vlad.generalminimumcomputer.GeneralMinimumComputerEntryPoint;
import org.manea.vlad.messagefilter.MessageFilterEntryPoint;
import org.manea.vlad.messagerandomizer.MessageRandomizerEntryPoint;
import org.manea.vlad.messageselector.MessageSelectorEntryPoint;
import org.manea.vlad.probablemixassigner.ProbableMixAssignerEntryPoint;

/**
 * is the moderator class
 * 
 * @author administrator
 */
public class Moderator {

	// create unique instance
	public final static Moderator INSTANCE = new Moderator();

	private final String warningZeroMessages = "WARN: Minimum could not be found. Maybe you have no messages!";

	// number of mixes
	private int numberOfMixes = 1;

	// number of operations
	private int numberOfOperations = 1;

	// number of queues
	private int numberOfQueues = 1;

	// number of memories
	private int numberOfMemory = 1;

	// number of messages
	private int numberOfMessages = Integer.MAX_VALUE;

	// input file location
	private String inputFile;

	// assigned file location
	private String assignedFile;

	// counted file location
	private String countedFile;

	// minimized file location
	private String minimizedFile;

	// selected file location
	private String selectedFile;

	// next file location
	private String nextFile;

	// round files locations
	private String[] roundFiles;

	// mix files locations
	private String[] mixFiles;

	/**
	 * moderator constructor
	 */
	private Moderator() {

		// implemented for singleton purpose

	}

	/**
	 * gets the input file
	 * 
	 * @return the inputFile
	 */
	public synchronized String getInputFile() {

		// get input file
		return inputFile;

	}

	/**
	 * sets the input file
	 * 
	 * @param inputFile
	 *            the input file
	 * 
	 * @throws Exception
	 */
	public synchronized void setInputFile(String inputFile) throws Exception {

		// test for incorrect input file
		if (inputFile == null)
			throw new NullPointerException();

		// set input file
		this.inputFile = inputFile;

	}

	/**
	 * gets the assigned file
	 * 
	 * @return the assignedFile
	 */
	public synchronized String getAssignedFile() {

		// get assigned file
		return assignedFile;

	}

	/**
	 * sets the assigned file
	 * 
	 * @param assignedFile
	 *            the assigned file
	 * 
	 * @throws Exception
	 */
	public synchronized void setAssignedFile(String assignedFile)
			throws Exception {

		// test for incorrect assigned file
		if (assignedFile == null)
			throw new NullPointerException();

		// set assigned file
		this.assignedFile = assignedFile;

	}

	/**
	 * gets the next file
	 * 
	 * @return the nextFile
	 */
	public synchronized String getNextFile() {

		// get next file
		return nextFile;

	}

	/**
	 * sets the next file
	 * 
	 * @param nextFile
	 *            the next file
	 * 
	 * @throws Exception
	 */
	public synchronized void setNextFile(String nextFile) throws Exception {

		// test for incorrect next file
		if (nextFile == null)
			throw new NullPointerException();

		// set next file
		this.nextFile = nextFile;

	}

	/**
	 * gets the minimized file
	 * 
	 * @return the minimizedFile
	 */
	public synchronized String getMinimizedFile() {

		// get minimized file
		return minimizedFile;

	}

	/**
	 * sets the minimized file
	 * 
	 * @param minimizedFile
	 *            the minimized file
	 * 
	 * @throws Exception
	 */
	public synchronized void setMinimizedFile(String minimizedFile)
			throws Exception {

		// test for incorrect minimized file
		if (minimizedFile == null)
			throw new NullPointerException();

		// set selected file
		this.minimizedFile = minimizedFile;

	}

	/**
	 * gets the selected file
	 * 
	 * @return the selectedFile
	 */
	public synchronized String getSelectedFile() {

		// get selected file
		return selectedFile;

	}

	/**
	 * sets the selected file
	 * 
	 * @param selectedFile
	 *            the selected file
	 * 
	 * @throws Exception
	 */
	public synchronized void setSelectedFile(String selectedFile)
			throws Exception {

		// test for incorrect selected file
		if (selectedFile == null)
			throw new NullPointerException();

		// set selected file
		this.selectedFile = selectedFile;

	}

	/**
	 * gets the counted file
	 * 
	 * @return the countedFile
	 */
	public synchronized String getCountedFile() {

		// get counted file
		return countedFile;

	}

	/**
	 * sets the counted file
	 * 
	 * @param countedFile
	 *            the counted file
	 * 
	 * @throws Exception
	 */
	public synchronized void setCountedFile(String countedFile)
			throws Exception {

		// test for incorrect counted file
		if (countedFile == null)
			throw new NullPointerException();

		// set counted file
		this.countedFile = countedFile;

	}

	/**
	 * gets the round file
	 * 
	 * @param round
	 *            number of round
	 * 
	 * @return the round file
	 */
	public synchronized String getRoundFile(int round) {

		// test for round in bounds
		if (round < 0 || round >= roundFiles.length)
			throw new ArrayIndexOutOfBoundsException();

		// get round file
		return roundFiles[round];

	}

	/**
	 * sets the round files
	 * 
	 * @param roundFiles
	 *            the round files
	 * @param size
	 *            the required size
	 * 
	 * @throws Exception
	 */
	public synchronized void setRoundFiles(String[] roundFiles, int size)
			throws Exception {

		// test for incorrect round files
		if (roundFiles == null)
			throw new NullPointerException();

		// test for incorrect number of round files
		if (roundFiles.length != size)
			throw new Exception();

		// set this round files
		this.roundFiles = new String[size];

		// set array by copying
		System.arraycopy(roundFiles, 0, this.roundFiles, 0, size);

	}

	/**
	 * gets the mix file
	 * 
	 * @param mix
	 *            index of mix
	 * 
	 * @return the mix file
	 */
	public synchronized String getMixFile(int mix) {

		// test for mix in bounds
		if (mix < 0 || mix >= roundFiles.length)
			throw new ArrayIndexOutOfBoundsException();

		// get mix file
		return mixFiles[mix];

	}

	/**
	 * sets the mix files
	 * 
	 * @param mixFiles
	 *            the mix files
	 * @param size
	 *            the required size
	 * 
	 * @throws Exception
	 */
	public synchronized void setMixFiles(String[] mixFiles, int size)
			throws Exception {

		// test for incorrect mix files
		if (mixFiles == null)
			throw new NullPointerException();

		// test for incorrect number of mix files
		if (mixFiles.length != size)
			throw new Exception();

		// set this mix files
		this.mixFiles = new String[size];

		// set array by copying
		System.arraycopy(mixFiles, 0, this.mixFiles, 0, size);

	}

	/**
	 * gets the number of queues
	 * 
	 * @return the numberOfQueues
	 */
	public synchronized int getNumberOfQueues() {

		// get number of queues
		return numberOfQueues;

	}

	/**
	 * sets the number of queues as a non-negative value
	 * 
	 * @param numberOfQueues
	 *            the numberOfQueues to set
	 * @throws Exception
	 */
	public synchronized void setNumberOfQueues(int numberOfQueues)
			throws Exception {

		// test for incorrect number of queues
		if (numberOfQueues <= 0)
			throw new Exception();

		// set number of queues
		this.numberOfQueues = numberOfQueues;

	}

	/**
	 * gets the number of mixes
	 * 
	 * @return the numberOfMixes
	 */
	public synchronized int getNumberOfMixes() {

		// get number of mixes
		return numberOfMixes;

	}

	/**
	 * sets the number of mixes as a non-negative value
	 * 
	 * @param numberOfMixes
	 *            the numberOfMixes to set
	 * @throws Exception
	 */
	public synchronized void setNumberOfMixes(int numberOfMixes)
			throws Exception {

		// test for incorrect number of mixes
		if (numberOfMixes <= 0)
			throw new Exception();

		// set number of mixes
		this.numberOfMixes = numberOfMixes;

	}

	/**
	 * gets the number of memory
	 * 
	 * @return the numberOfMemory
	 */
	public synchronized int getNumberOfMemory() {

		// get number of memory
		return numberOfMemory;

	}

	/**
	 * sets the number of memory as a non-negative value
	 * 
	 * @param numberOfMemory
	 *            the numberOfMemory to set
	 * @throws Exception
	 */
	public synchronized void setNumberOfMemory(int numberOfMemory)
			throws Exception {

		// test for incorrect number of memory
		if (numberOfMemory <= 0)
			throw new Exception();

		// set number of memory
		this.numberOfMemory = numberOfMemory;

	}

	/**
	 * gets the number of operations
	 * 
	 * @return the numberOfOperations
	 */
	public synchronized int getNumberOfOperations() {

		// get number of operations
		return numberOfOperations;

	}

	/**
	 * sets the number of operations as a non-negative value
	 * 
	 * @param numberOfOperations
	 *            the numberOfOperations to set
	 * @throws Exception
	 */
	public synchronized void setNumberOfOperations(int numberOfOperations)
			throws Exception {

		// test for incorrect number of operations
		if (numberOfOperations <= 0)
			throw new Exception();

		// set number of operations
		this.numberOfOperations = numberOfOperations;

	}

	/**
	 * gets the number of messages
	 * 
	 * @return the numberOfMessages
	 */
	public synchronized int getNumberOfMessages() {

		// get number of messages
		return numberOfMessages;

	}

	/**
	 * sets the number of messages as a non-negative value<br />
	 * the number of messages is the number of messages each mix must receive<br />
	 * the number of messages must be a multiple of the number of mixes
	 * 
	 * @param numberOfMessages
	 *            the numberOfMessages to set
	 * @throws Exception
	 */
	public synchronized void setNumberOfMessages(int numberOfMessages)
			throws Exception {

		// test for incorrect number of messages
		if (numberOfMessages < 0)
			throw new Exception();

		// test for not multiple of n
		if (numberOfMessages % getNumberOfMixes() != 0)
			throw new Exception();

		// set number of messages
		this.numberOfMessages = numberOfMessages;

	}

	/**
	 * gets minimum
	 * 
	 * @param file
	 *            the file to be read
	 * @return the minimum number
	 * @throws IOException
	 */
	private int getMinimum(File file) throws IOException {

		// test if file is not directory
		if (!file.isDirectory())
			throw new IOException();

		// obtain children files
		File[] children = file.listFiles(new MinimizedFileFilter());

		// check if children has files
		if (children.length < 1)
			throw new IOException();

		// set new file
		file = children[0];

		// create a buffer reader
		BufferedReader bufferReader = new BufferedReader(new FileReader(
				file.getAbsolutePath()));

		// create string
		String line = bufferReader.readLine();

		// try to read line
		if (line == null) {

			// write warning
			System.out.println(warningZeroMessages);

			// there is no line
			return 0;

		}

		// create string token creator
		StringTokenizer stringTokenizer = new StringTokenizer(line);

		// check if the line has more tokens
		if (!stringTokenizer.hasMoreTokens())
			throw new IOException();

		// lose the first token
		stringTokenizer.nextToken();

		// check if the line has more tokens
		if (!stringTokenizer.hasMoreTokens())
			throw new IOException();

		// return the integer value of the next token
		return Integer.parseInt(stringTokenizer.nextToken());

	}

	/**
	 * run method
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		// create probable mix assigner
		ProbableMixAssignerEntryPoint probableMixAssignerEntryPoint = new ProbableMixAssignerEntryPoint(
				getInputFile(), getAssignedFile());

		// try to delete the assigned folder
		if (!Specifier.deleteFile(new File(getAssignedFile())))
			System.out.println(Specifier.deleteFileWarningMessage
					+ getAssignedFile());

		// run probable mix assigner
		probableMixAssignerEntryPoint.run();

		// create assigned mix counter
		AssignedMixCounterEntryPoint assignedMixCounterEntryPoint = new AssignedMixCounterEntryPoint(
				getAssignedFile(), getCountedFile());

		// try to delete the counted folder
		if (!Specifier.deleteFile(new File(getCountedFile())))
			System.out.println(Specifier.deleteFileWarningMessage
					+ getCountedFile());

		// run assigned mix counter
		assignedMixCounterEntryPoint.run();

		// create assigned mix counter
		GeneralMinimumComputerEntryPoint generalMinimumComputerEntryPoint = new GeneralMinimumComputerEntryPoint(
				getCountedFile(), getMinimizedFile());

		// try to delete the minimized folder
		if (!Specifier.deleteFile(new File(getMinimizedFile())))
			System.out.println(Specifier.deleteFileWarningMessage
					+ getMinimizedFile());

		// run assigned mix minimum computer
		generalMinimumComputerEntryPoint.run();

		// read number of messages
		int numberOfMessages = getMinimum(new File(getMinimizedFile()));

		// test for positive minimum
		if (numberOfMessages <= 0)
			return;

		// set number of messages
		Moderator.INSTANCE.setNumberOfMessages(numberOfMessages);

		/*
		 * write the number of messages
		 * System.out.println("The number of accepted messages per server is " +
		 * getNumberOfMessages());
		 */

		// create message selector
		MessageSelectorEntryPoint messageSelectorEntryPoint = new MessageSelectorEntryPoint(
				getAssignedFile(), getSelectedFile());

		// try to delete the selected folder
		if (!Specifier.deleteFile(new File(getSelectedFile())))
			System.out.println(Specifier.deleteFileWarningMessage
					+ getSelectedFile());

		// run assigned mix selector
		messageSelectorEntryPoint.run();

		// create message filter[true]
		MessageFilterEntryPoint messageFilterEntryPointTrue = new MessageFilterEntryPoint(
				getSelectedFile(), getRoundFile(0), true);

		// try to delete the round folder
		if (!Specifier.deleteFile(new File(roundFiles[0])))
			System.out.println(Specifier.deleteFileWarningMessage
					+ getRoundFile(0));

		// run assigned mix filter
		messageFilterEntryPointTrue.run();

		// create message filter[false]
		MessageFilterEntryPoint messageFilterEntryPointFalse = new MessageFilterEntryPoint(
				getSelectedFile(), getNextFile(), false);

		// try to delete the next folder
		if (!Specifier.deleteFile(new File(getNextFile())))
			System.out.println(Specifier.deleteFileWarningMessage
					+ getNextFile());

		// run assigned mix filter
		messageFilterEntryPointFalse.run();

		// set number of operations
		Moderator.INSTANCE.setNumberOfOperations(numberOfOperations);

		// set the index
		int index;

		// make less than the operations non-redistribute randomize operations
		for (index = 1; index <= Moderator.INSTANCE.getNumberOfOperations() - 1; ++index) {

			// create message randomizer[false]
			MessageRandomizerEntryPoint messageRandomizerEntryPointI = new MessageRandomizerEntryPoint(
					getRoundFile(index - 1), getRoundFile(index), false);

			// try to delete the output folder
			if (!Specifier.deleteFile(new File(getRoundFile(index))))
				System.out.println(Specifier.deleteFileWarningMessage
						+ getRoundFile(index));

			// run assigned mix randomizer
			messageRandomizerEntryPointI.run();

		}

		// create message randomizer[false]
		MessageRandomizerEntryPoint messageRandomizerEntryPoint = new MessageRandomizerEntryPoint(
				getRoundFile(index - 1), getRoundFile(index), true);

		// try to delete the output folder
		if (!Specifier.deleteFile(new File(getRoundFile(index))))
			System.out.println(Specifier.deleteFileWarningMessage
					+ getRoundFile(index));

		// run assigned mix randomizer
		messageRandomizerEntryPoint.run();

		// make the operations non-redistribute randomize operations
		for (++index; index <= 2 * Moderator.INSTANCE.getNumberOfOperations(); ++index) {

			// create message randomizer[false]
			MessageRandomizerEntryPoint messageRandomizerEntryPointI = new MessageRandomizerEntryPoint(
					getRoundFile(index - 1), getRoundFile(index), false);

			// try to delete the output folder
			if (!Specifier.deleteFile(new File(getRoundFile(index))))
				System.out.println(Specifier.deleteFileWarningMessage
						+ getRoundFile(index));

			// run assigned mix randomizer
			messageRandomizerEntryPointI.run();

		}

	}

}

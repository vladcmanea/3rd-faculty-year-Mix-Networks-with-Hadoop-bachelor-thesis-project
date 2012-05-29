package org.manea.vlad.entrypoint;

import org.manea.vlad.moderator.Moderator;

/**
 * is entry point superclass for entry points which know their moderators
 * 
 * @author administrator
 */
public abstract class EntryPoint {

	// moderator
	private static Moderator moderator = Moderator.INSTANCE;

	// inputFolder
	private String inputFolder;

	// outputFolder
	private String outputFolder;

	/**
	 * gets the moderator
	 * 
	 * @return the moderator
	 */
	public static synchronized final Moderator getModerator() {

		// return the moderator
		return moderator;

	}

	/**
	 * gets the input folder
	 * 
	 * @return the inputFolder
	 */
	protected synchronized final String getInputFolder() {

		// return the input folder
		return inputFolder;

	}

	/**
	 * sets the input folder
	 * 
	 * @param inputFolder
	 *            the inputFolder to set
	 */
	private synchronized final void setInputFolder(String inputFolder) {

		// check to see if input folder exists
		if (inputFolder == null)
			throw new NullPointerException();

		// set the input folder
		this.inputFolder = inputFolder;

	}

	/**
	 * gets the output folder
	 * 
	 * @return the outputFolder
	 */
	protected synchronized final String getOutputFolder() {

		// return the output folder
		return outputFolder;

	}

	/**
	 * sets the output folder
	 * 
	 * @param outputFolder
	 *            the outputFolder to set
	 */
	private synchronized final void setOutputFolder(String outputFolder) {

		// check to see if output folder exists
		if (outputFolder == null)
			throw new NullPointerException();

		// set the output folder
		this.outputFolder = outputFolder;

	}

	/**
	 * configures and runs a job
	 * 
	 * @throws Exception
	 */
	public abstract void run() throws Exception;

	/**
	 * constructs the entry point
	 * 
	 * @param inputFolder
	 *            the input folder
	 * @param outputFolder
	 *            the output folder
	 */
	public EntryPoint(String inputFolder, String outputFolder) {

		// set all fields
		setInputFolder(inputFolder);
		setOutputFolder(outputFolder);

	}

}

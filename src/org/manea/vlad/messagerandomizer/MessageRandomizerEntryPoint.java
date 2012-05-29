package org.manea.vlad.messagerandomizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;

import org.manea.vlad.entrypoint.EntryPoint;

/**
 * is the configure class for message randomize
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
public class MessageRandomizerEntryPoint extends EntryPoint {

	// redistribute of messages
	private boolean redistribute = false;

	/**
	 * gets the redistribute
	 * 
	 * @return the redistribute
	 */
	boolean isRedistribute() {

		// get redistribute
		return redistribute;

	}

	/**
	 * sets the redistribute<br />
	 * if redistribute is true, the messages are sent evenly to all n mixes<br />
	 * if redistribute is false, the messages are all sent to the next mix<br />
	 * 
	 * @param numberOfMessages
	 *            the numberOfMessages to set
	 */
	private void setRedistribute(boolean redistribute) {

		// set redistribute
		this.redistribute = redistribute;

	}

	/**
	 * constructs the entry point for message randomizer
	 * 
	 * @param inputFolder
	 *            the input folder
	 * @param outputFolder
	 *            the output folder
	 * @param redistribute
	 *            the redistribute
	 * @throws Exception
	 */
	public MessageRandomizerEntryPoint(String inputFolder, String outputFolder,
			boolean redistribute) {

		// call super
		super(inputFolder, outputFolder);

		// set redistribute
		setRedistribute(redistribute);

	}

	/**
	 * runs for message randomizer
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		// create a job configuration
		JobConf job = new JobConf(MessageRandomizerEntryPoint.class);

		// set job name
		job.setJobName(MessageRandomizerEntryPoint.class.getName());

		// set output key and value classes
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);

		// set map class
		job.setMapperClass(MessageRandomizerMapper.class);

		// set reduce class according to redistribute
		if (isRedistribute())
			job.setReducerClass(MessageRandomizerRedistributeReducer.class);
		else
			job.setReducerClass(MessageRandomizerProceedReducer.class);

		// set input and output paths for files
		FileInputFormat.setInputPaths(job, new Path(getInputFolder()));
		FileOutputFormat.setOutputPath(job, new Path(getOutputFolder()));

		// run job
		JobClient.runJob(job);

	}

}

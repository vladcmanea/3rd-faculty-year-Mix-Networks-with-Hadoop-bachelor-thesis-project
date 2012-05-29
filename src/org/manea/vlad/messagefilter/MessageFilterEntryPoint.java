package org.manea.vlad.messagefilter;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;

import org.manea.vlad.entrypoint.EntryPoint;

/**
 * is the configure class for message filter of messages
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
public class MessageFilterEntryPoint extends EntryPoint {

	// filter criteria
	private boolean criteria = false;

	/**
	 * gets the criteria
	 * 
	 * @return the criteria
	 */
	boolean isCriteria() {

		// get criteria
		return criteria;

	}

	/**
	 * sets the criteria
	 * 
	 * @param criteria
	 *            the criteria to set
	 */
	private void setCriteria(boolean criteria) {

		// set number of messages
		this.criteria = criteria;

	}

	/**
	 * constructs the entry point for message filter
	 * 
	 * @param inputFolder
	 *            the input folder
	 * @param outputFolder
	 *            the output folder
	 * @param criteria
	 *            the criteria
	 * @throws Exception
	 */
	public MessageFilterEntryPoint(String inputFolder, String outputFolder,
			boolean criteria) {

		// call super
		super(inputFolder, outputFolder);

		// set criteria
		setCriteria(criteria);

	}

	/**
	 * runs for message filter
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		// create a job configuration
		JobConf job = new JobConf(MessageFilterEntryPoint.class);

		// set job name
		job.setJobName(MessageFilterEntryPoint.class.getName());

		// set output key and value classes
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);

		// set map class according to criteria
		if (isCriteria())
			job.setMapperClass(MessageFilterPassMapper.class);
		else
			job.setMapperClass(MessageFilterFailMapper.class);

		// set input and output paths for files
		FileInputFormat.setInputPaths(job, new Path(getInputFolder()));
		FileOutputFormat.setOutputPath(job, new Path(getOutputFolder()));

		// set no reducers
		job.setNumReduceTasks(0);

		// run job
		JobClient.runJob(job);

	}

}

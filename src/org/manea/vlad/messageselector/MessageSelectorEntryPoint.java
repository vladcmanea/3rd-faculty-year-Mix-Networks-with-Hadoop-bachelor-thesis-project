package org.manea.vlad.messageselector;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;

import org.manea.vlad.entrypoint.EntryPoint;

/**
 * is the configure class for message select
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
public class MessageSelectorEntryPoint extends EntryPoint {

	/**
	 * constructs the entry point for message filter
	 * 
	 * @param inputFolder
	 *            the input folder
	 * @param outputFolder
	 *            the output folder
	 * @throws Exception
	 */
	public MessageSelectorEntryPoint(String inputFolder, String outputFolder) {

		// call super
		super(inputFolder, outputFolder);

	}

	/**
	 * runs for message selector
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		// create a job configuration
		JobConf job = new JobConf(MessageSelectorEntryPoint.class);

		// set job name
		job.setJobName(MessageSelectorEntryPoint.class.getName());

		// set output key and value classes
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);

		// set map and reduce classes
		job.setMapperClass(MessageSelectorMapper.class);
		job.setReducerClass(MessageSelectorReducer.class);

		// set input and output paths for files
		FileInputFormat.setInputPaths(job, new Path(getInputFolder()));
		FileOutputFormat.setOutputPath(job, new Path(getOutputFolder()));

		// run job
		JobClient.runJob(job);

	}

}

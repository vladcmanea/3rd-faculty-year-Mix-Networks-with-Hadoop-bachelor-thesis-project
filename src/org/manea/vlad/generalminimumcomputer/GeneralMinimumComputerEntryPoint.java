package org.manea.vlad.generalminimumcomputer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;

import org.manea.vlad.entrypoint.EntryPoint;

/**
 * is the configure class for general minimum compute of mix counts
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
public class GeneralMinimumComputerEntryPoint extends EntryPoint {

	/**
	 * constructs the entry point for general minimum computer
	 * 
	 * @param inputFolder
	 *            the input folder
	 * @param outputFolder
	 *            the output folder
	 * @throws Exception
	 */
	public GeneralMinimumComputerEntryPoint(String inputFolder,
			String outputFolder) {

		// call super
		super(inputFolder, outputFolder);

	}

	/**
	 * runs for general minimum computer
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		// create a job configuration
		JobConf job = new JobConf(GeneralMinimumComputerEntryPoint.class);

		// set job name
		job.setJobName(GeneralMinimumComputerEntryPoint.class.getName());

		// set output key and value classes
		job.setOutputKeyClass(BooleanWritable.class);
		job.setOutputValueClass(IntWritable.class);

		// set map and reduce classes
		job.setMapperClass(GeneralMinimumComputerMapper.class);
		job.setReducerClass(GeneralMinimumComputerReducer.class);

		// set input and output paths for files
		FileInputFormat.setInputPaths(job, new Path(getInputFolder()));
		FileOutputFormat.setOutputPath(job, new Path(getOutputFolder()));

		// run job
		JobClient.runJob(job);

	}

}

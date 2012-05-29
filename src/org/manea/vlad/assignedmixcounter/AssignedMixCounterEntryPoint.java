package org.manea.vlad.assignedmixcounter;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;

import org.manea.vlad.entrypoint.EntryPoint;

/**
 * is the configure class for assigned mix count of messages
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
public class AssignedMixCounterEntryPoint extends EntryPoint {

	/**
	 * constructs the entry point for assigned mix counter
	 * 
	 * @param inputFolder
	 *            the input folder
	 * @param outputFolder
	 *            the output folder
	 * @throws Exception
	 */
	public AssignedMixCounterEntryPoint(String inputFolder, String outputFolder) {

		// call super
		super(inputFolder, outputFolder);

	}

	/**
	 * runs for assigned mix counter
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		// create a job configuration
		JobConf job = new JobConf(AssignedMixCounterEntryPoint.class);

		// set job name
		job.setJobName(AssignedMixCounterEntryPoint.class.getName());

		// set output key and value classes
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);

		// set map, combine and reduce classes
		job.setMapperClass(AssignedMixCounterMapper.class);
		job.setCombinerClass(AssignedMixCounterReducer.class);
		job.setReducerClass(AssignedMixCounterReducer.class);

		// set input and output paths for files
		FileInputFormat.setInputPaths(job, new Path(getInputFolder()));
		FileOutputFormat.setOutputPath(job, new Path(getOutputFolder()));

		// run job
		JobClient.runJob(job);

	}

}

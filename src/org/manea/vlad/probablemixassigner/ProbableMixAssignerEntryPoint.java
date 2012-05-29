package org.manea.vlad.probablemixassigner;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;

import org.manea.vlad.entrypoint.EntryPoint;

/**
 * is the configure class for probable mix assignment to messages
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
public class ProbableMixAssignerEntryPoint extends EntryPoint {

	/**
	 * constructs the entry point for message filter
	 * 
	 * @param inputFolder
	 *            the input folder
	 * @param outputFolder
	 *            the output folder
	 * @throws Exception
	 */
	public ProbableMixAssignerEntryPoint(String inputFolder, String outputFolder) {

		// call super
		super(inputFolder, outputFolder);

	}

	/**
	 * provides an entry point for probable mix assignment to messages
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		// create a job configuration
		JobConf job = new JobConf(ProbableMixAssignerEntryPoint.class);

		// set job name
		job.setJobName(ProbableMixAssignerEntryPoint.class.getName());

		// set output key and value classes
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		// set map class
		job.setMapperClass(ProbableMixAssignerMapper.class);

		// set input and output paths for files
		FileInputFormat.setInputPaths(job, new Path(getInputFolder()));
		FileOutputFormat.setOutputPath(job, new Path(getOutputFolder()));

		// set no reducers and argument number of mixes
		job.setNumReduceTasks(0);

		// run job
		JobClient.runJob(job);

	}

}

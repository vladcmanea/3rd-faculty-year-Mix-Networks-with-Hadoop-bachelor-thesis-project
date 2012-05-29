package org.manea.vlad.assignedmixcounter;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

/**
 * is the assigned mix count map class
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
class AssignedMixCounterMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, IntWritable, IntWritable> {

	// constant one
	private static final IntWritable ONE = new IntWritable(1);

	/**
	 * maps (mix, word) to (mix, 1)
	 * 
	 * @param key
	 *            number of file
	 * @param value
	 *            contents of file
	 * @param output
	 *            output to write to
	 * @param reporter
	 *            reporter of actions
	 * @throws IOException
	 */
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<IntWritable, IntWritable> output, Reporter reporter)
			throws IOException {

		// get line
		String line = value.toString();

		// create string token generator
		StringTokenizer tokenizer = new StringTokenizer(line);

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// get the mix token
		IntWritable mix = new IntWritable(Integer.parseInt(tokenizer
				.nextToken()));

		// output (mix, 1)
		output.collect(mix, ONE);

	}

}

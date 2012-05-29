package org.manea.vlad.generalminimumcomputer;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

/**
 * is the general minimum compute map class
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
class GeneralMinimumComputerMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, BooleanWritable, IntWritable> {

	// constant true
	static final BooleanWritable TRUE = new BooleanWritable(true);

	/**
	 * maps (mix, count) to (TRUE, count)
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
			OutputCollector<BooleanWritable, IntWritable> output,
			Reporter reporter) throws IOException {

		// get line
		String line = value.toString();

		// create string token generator
		StringTokenizer tokenizer = new StringTokenizer(line);

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// lose the message token
		tokenizer.nextToken();

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// get the count token
		IntWritable count = new IntWritable(Integer.parseInt(tokenizer
				.nextToken()));

		// output (TRUE, count)
		output.collect(TRUE, count);

	}

}

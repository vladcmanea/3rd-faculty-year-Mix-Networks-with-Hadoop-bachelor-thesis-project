package org.manea.vlad.probablemixassigner;

import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

/**
 * is the probable mix assigner map class
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
class ProbableMixAssignerMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, IntWritable, Text> {

	/**
	 * maps (word) to (mix, word) where mix is a random in {1, 2, ..., n-1, n}
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
			OutputCollector<IntWritable, Text> output, Reporter reporter)
			throws IOException {

		// get line
		String line = value.toString();
		
		// create a random generator
		Random randomGenerator = new Random();

		// create string token generator
		StringTokenizer tokenizer = new StringTokenizer(line);

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// lose the mix token
		tokenizer.nextToken();

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// get the word token
		Text word = new Text(tokenizer.nextToken());

		// output (mix, word) where mix in {0, 1, ..., n-2, n-1}
		output.collect(
				new IntWritable(randomGenerator
						.nextInt(ProbableMixAssignerEntryPoint.getModerator()
								.getNumberOfMixes())), word);

	}

}

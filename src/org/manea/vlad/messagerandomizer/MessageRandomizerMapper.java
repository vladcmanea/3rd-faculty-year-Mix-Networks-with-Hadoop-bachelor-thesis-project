package org.manea.vlad.messagerandomizer;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

/**
 * is the message randomize map class
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
class MessageRandomizerMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, IntWritable, Text> {

	/**
	 * maps (mix, word) to (mix, word)
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

		// create string token generator
		StringTokenizer tokenizer = new StringTokenizer(line);

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// get the mix token
		IntWritable mix = new IntWritable(Integer.parseInt(tokenizer
				.nextToken()));

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// get the word token
		Text word = new Text(tokenizer.nextToken());

		// output (mix, word)
		output.collect(mix, word);

	}

}

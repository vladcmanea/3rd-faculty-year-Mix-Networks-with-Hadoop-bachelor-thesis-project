package org.manea.vlad.messagefilter;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

/**
 * is the current input message assign map class
 * 
 * @author administrator
 */
@SuppressWarnings("deprecation")
class MessageFilterPassMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, IntWritable, Text> {

	// constant max
	static final IntWritable MAX = new IntWritable(Integer.MAX_VALUE);

	/**
	 * maps (mix, word) to (mix, word) if mix is not MAX<br />
	 * discards other (mix, word) pairs
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
		int mix = Integer.parseInt(tokenizer.nextToken());

		// test for message token existence
		if (!tokenizer.hasMoreTokens())
			throw new IOException();

		// get the word token
		Text word = new Text(tokenizer.nextToken());

		// output (word, mix)
		if (mix != Integer.MAX_VALUE)
			output.collect(new IntWritable(mix), word);

	}

}

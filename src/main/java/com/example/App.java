// package com.example;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class App {
    
    public static class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, Text> {
        private final Text word = new Text();
        private final Text docPosition = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = value.toString().split("\\s+");
            String docName = ((FileSplit) context.getInputSplit()).getPath().getName();

            for (int i = 0; i < tokens.length; i++) {
                String currentWord = tokens[i];
                docPosition.set(docName + "-index-" + (i + 1)); // Document name with position
                word.set(currentWord);
                context.write(word, docPosition); // Emit (word, docName-index-position)
            }
        }
    }

    public static class InvertedIndexReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Map<String, StringJoiner> docIndicesMap = new HashMap<>();
            
            for (Text value : values) {
                String[] parts = value.toString().split("-index-");
                String docName = parts[0];
                String index = parts[1];

                // Create or get the joiner for the specific document
                StringJoiner joiner = docIndicesMap.getOrDefault(docName, new StringJoiner(","));
                joiner.add(index); // Add index to the joiner
                docIndicesMap.put(docName, joiner); // Update the map with the joiner
            }

            // Build the output string
            StringJoiner outputJoiner = new StringJoiner(",");
            for (Map.Entry<String, StringJoiner> entry : docIndicesMap.entrySet()) {
                String docName = entry.getKey();
                String indices = entry.getValue().toString();
                outputJoiner.add(docName + "-index[" + indices + "]"); // Format as required
            }

            context.write(key, new Text(outputJoiner.toString())); // Emit (word, formatted output)
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: InvertedIndex <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Inverted Index");
        job.setJarByClass(App.class);
        job.setMapperClass(InvertedIndexMapper.class);
        job.setReducerClass(InvertedIndexReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

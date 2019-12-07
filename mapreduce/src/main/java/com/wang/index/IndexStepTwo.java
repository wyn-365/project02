package com.wang.index;

import org.apache.avro.io.parsing.Symbol;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class IndexStepTwo {

    public static class IndexStepTwoMapper extends Mapper<LongWritable, Text,Text, Text>{

        //产生《hello--文件名--1》
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] split = value.toString().split("-");

            context.write(new Text(split[0]),new Text(split[1].replaceAll("\t","-->")));
        }
    }


    public static class   IndexStepTwoReducer extends Reducer<Text,Text,Text,Text>{

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            //非线程安全，多线程   比较快 StringBuffer
            StringBuilder sb = new StringBuilder();

            for (Text value : values){
                sb.append(value.toString()).append("\t");
            }

            context.write(key,new Text(sb.toString()));
        }
    }

    //主方法
    public static void main(String[] args) throws Exception{

        //默认值加载core-defaults.xml   core-site.xml
        Configuration conf = new Configuration();

        //1.获取job对象
        Job job = Job.getInstance(conf);

        //2.设置jar存储的位置
        job.setJarByClass(IndexStepTwo.class);

        //3.关联Mapper和Reducer类
        job.setMapperClass(IndexStepTwoMapper.class);
        job.setReducerClass(IndexStepTwoReducer.class);

        //4.设置Mapper阶段的输出数据key和value数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);


        //5.设置最终数据的输出key value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //6.设置输入路径和输出路径
        args = new String[]{"e:/input","e:/output"};
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //7.提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1 );
    }
}

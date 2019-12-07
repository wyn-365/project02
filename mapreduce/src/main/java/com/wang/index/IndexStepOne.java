package com.wang.index;

import com.wang.page.JobSubmit;
import com.wang.page.PageMapper;
import com.wang.page.PageReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class IndexStepOne {

    public static class IndexStepOneMapper extends Mapper<LongWritable, Text,Text, IntWritable>{

        //产生《hello--文件名--1》
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //输入切片，获取正在处理文件的信息
            FileSplit inputSplit = (FileSplit)context.getInputSplit();
            String fileName = inputSplit.getPath().getName();

            String[] words = value.toString().split("");
            for (String w : words){
                //将  单词--文件名  作为key   value = 1
                context.write(new Text(w + "-" + fileName),new IntWritable(1));
            }

        }
    }


    public static class   IndexStepOneReducer extends Reducer<Text,IntWritable,Text,IntWritable>{

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int count = 0;
            for (IntWritable value : values){
                count += value.get();
            }

            context.write(key,new IntWritable(count));
        }
    }

    //主方法
    public static void main(String[] args) throws Exception{

        //默认值加载core-defaults.xml   core-site.xml
        Configuration conf = new Configuration();

        //1.获取job对象
        Job job = Job.getInstance(conf);

        //2.设置jar存储的位置
        job.setJarByClass(IndexStepOne.class);

        //3.关联Mapper和Reducer类
        job.setMapperClass(IndexStepOneMapper.class);
        job.setReducerClass(IndexStepOneReducer.class);

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

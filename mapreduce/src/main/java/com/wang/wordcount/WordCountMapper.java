package com.wang.wordcount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//map阶段
//输入数据key 输入数据的value
//输出数据的类型  输出数据
public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {

    Text k = new Text();
    IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //1.获取一行数据
        String line = value.toString();

        //2.切分单词
        String[] words = line.split("");

        //3.循环输出
        for(String word:words){

            k.set(word);
            context.write(k,v);
        }
    }
}

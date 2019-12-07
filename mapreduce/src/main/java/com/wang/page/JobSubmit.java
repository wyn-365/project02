package com.wang.page;

import com.wang.flowsum.FlowBean;
import com.wang.flowsum.FlowCountMapper;
import com.wang.flowsum.FlowCountReducer;
import com.wang.flowsum.FlowSumDriver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.util.Properties;

public class JobSubmit {

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        //conf.setInt("top.n",3);
        //读取配置文件中的值
        //Properties props = new Properties();
        //加载配置文件
        //props.load(JobSubmit.class.getClassLoader().getResourceAsStream("topn.prpperties"));
        //读取配置文件的值
        //conf.setInt("topn",Integer.parseInt(props.getProperty("topn")));

        conf.addResource("xx-oo.xml");

        //1.获取job对象
        Job job = Job.getInstance(conf);

        //2.设置jar存储的位置
        job.setJarByClass(JobSubmit.class);

        //3.关联Mapper和Reducer类
        job.setMapperClass(PageMapper.class);
        job.setReducerClass(PageReducer.class);

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

package com.wang.flowsum;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowSumDriver {

    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();

        //1.获取job对象
        Job job = Job.getInstance(conf);

        //2.设置jar存储的位置
        job.setJarByClass(FlowSumDriver.class);

        //3.关联Mapper和Reducer类
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);

        //4.设置Mapper阶段的输出数据key和value数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);


        //5.设置最终数据的输出key value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //6.设置输入路径和输出路径
        args = new String[]{"e:/input","e:/output"};
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //7.提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1 );
    }
}

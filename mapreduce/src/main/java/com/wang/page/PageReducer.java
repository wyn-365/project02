package com.wang.page;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PageReducer extends Reducer<Text, IntWritable,Text,IntWritable> {

    TreeMap<Page,Object> treeMap = new TreeMap<Page, Object>();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value:values){
            count += value.get();
        }

        Page page = new Page();
        page.set(key.toString(),count);
        treeMap.put(page,null);
    }

    @Override
    protected void cleanup(Reducer<Text,IntWritable,Text,IntWritable>.Context context) throws IOException, InterruptedException {
        //次数最高的五条
        Configuration conf = new Configuration();
        int topn = conf.getInt("top.n", 5);

        Set<Map.Entry<Page, Object>> entrySet = treeMap.entrySet();
        int i = 0;

        for (Map.Entry<Page,Object>  entry:entrySet){
            //context.write(entry.getKey().getPage(),new IntWritable(entry.getKey().getCount()));
            i++;
            if (i==topn){
                return;
            }

        }
    }
}

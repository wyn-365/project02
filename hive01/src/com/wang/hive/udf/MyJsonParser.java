package com.wang.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class MyJsonParser extends UDF {

    //重载父类中一个方法
    public String  evaluate(String json,int index){

        //{"movie":"1193","rate":"5","timeStamp":"978300760","uid":"1"}
        //没有嵌套 切割一下就好

        String[] fields = json.split("\"");

        return fields[2*index + 1];
    }
}

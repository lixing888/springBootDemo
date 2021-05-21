package com.springboot.demo.util.Hadoop;


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Copyright (C), 2018-2021
 * FileName: MyWordCountMapper
 * Author:   李兴
 * Date:     2021/5/21 10:54
 * Description: Mapper阶段的代码
 * 自定义MyWordCountMapper 类继承Mapper，重写map方法来实现Mapper阶段的数据获取、按自己的规则分词、以及输出到reducer做处理
 */

public class MyWordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key1, Text value1, Context context)
            throws IOException, InterruptedException {
        // TODO Auto-generated method stub
//      super.map(key1, value1, context);
        /*
         * context 表示Mapper的上下文
         * 上文：HDFS
         * 下文：Mapper
         */
        //数据： I love Beijing
        String data1 = value1.toString();

        //分词
        String[] words = data1.split(" ");
        for (String w : words) {
            context.write(new Text(w), new IntWritable(1));
        }
    }

}

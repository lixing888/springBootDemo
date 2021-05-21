package com.springboot.demo.util.Hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Copyright (C), 2018-2021
 * FileName: MyWordCountReducer
 * Author:   李兴
 * Date:     2021/5/21 10:55
 * Description: Reducer阶段的代码
 * WordCountReducer 继承 Reducer 重写reduce方法来实现Reduce阶段数据的求和处理，最终输出。
 */
public class MyWordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text k3, Iterable<IntWritable> v3,
                          Context context) throws IOException, InterruptedException {
        // TODO Auto-generated method stub
//      super.reduce(arg0, arg1, arg2);
        int total = 0;

        for (IntWritable v : v3) {
            total += v.get();
        }
        context.write(k3, new IntWritable(total));
    }

}

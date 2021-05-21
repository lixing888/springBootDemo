package com.springboot.demo.util.Hadoop;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Copyright (C), 2018-2021
 * FileName: MapReduce
 * Author:   李兴
 * Date:     2021/5/21 11:03
 * Description: MapReduce任务代码
 */
public class MapReduce {

    public static class Map extends Mapper<LongWritable, Text, IntWritable, Text> {

        //中心集合
        ArrayList<ArrayList<Double>> centers = null;
        //用k个中心
        int k = 0;

        //读取中心
        protected void setup(Context context) throws IOException,
                InterruptedException {
            centers = Utils.getCentersFromHDFS(context.getConfiguration().get("centersPath"), false);
            k = centers.size();//获取中心点个数
        }


        /**
         * 1.每次读取一条要分类的条记录与中心做对比，归类到对应的中心
         * 2.以中心ID为key，中心包含的记录为value输出(例如： 1 0.2---->1为聚类中心的ID，0.2为靠近聚类中心的某个值)
         */
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            //读取一行数据
            ArrayList<Double> fileds = Utils.textToArray(value);
            int sizeOfFileds = fileds.size();

            double minDistance = 99999999;
            int centerIndex = 0;

            //依次取出k个中心点与当前读取的记录做计算
            for (int i = 0; i < k; i++) {
                double currentDistance = 0;
                for (int j = 1; j < sizeOfFileds; j++) {
                    double centerPoint = Math.abs(centers.get(i).get(j));
                    double filed = Math.abs(fileds.get(j));
                    currentDistance += Math.pow((centerPoint - filed) / (centerPoint + filed), 2);//这里的距离算法简化处理
                }
                //循环找出距离该记录最接近的中心点的ID
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    centerIndex = i;
                }
            }
            //以中心点为Key 将记录原样输出
            context.write(new IntWritable(centerIndex + 1), value);
        }

    }

    //利用reduce的归并功能以中心为Key将记录归并到一起
    public static class Reduce extends Reducer<IntWritable, Text, Text, Text> {

        /**
         * 1.Key为聚类中心的ID value为该中心的记录集合
         * 2.计数所有记录元素的平均值，求出新的中心
         */
        protected void reduce(IntWritable key, Iterable<Text> value, Context context)
                throws IOException, InterruptedException {
            ArrayList<ArrayList<Double>> filedsList = new ArrayList<ArrayList<Double>>();

            //依次读取记录集，每行为一个ArrayList<Double>
            for (Iterator<Text> it = value.iterator(); it.hasNext(); ) {
                ArrayList<Double> tempList = Utils.textToArray(it.next());
                filedsList.add(tempList);
            }

            //计算新的中心
            //每行的元素个数
            int filedSize = filedsList.get(0).size();
            double[] avg = new double[filedSize];
            for (int i = 1; i < filedSize; i++) {
                //求没列的平均值
                double sum = 0;
                int size = filedsList.size();
                for (int j = 0; j < size; j++) {
                    sum += filedsList.get(j).get(i);
                }
                avg[i] = sum / size;
            }
            context.write(new Text(""), new Text(Arrays.toString(avg).replace("[", "").replace("]", "")));
        }

    }

    @SuppressWarnings("deprecation")
    public static void run(String centerPath, String dataPath, String newCenterPath, boolean runReduce) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        conf.set("centersPath", centerPath);

        Job job = new Job(conf, "mykmeans");
        job.setJarByClass(MapReduce.class);

        job.setMapperClass(Map.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);

        if (runReduce) {
            //最后依次输出不需要reduce
            job.setReducerClass(Reduce.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
        }

        FileInputFormat.addInputPath(job, new Path(dataPath));

        FileOutputFormat.setOutputPath(job, new Path(newCenterPath));

        System.out.println(job.waitForCompletion(true));
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
        String centerPath = "hdfs://sparkproject1:9000/input/centers.txt";//存放中心点坐标值
        String dataPath = "hdfs://sparkproject1:9000/input/wine.txt";//存放待处理数据
        String newCenterPath = "hdfs://sparkproject1:9000/out/kmean";//结果输出目录
        int count = 0;

        while (true) {
            run(centerPath, dataPath, newCenterPath, true);
            System.out.println(" 第 " + ++count + " 次计算 ");
            if (Utils.compareCenters(centerPath, newCenterPath)) {
                run(centerPath, dataPath, newCenterPath, false);
                break;
            }
        }
    }
}

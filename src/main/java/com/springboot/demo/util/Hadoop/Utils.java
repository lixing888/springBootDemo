package com.springboot.demo.util.Hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;

/**
 * Copyright (C), 2018-2021
 * FileName: Utils
 * Author:   李兴
 * Date:     2021/5/21 11:06
 * Description: MapReduce辅助工具类
 */
public class Utils {
    //读取中心文件的数据
    public static ArrayList<ArrayList<Double>> getCentersFromHDFS(String centersPath, boolean isDirectory) throws IOException {

        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();

        Path path = new Path(centersPath);

        Configuration conf = new Configuration();

        FileSystem fileSystem = path.getFileSystem(conf);

        if (isDirectory) {
            FileStatus[] listFile = fileSystem.listStatus(path);
            for (int i = 0; i < listFile.length; i++) {
                result.addAll(getCentersFromHDFS(listFile[i].getPath().toString(), false));
            }
            return result;
        }

        FSDataInputStream fsis = fileSystem.open(path);
        LineReader lineReader = new LineReader(fsis, conf);

        Text line = new Text();

        while (lineReader.readLine(line) > 0) {
            ArrayList<Double> tempList = textToArray(line);
            result.add(tempList);
        }
        lineReader.close();
        return result;
    }

    //删掉文件
    public static void deletePath(String pathStr) throws IOException {
        Configuration conf = new Configuration();
        Path path = new Path(pathStr);
        FileSystem hdfs = path.getFileSystem(conf);
        hdfs.delete(path, true);
    }

    //文本string类型转为数组类型
    public static ArrayList<Double> textToArray(Text text) {
        ArrayList<Double> list = new ArrayList<Double>();
        String[] fileds = text.toString().split(",");
        for (int i = 0; i < fileds.length; i++) {
            list.add(Double.parseDouble(fileds[i]));
        }
        return list;
    }

    //比较新旧中心点的变化情况
    public static boolean compareCenters(String centerPath, String newPath) throws IOException {

        List<ArrayList<Double>> oldCenters = Utils.getCentersFromHDFS(centerPath, false);
        List<ArrayList<Double>> newCenters = Utils.getCentersFromHDFS(newPath, true);

        int size = oldCenters.size();
        int fildSize = oldCenters.get(0).size();
        double distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < fildSize; j++) {
                double t1 = Math.abs(oldCenters.get(i).get(j));
                double t2 = Math.abs(newCenters.get(i).get(j));
                distance += Math.pow((t1 - t2) / (t1 + t2), 2);//根据具体情况选择相应的距离算法，这里简化处理
            }
        }

        if (distance == 0.0) {
            //删掉新的中心文件以便最后依次归类输出
            Utils.deletePath(newPath);
            return true;
        } else {
            //先清空中心文件，将新的中心文件复制到中心文件中，再删掉中心文件

            Configuration conf = new Configuration();
            Path outPath = new Path(centerPath);
            FileSystem fileSystem = outPath.getFileSystem(conf);

            FSDataOutputStream overWrite = fileSystem.create(outPath, true);
            overWrite.writeChars("");
            overWrite.close();


            Path inPath = new Path(newPath);
            FileStatus[] listFiles = fileSystem.listStatus(inPath);
            for (int i = 0; i < listFiles.length; i++) {
                FSDataOutputStream out = fileSystem.create(outPath);
                FSDataInputStream in = fileSystem.open(listFiles[i].getPath());
                IOUtils.copyBytes(in, out, 4096, true);
            }
            //删掉新的中心文件以便第二次任务运行输出
            Utils.deletePath(newPath);
        }

        return false;
    }
}

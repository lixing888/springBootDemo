package com.springboot.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory (e.g. /usr/local/jdk5.0/src): ");
        String directory = in.nextLine();
        System.out.print("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();

        final int FILE_QUEUE_SIZE = 10;// 阻塞队列大小
        final int SEARCH_THREADS = 100;// 关键字搜索线程个数

        // 基于ArrayBlockingQueue的阻塞队列
        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(
                FILE_QUEUE_SIZE);

        //只启动一个线程来搜索目录
        FileEnumerationTask enumerator = new FileEnumerationTask(queue,
                new File(directory));
        new Thread(enumerator).start();

        //启动100个线程用来在文件中搜索指定的关键字
        for (int i = 1; i <= SEARCH_THREADS; i++)
            new Thread(new SearchTask(queue, keyword)).start();
    }
}
class FileEnumerationTask implements Runnable {
    //哑元文件对象，放在阻塞队列最后，用来标示文件已被遍历完
    public static File DUMMY = new File("");

    private BlockingQueue<File> queue;
    private File startingDirectory;

    public FileEnumerationTask(BlockingQueue<File> queue, File startingDirectory) {
        this.queue = queue;
        this.startingDirectory = startingDirectory;
    }

    public void run() {
        try {
            enumerate(startingDirectory);
            queue.put(DUMMY);//执行到这里说明指定的目录下文件已被遍历完
        } catch (InterruptedException e) {
        }
    }

    // 将指定目录下的所有文件以File对象的形式放入阻塞队列中
    public void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory())
                enumerate(file);
            else
                //将元素放入队尾，如果队列满，则阻塞
                queue.put(file);
        }
    }
}
class SearchTask implements Runnable {
    private BlockingQueue<File> queue;
    private String keyword;

    public SearchTask(BlockingQueue<File> queue, String keyword) {
        this.queue = queue;
        this.keyword = keyword;
    }

    public void run() {
        try {
            boolean done = false;
            while (!done) {
                //取出队首元素，如果队列为空，则阻塞
                File file = queue.take();
                if (file == FileEnumerationTask.DUMMY) {
                    //取出来后重新放入，好让其他线程读到它时也很快的结束
                    queue.put(file);
                    done = true;
                } else
                    search(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        }
    }
    public void search(File file) throws IOException {
        Scanner in = new Scanner(new FileInputStream(file));
        int lineNumber = 0;
        while (in.hasNextLine()) {
            lineNumber++;
            String line = in.nextLine();
            if (line.contains(keyword))
                System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber,
                        line);
        }
        in.close();
    }
}

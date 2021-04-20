package com.springboot.demo.esensoft;

import com.esensoft.common.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@Slf4j
public class ApplicationTests {

    @Autowired
    private Task task;

//    @Test
//    public void test() throws Exception {
//        task.doTaskOne();
//        task.doTaskTwo();
//        task.doTaskThree();
//        Thread.currentThread().join();
//    }

    @Test
    public void test1() {
        Future<String> futureResult = null;
        try {
            futureResult = task.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = null;
        try {
            result = futureResult.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void test2() {
//        List<Future<String>> futures = new ArrayList<>();
////        for (int i = 0; i < companyNamejsonArray.size(); i++) {
////            Future<String> future = financialCaseService.addDomList(companyNamejsonArray.getString(i), automaticTask.getTaskName(), taskId, i);
////            //在addDom中用return new AsyncResult<>(phoneNum+"成功");返回信息
////            futures.add(future);
////        }
//
//        for (Future future : futures) {
//            String string = null;
//            try {
//                string = (String) future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            System.out.println(string);
//        }
//    }
}

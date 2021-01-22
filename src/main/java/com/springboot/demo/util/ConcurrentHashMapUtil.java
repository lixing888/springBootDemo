package com.springboot.demo.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * concurrentHashMap 并发场景下写入数据后丢失
 * @author Administrator
 *
 */
public class ConcurrentHashMapUtil {
	public static void main(String[] args) throws Exception{
		while (true) {
			AtomicInteger atomicInteger = new AtomicInteger(0) ;
			ConcurrentHashMap<String, ConcurrentHashMap<String, String>> concurrentHashMapConcurrentHashMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
			Lock lock = new ReentrantLock() ;
			Condition condition = lock.newCondition() ;

			ExecutorService executor = Executors.newCachedThreadPool();
			for (int i = 0; i < 10; i++) {
				executor.execute(new PutThread(concurrentHashMapConcurrentHashMap ,atomicInteger , lock , condition));
			}

			lock.lock();
			while (atomicInteger.get() != 10){
				condition.await();
			}
			lock.unlock();

			System.out.println(concurrentHashMapConcurrentHashMap.get("topic").size());
			if (concurrentHashMapConcurrentHashMap.get("topic").size() != 500) {
				//线程池复用导致key重复了！！！
				System.out.println("not ...");
			}

			concurrentHashMapConcurrentHashMap.clear();

			executor.shutdownNow() ;
		}
	}

	public static void put(String topic , String producerKey , String value , ConcurrentHashMap<String , ConcurrentHashMap<String , String>> concurrentHashMapConcurrentHashMap){
		synchronized (concurrentHashMapConcurrentHashMap) {
			if (concurrentHashMapConcurrentHashMap.containsKey(topic)) {
				concurrentHashMapConcurrentHashMap.get(topic).put(producerKey, value);
			} else {
				concurrentHashMapConcurrentHashMap.put(topic, new ConcurrentHashMap<String, String>());
				concurrentHashMapConcurrentHashMap.get(topic).put(producerKey, value);
			}
		}
	}
}

class PutThread implements Runnable{
	private volatile ConcurrentHashMap<String , ConcurrentHashMap<String , String>> concurrentHashMapConcurrentHashMap ;
	private volatile AtomicInteger atomicInteger ;
	private Lock lock ;
	private Condition condition ;

	public PutThread(ConcurrentHashMap<String, ConcurrentHashMap<String, String>> concurrentHashMapConcurrentHashMap, AtomicInteger atomicInteger, Lock lock, Condition condition) {
		this.concurrentHashMapConcurrentHashMap = concurrentHashMapConcurrentHashMap;
		this.atomicInteger = atomicInteger;
		this.lock = lock;
		this.condition = condition;
	}

	@Override
	public void run() {
		for(int i=0 ; i<50 ; i++){
			String id = Thread.currentThread().getName()+i ;
			//System.out.println("id="+id);
			ConcurrentHashMapUtil.put("topic" , id , "li" , concurrentHashMapConcurrentHashMap);
		}
		atomicInteger.addAndGet(1) ;

		lock.lock();
		condition.signal();
		lock.unlock();
	}
}

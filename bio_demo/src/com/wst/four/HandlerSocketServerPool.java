package com.wst.four;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerSocketServerPool {
    //1. 创建一个线程池的成员变量，用于存储一个线程池对象
    private ExecutorService executorService;

    /**
     * 2.在创建这个类的对象的时候，就需要初始化线程池对象
     *     public ThreadPoolExecutor(int corePoolSize,
     *                               int maximumPoolSize,
     *                               long keepAliveTime,
     *                               TimeUnit unit,
     *                               BlockingQueue<Runnable> workQueue)
     */
    public HandlerSocketServerPool(int maxThreadNumber, int queueSize){
        executorService = new ThreadPoolExecutor(3, maxThreadNumber, 120,
                                                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));

    }

    /**
     * 3.提供一个方法来提交任务给线程池的任务队列来暂存，等着线程池处理
     */
    public void execute(Runnable target){
        executorService.execute(target);
    }


}

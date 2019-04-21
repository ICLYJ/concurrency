package com.mmall.concurrency.atomic;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：lyj
 * @email: : iclyj@iclyj.cn
 * @date ：2019/4/21 21:40
 *
 * 线程安全测试
 */
@Slf4j
@ThreadSafe
public class AtomicExample1 {

    // 请求总数
    public static int clinetTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clinetTotal);
        for (int i = 0; i < clinetTotal; i++){
            executorService.execute(() ->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count.get());
    }

    private static void add(){
        //先增加再获取值
        // getAndAddInt->getAndAddInt->compareAndSwapInt
        /**
         * public final int getAndAddInt(Object var1, long var2, int var4) {
         *         int var5;
         *         do {
         *             var5 = this.getIntVolatile(var1, var2);
         *             //当前的值等于期望的值才执行（var5 + var4）操作
         *             //CAS操作（当前对象的值等于底层的值才执行对应的操作）
         *         } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
         *
         *         return var5;
         *     }
         */
        count.incrementAndGet();
        //先获取值再增加
//        count.getAndIncrement();
    }


}

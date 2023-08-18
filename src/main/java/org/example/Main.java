package org.example;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static org.example.UtilClass.performTask;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 3;
        ExecutorService singleQueueExecutor = Executors.newFixedThreadPool(numThreads);
        ExecutorService multiQueueExecutor = new ThreadPoolExecutor(
                numThreads,
                numThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new SeparateQueueThreadFactory()
        );

        Boolean isMultiQueue = Boolean.TRUE;
        ExecutorService executorUsed = isMultiQueue?multiQueueExecutor:singleQueueExecutor;

        Integer start = 1;
        executorUsed.submit(() -> {
            performTask(start, executorUsed);
        });
        sleep(5000);
        executorUsed.shutdown();
    }

    private static class SeparateQueueThreadFactory implements java.util.concurrent.ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Thread with Separate Queue");
        }
    }
}
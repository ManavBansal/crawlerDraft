package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import static java.lang.Thread.currentThread;

public class UtilClass {
    public static Map<Integer, Boolean> visited = new ConcurrentHashMap<>();
    public static void performTask(Integer currDirectory, ExecutorService executor){
        for(int i = 2; i < 10; i += 2){
            Integer newTask = (i * currDirectory) % 100;
            if(!visited.containsKey(newTask)){
                executor.submit(
                        () -> performTask(newTask,executor)
                );
                System.out.println(newTask.toString() + ": By thread " + currentThread().getId());
                visited.put(newTask,Boolean.TRUE);

            }
        }
    }
}

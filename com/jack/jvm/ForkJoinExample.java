package com.jack.jvm;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinExample extends RecursiveTask<Integer> {
    private int start;
    private int end;
    private static final int THRESHOLD = 10;

    public ForkJoinExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (end - start <= THRESHOLD) {
            // 基本情况，直接计算
            for (int i = start; i <= end; i++) {
                result += i;
            }
        } else {
            // 分解任务
            int middle = (start + end) / 2;
            ForkJoinExample subTask1 = new ForkJoinExample(start, middle);
            ForkJoinExample subTask2 = new ForkJoinExample(middle + 1, end);
            // 执行子任务
            subTask1.fork();
            subTask2.fork();
            // 合并结果
            result = subTask1.join() + subTask2.join();
        }
        return result;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinExample task = new ForkJoinExample(1, 100);
        Integer result = pool.invoke(task);
        System.out.println("Result: " + result);
    }
}
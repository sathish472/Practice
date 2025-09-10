package com.test.pract.vthreds;

public class ThreadComparison {

    public static void main(String[] args) throws InterruptedException {

        int taskCount = 10_000;

        // -------- Platform Threads --------
        long startPlatform = System.currentTimeMillis();
        Thread[] platformThreads = new Thread[taskCount];

        for (int i = 0; i < taskCount; i++) {
            platformThreads[i] = new Thread(() -> {
                try {
                    Thread.sleep(100); // Simulate I/O work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            platformThreads[i].start();
        }

        for (Thread t : platformThreads) {
            t.join();
        }

        long endPlatform = System.currentTimeMillis();
        System.out.println("Platform Threads completed in: " + (endPlatform - startPlatform) + " ms");


        // -------- Virtual Threads --------
        long startVirtual = System.currentTimeMillis();
        Thread[] virtualThreads = new Thread[taskCount];

        for (int i = 0; i < taskCount; i++) {
            virtualThreads[i] = Thread.ofVirtual().unstarted(() -> {
                try {
                    Thread.sleep(100); // Simulate I/O work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            virtualThreads[i].start();
        }

        for (Thread t : virtualThreads) {
            t.join();
        }

        long endVirtual = System.currentTimeMillis();
        System.out.println("Virtual Threads completed in: " + (endVirtual - startVirtual) + " ms");
    }
}

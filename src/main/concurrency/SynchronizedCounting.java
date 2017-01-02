package main.concurrency;

import java.util.stream.IntStream;

public class SynchronizedCounting {

    public static void main(String[] args) throws InterruptedException {
        class Counter {
            private int count = 0;

            public synchronized void increment() {
                ++count;
            }

            public synchronized int getCount() {
                return count;
            }
        }

        final Counter counter = new Counter();

        class CountingThread extends Thread {

            @Override
            public void run() {
                IntStream.range(0, 10000)
                        .forEach(value -> counter.increment());
            }
        }

        CountingThread t1 = new CountingThread();
        CountingThread t2 = new CountingThread();
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(counter.getCount());
    }
}

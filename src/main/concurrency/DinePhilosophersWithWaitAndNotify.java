package main.concurrency;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DinePhilosophersWithWaitAndNotify {

    public static void main(String[] args) {
        final List<ChopstickWithWaitAndNotify> chopstickWithWaitAndNotifies = IntStream.range(0, 5)
                .mapToObj(value -> new ChopstickWithWaitAndNotify("C:" + value))
                .collect(Collectors.toList());


        PhilosopherWithAndNotify[] philosopherWithAndNotifies = new PhilosopherWithAndNotify[5];

        philosopherWithAndNotifies[0] = new PhilosopherWithAndNotify(chopstickWithWaitAndNotifies.get(0), chopstickWithWaitAndNotifies.get(1));
        philosopherWithAndNotifies[1] = new PhilosopherWithAndNotify(chopstickWithWaitAndNotifies.get(1), chopstickWithWaitAndNotifies.get(2));
        philosopherWithAndNotifies[2] = new PhilosopherWithAndNotify(chopstickWithWaitAndNotifies.get(2), chopstickWithWaitAndNotifies.get(3));
        philosopherWithAndNotifies[3] = new PhilosopherWithAndNotify(chopstickWithWaitAndNotifies.get(3), chopstickWithWaitAndNotifies.get(4));
        philosopherWithAndNotifies[4] = new PhilosopherWithAndNotify(chopstickWithWaitAndNotifies.get(4), chopstickWithWaitAndNotifies.get(0));

        for (PhilosopherWithAndNotify philosopherWithAndNotify : philosopherWithAndNotifies) {
            philosopherWithAndNotify.start();
        }
    }


}

class PhilosopherWithAndNotify extends Thread {
    private ChopstickWithWaitAndNotify left, right;
    private Random random;

    public PhilosopherWithAndNotify(ChopstickWithWaitAndNotify left, ChopstickWithWaitAndNotify right) {
        this.left = left;
        this.right = right;
        this.random = new Random();
    }

    public void eat(){
        left.takeUp();
        right.takeUp();
        System.out.println(Thread.currentThread() + "eating");
    }

    public void think(){
        left.putDown();
        right.putDown();
        System.out.println(Thread.currentThread() + "thinking");
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    eat();
                    sleep(100);
                    think();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class ChopstickWithWaitAndNotify {
    public String name;
    public boolean available = true;



    public ChopstickWithWaitAndNotify(String name) {
        this.name = name;
        available = true;
    }

    public synchronized void takeUp() {
        while (!available){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false;
    }

    public synchronized void putDown(){
        available = true;
        notify();
    }
}
package main.concurrency;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dine {

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


class Philosopher extends Thread {
    private ChopstickWithWaitAndNotify left, right;
    private Random random;

    public Philosopher(ChopstickWithWaitAndNotify left, ChopstickWithWaitAndNotify right) {
        this.left = left;
        this.right = right;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(10));
                synchronized (left) {
                    synchronized (right) {
                        Thread.sleep(random.nextInt(1000));
                        System.out.println("Eat");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class Chopstick {
    public String name;

    public Chopstick(String name) {
        this.name = name;
    }
}

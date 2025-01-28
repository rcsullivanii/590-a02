package main.java;

// import Lock to control access to shared recourses (forks)
// in our table class, we will create Philosophers with reentrant locks
import java.util.concurrent.locks.Lock;

public class Philosopher extends Thread {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;

    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override 
    public void run() {
        try {
            // ideally we want to infinitely loop here until the thread is interrupted
            while (true) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep((int) Math.random() * 3000);
    }

    private void eat() throws InterruptedException {
        // Lock both resources and log the action
        leftFork.lock();
        System.out.println("Philosopher " + id + " acquired left fork");
        rightFork.lock();
        System.out.println("Philosopher " + id + " acquired right fork");

        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep((int) Math.random() * 3000);

        leftFork.unlock();
        System.out.println("Philosopher " + id + " released left fork");
        rightFork.unlock();
        System.out.println("Philosopher " + id + " released right fork");
    }

}
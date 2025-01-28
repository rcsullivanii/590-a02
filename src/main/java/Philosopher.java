package main.java;

import java.util.HashMap;
// import Lock to control access to shared recourses (forks)
// in our table class, we will create Philosophers with reentrant locks
import java.util.concurrent.locks.Lock;

public class Philosopher extends Thread {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;
    private HashMap<Integer, Integer> eatingCount = new HashMap<>();

    public Philosopher(int id, Lock leftFork, Lock rightFork, HashMap<Integer, Integer> eatingCount) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        // Pass a shared map in Table so we may record each time a Philosopher eats
        this.eatingCount = eatingCount;
    }

    @Override 
    public void run() {
        try {
            // ideally we want to infinitely loop here until the thread is interrupted
            while (!Thread.currentThread().isInterrupted()) {
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
        Thread.sleep((int) (Math.random() * 3000));
    }

    private void eat() throws InterruptedException {
        // use try and finally to make sure that lock is released no matter what
        leftFork.lock();
        try {
            System.out.println("Philosopher " + id + " acquired left fork");
            rightFork.lock();
            try {
                System.out.println("Philosopher " + id + " acquired right fork");
                System.out.println("Philosopher " + id + " is eating");
                
                synchronized (eatingCount) {
                    eatingCount.put(id, eatingCount.getOrDefault(id, 0) + 1);
                }
                
                Thread.sleep((int) (Math.random() * 3000));
            } finally {
                rightFork.unlock();
                System.out.println("Philosopher " + id + " released right fork");
            }
        } finally {
            leftFork.unlock();
            System.out.println("Philosopher " + id + " released left fork");
        }
    }
}
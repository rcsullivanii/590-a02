package main.java;

import java.util.HashMap;
import java.util.Map;
// We import Reentrant lock to provide manual locking /  more control over resources
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    // Change this value during testing to expand / contract the table size
    private final static int NUM_PHILOSOPHERS = 5;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];

        // Initializing using reentrant locks allows us to manually lock / unlock 
        // This gives us better control than just synchronized 
        ReentrantLock[] forks = new ReentrantLock[NUM_PHILOSOPHERS];

        HashMap<Integer, Integer> eatingCount = new HashMap<>();

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            // set fair paramter to true to prevent starvation (one philosopher continually being denied forks)
            forks[i] = new ReentrantLock(true);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            ReentrantLock leftFork = forks[i];
            ReentrantLock rightFork = forks[(i + 1) % NUM_PHILOSOPHERS];

            // By ensuring that the last philosopher picks up his right fork first, we can prevent potential deadlock
            if (i == NUM_PHILOSOPHERS - 1) {
                philosophers[i] = new Philosopher(i, rightFork, leftFork, eatingCount);
            } else {
                philosophers[i] = new Philosopher(i, leftFork, rightFork, eatingCount);
            }

            // This line will allocate a new thread for each philosopher (since Philosopher extends Thread and we override the run method)
            philosophers[i].start();
        }

        try {
            // Run for 5 seconds
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Close all threads
        for (Philosopher philosopher : philosophers) {
            philosopher.interrupt();
        }

        // join to wait for all threads to close
        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Print eating count 
        System.out.println("Simulation Complete | Eating Summary");
        synchronized(eatingCount) {
            for (Map.Entry<Integer, Integer> entry : eatingCount.entrySet()) {
                System.out.println("Philosopher " + entry.getKey() + " ate " + entry.getValue() + " times");
            }
        }
    }
}
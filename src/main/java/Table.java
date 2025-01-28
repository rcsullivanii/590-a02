package main.java;

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

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            ReentrantLock leftFork = forks[i];
            ReentrantLock rightFork = forks[(i + 1) % NUM_PHILOSOPHERS];

            // By ensuring that the last philosopher picks up his right fork first, we can prevent potential deadlock
            if (i == NUM_PHILOSOPHERS - 1) {
                philosophers[i] = new Philosopher(i, rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher(i, leftFork, rightFork);
            }

            // This line will allocate a new thread for each philosopher (since Philosopher extends Thread and we override the run method)
            philosophers[i].start();
        }

        try {
            // Run for 10 seconds
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Close all threads
        for (Philosopher philosopher : philosophers) {
            philosopher.interrupt();
        }
    }
}
# Assignment 2: Java Threads

## Team Members

Robert Sullivan and Cole Vita

## Project Description

Using Java, and threads in Java, program a solution to the *Dining Philosophers problem*, a classic problem in concurrent computation. Use judicious output to show what is going on in your simulation as it executes. Make sure your solution avoids deadlocks, starvations, and race conditions.

In your readme file for the zipfile, explain your design rationale. What features and structures are you using to represent the philosophers? To represent the table, the forks, the spaghetti? To represent eating phase of a philosophers "life"... the thinking phase?

What does your algorithm do to help prevent deadlocks and starvation? Are deadlocks and/or starvations still possible (and just improbable)?
Dining Philosophers Problem

## Notes on Project Description

Before crafting a viable solution, let us first define important terms.
- Deadlock: A deadlock occurs when there are 2+ threads waiting for resources that the other threads are holding. With deadlocks, no progress can be made as the waiting continues indefinitely.
- Starvation: Starvation occurs when a thread is continually denied access to resources. Typically due to lock priorities or bad scheduling.
- Race Conditions: There is a race condition when 2+ threads access shared data or resources simultaneously. With race conditions, the final output is unknown as it depends on the order of execution.

## Design Rationale

To start, we created two classes. First, we created the Philosopher class, which has three fields: int id (assigned 0 -> N - 1), Lock leftFork, and Lock rightFork. The constructor sets these fields. We chose to import the Lock interface, as opposed to a specific type of Lock directly, since it could give us more flexibility if we decided to change the type of lock in our Table class. Using locks to solve this problem gives us the ability to manually lock and unlock access to the shared recourses (forks). Since this control is necessary to solve the problem, we were able to discard the idea of using the less flexible synchronized. 

Our Philosopher class also extends the Thread class - enabling us to override the run() method to create new threads. Within run(), we call two private methods for thinking and eating until eventually the thread is interrupted (or, if an exception is thrown, it is caught and outputted). Thinking and eating both wait a random amount of time no greater than 3 seconds. Eating manually locks and unlocks access to each fork.

As discussed in class, even with the locks in place, we can observe a deadlock if all philosophers grab their left fork at the same time (as none of their right forks would be available). Keeping this in mind, while designing the Table class, we ensured that the last philosopher (with id of N - 1) picks up his right fork first. This guarantees that no deadlock can ever be reached since even if each philosopher becomes hungry at the same time, at least one philosopher will be able to hold access to both forks and, once he releases them, others will be able to eat. That is, there is never a situation with this change where every philosopher has only one fork and cannot gain access to the other. 

Lastly, to solve the issue of starvation, or a situation where one philosopher keeps trying to pick up a fork and eat but is continually blocked, we needed to modify our reeentrant lock by setting the fairness parameter to true. By intitializing as "new ReentrantLock(true)", then, under contention for the fork, the lock will favor giving access to the longest-waiting thread / philosopher.

In our Table class, we run the simulation logging each time a philosopher thinks, picks up or puts down a fork, and eats over 10 seconds.

## Resources

https://www.geeksforgeeks.org/reentrant-lock-java/

https://www.geeksforgeeks.org/java-program-to-create-a-thread/

https://docs.oracle.com/cd/E17802_01/j2se/j2se/1.5.0/jcp/beta1/apidiffs/java/util/concurrent/locks/ReentrantLock.html

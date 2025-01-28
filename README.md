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

How will we implement project? How does the algorithm prevent deadlocks and starvation? Are they still technically possible?

## Resources

https://www.geeksforgeeks.org/reentrant-lock-java/
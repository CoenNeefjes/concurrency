package nl.saxion.concurrency.ballbarrier;

import java.util.concurrent.Semaphore;

/**
 * Created by coen on 21-9-2018.
 */
public class Barrier {

    private static final int NUM_BALLS = 4;

    private Semaphore semaphore = new Semaphore(0);
    private Semaphore mutex =new Semaphore(1);

    private int sleepThreads = 0;

    public void acquire(){

        System.out.println("Available: " + semaphore.availablePermits());
        System.out.println("SleepThreads: " + sleepThreads);
        try {
            mutex.acquire();
            if (sleepThreads < NUM_BALLS - 1){
                sleepThreads++;
                mutex.release();
                semaphore.acquire();
            } else {
                sleepThreads = 0;
                mutex.release();
                semaphore.release(NUM_BALLS - 1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

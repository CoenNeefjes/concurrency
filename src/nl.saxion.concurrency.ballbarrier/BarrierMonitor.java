package nl.saxion.concurrency.ballbarrier;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by coen on 21-9-2018.
 */
public class BarrierMonitor {

    private ReentrantLock lock = new ReentrantLock();
    private Condition allOnLine = lock.newCondition();

    private final int NUM_BALLS = 4;

    private int sleepThreads = 0;
    private boolean wait = true;

    public void acquire(){
        try {
            lock.lock();
            sleepThreads++;

            if (sleepThreads == NUM_BALLS){
                wait = false;
            }

            while(wait){
                allOnLine.await();
            }

            sleepThreads = 0;
            allOnLine.signalAll();
            lock.unlock();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

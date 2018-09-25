package nl.saxion.concurrency.ballbarrier.opdracht;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class Club {

    public static final int MAX_NR_OF_ENTRIES = 20;

    private Lock lock;
    private Condition notfull, importantInsideCondition;

    private int nrOfEntries = 0;
    private boolean importantInside = false;

    private int nrOfImortantInARow = 0;
    private String previousStatus = "";

    public Club(){
        lock = new ReentrantLock();
        notfull = lock.newCondition();
        importantInsideCondition = lock.newCondition();

    }

    private boolean noEntryAvailable(){
        return nrOfEntries >= MAX_NR_OF_ENTRIES;
    }

    private boolean isEmpty(){
        return nrOfEntries == 0;
    }

    public void enter(Person person) throws InterruptedException {
        lock.lock();
        try {
            while (noEntryAvailable()){
                notfull.await();
            }
            while (importantInside){
                importantInsideCondition.await();
            }
            nrOfEntries++;
            if (person.getStatus().equals("belangrijk")){
                importantInside = true;
            }
            System.out.println(person.getName() + " joined, number of people inside: " + nrOfEntries + " and important inside= " + importantInside);
        } finally {
            lock.unlock();
        }
    }

    public void leave(Person person) throws InterruptedException {
        lock.lock();
        try {
            nrOfEntries--;
            System.out.println(person.getName() + " left, number of people inside: " + nrOfEntries + " and important inside= " + importantInside);
            if (person.getStatus().equals("belangrijk")){
                importantInside = false;
                importantInsideCondition.signal();
            }
            notfull.signal();
        } finally {
            lock.unlock();
        }
    }

}

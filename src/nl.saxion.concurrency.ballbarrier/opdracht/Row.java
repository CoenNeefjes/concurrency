package nl.saxion.concurrency.ballbarrier.opdracht;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class Row {

    private Queue<Person> queue = new LinkedList<>();
    private static Row rowInstance;

    private Lock lock;
    private Condition notFull;
    private Condition notEmpty;
    private Condition alreadyInQueue;
    private Condition notInQueue;

    public static Row getRowInstance(){
        if (rowInstance == null){
            rowInstance = new Row();
        }
        return rowInstance;
    }

    public Row(){
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void add(Person person) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == Club.MAX_NR_OF_ENTRIES){
                notFull.await();
            }
            queue.add(person);
            notEmpty.signal();
            System.out.println("added " + person.getName() + " to the queue, size is now: " + queue.size());
        } finally {
            lock.unlock();
        }
    }

    public Person get() throws InterruptedException {
        lock.lock();
        Person person = null;
        try {
            while (queue.size() == 0){
                notEmpty.await();
            }
            person = queue.remove();
            notFull.signal();
        } finally {
            lock.unlock();
        }
        if (person == null){
            System.out.println(" could not get person from row");
        }
        return person;
    }

}

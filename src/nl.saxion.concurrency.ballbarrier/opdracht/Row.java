package nl.saxion.concurrency.ballbarrier.opdracht;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class Row extends Thread{

    private Queue<Person> queue = new LinkedList<>();

    private Lock lock;
    private Condition notEmpty;
    private Condition notInQueue;

    private Club club;

    public Row(Club club){
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notInQueue = lock.newCondition();
        this.club = club;
    }

    public void add(Person person) throws InterruptedException {
        lock.lock();
        try {
            // Wait if the person is already in the queue
            while (queue.contains(person)){
                notInQueue.await();
            }
            // Add the person to the queue
            queue.add(person);
            // Make sure the person knows he is in the queue
            person.setInQueue(true);
            // Signal that the queue is not empty
            notEmpty.signal();
            System.out.println("added " + person.getName() + " to the queue, size is now: " + queue.size());
        } finally {
            lock.unlock();
        }
    }

    public void get() throws InterruptedException {
        lock.lock();
        Person person = null;
        try {
            // Wait if the queue is empty
            while (queue.size() == 0){
                notEmpty.await();
            }
            // Remove the first person of the queue
            person = queue.remove();
            // Let that person into the club
            club.enter(person);
            // Signal that this person is not in the queue anymore
            notInQueue.signal();
        } finally {
            lock.unlock();
        }
    }

    public void run(){
        while (true) {
            try{
                get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

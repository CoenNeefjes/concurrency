package nl.saxion.concurrency.ballbarrier.opdracht;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class Club {

    public static final int MAX_NR_OF_ENTRIES = 10;

    private Lock lock;
    private Condition entry, noImportantInside;

    private int nrOfEntries = 0;
    private boolean importantInside = false;

    private ArrayList<Person> inTheClub = new ArrayList<>();

    public Club(){
        lock = new ReentrantLock();
        entry = lock.newCondition();
        noImportantInside = lock.newCondition();
    }

    private boolean noEntryAvailable(){
        return nrOfEntries == MAX_NR_OF_ENTRIES;
    }

    private boolean isEmpty(){
        return nrOfEntries == 0;
    }

    private boolean moreThanHalfFull() { return nrOfEntries < MAX_NR_OF_ENTRIES/2; }

    public void enter(Person person) throws InterruptedException {
        lock.lock();
        try {
            // keep track if there are important persons in the club
            if (person.getStatus().equals("belangrijk")){
                while (moreThanHalfFull() || noEntryAvailable() || importantInside){
                    entry.await();
                }
                importantInside = true;
            } else {
                // wait if there is no room or if there is an important person in the club
                while (noEntryAvailable() || importantInside){
                    entry.await();
                }
            }
            person.setInQueue(false);
            nrOfEntries++;
            assert nrOfEntries <= MAX_NR_OF_ENTRIES;
            inTheClub.add(person);
            System.out.println(person.getName() + " entered the club, now " + nrOfEntries + " inside");
            System.out.println("important inside= " + importantInside);
        } finally {
            lock.unlock();
        }
    }

    public void leave(Person person) throws InterruptedException {
        lock.lock();
        try {
            assert inTheClub.contains(person);
            inTheClub.remove(person);

            nrOfEntries--;
            assert nrOfEntries >= 0;

            if (importantInside){
                if (person.getStatus().equals("belangrijk")){
                    importantInside = false;
                    entry.signal();
                }
            } else {
                entry.signal();
            }
            System.out.println(person.getName() + " left the club, now " + nrOfEntries + " inside");
            System.out.println("important inside= " + importantInside);
        } finally {
            lock.unlock();
        }
    }

}

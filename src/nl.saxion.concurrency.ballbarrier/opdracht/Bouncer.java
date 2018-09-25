package nl.saxion.concurrency.ballbarrier.opdracht;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class Bouncer extends Thread{

    private Club club;
    private Row row;

    public Bouncer(Club club){
        this.club = club;
        this.row = Row.getRowInstance();
    }

    public void run(){
        while (true){
            getFromRow();
        }
    }

    private void getFromRow(){
        try{
            enter(row.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enter(Person person) throws InterruptedException {
        club.enter(person);
    }

    public void leave(Person person) throws InterruptedException{
        club.leave(person);
    }

}

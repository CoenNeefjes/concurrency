package nl.saxion.concurrency.ballbarrier.opdracht;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class Person  extends Thread{

    private String status;
    private Row row;
    private Club club;

    private boolean inQueue = false;

    public Person(Club club, Row row, String status){
        this.status = status;
        this.row = row;
        this.club = club;
    }

    public void run(){
        while (true){
            try {
                row.add(this);
                while (inQueue){
                    Thread.sleep(50);
                }
                party();
                club.leave(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void party(){
        try {
            if (status.equals("normaal")){
                Thread.sleep(50);
            } else {
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getStatus(){
        return this.status;
    }

    public void setInQueue(boolean value){
        inQueue = value;
    }

}

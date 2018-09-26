package nl.saxion.concurrency.ballbarrier.opdracht;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class Person  extends Thread{

    private String status;
    private Bouncer bouncer;
    private Row row;

    public Person(Bouncer bouncer, String status){
        this.status = status;
        this.bouncer = bouncer;
        this.row = Row.getRowInstance();
    }

    public void run(){
        while (true){
            try {
                row.add(this);
//                bouncer.enter(this);
                party();
                bouncer.leave(this);
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

}

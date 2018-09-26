package nl.saxion.concurrency.ballbarrier.opdracht;

/**
 * Created by coen on 21-9-2018.
 */
public class Person implements Runnable{

    private String status = "";

    public Person(String status){
        this.status = status;
    }

    public void run(){
        while (true){
            checkForEntry();
            party();
            signOut();
        }
    }

    public void checkForEntry(){

    }

    public void party(){
        try {
            wait(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void signOut(){

    }

    public String getStatus(){
        return this.status;
    }
}

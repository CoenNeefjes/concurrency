package nl.saxion.concurrency.ballbarrier.opdracht;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class World {

    private static final int NR_OF_PERSONS = 50;
    private static final int NR_OF_IMPORTANT_PERSONS = 5;

    public static void main(String[] args){
        Club club = new Club();

        Row row = Row.getRowInstance();

        Bouncer bouncer = new Bouncer(club);

        for (int i=0; i<NR_OF_PERSONS; i++){
            Person person = new Person(bouncer, "normaal");
            person.start();
        }

        for (int i=0; i<NR_OF_IMPORTANT_PERSONS; i++){
            Person person = new Person(bouncer, "belangrijk");
            person.start();
        }

        bouncer.start();
    }
}

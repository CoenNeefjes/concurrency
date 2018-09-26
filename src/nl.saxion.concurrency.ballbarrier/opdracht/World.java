package nl.saxion.concurrency.ballbarrier.opdracht;

/**
 * Created by Coen Neefjes on 25-9-2018.
 */
public class World {

    private static final int NR_OF_PERSONS = 20;
    private static final int NR_OF_IMPORTANT_PERSONS = 2;

    public static void main(String[] args){
        Club club = new Club();

        Row row = new Row(club);

        for (int i=0; i<NR_OF_PERSONS; i++){
            Person person = new Person(club, row, "normaal");
            person.start();
        }

        for (int i=0; i<NR_OF_IMPORTANT_PERSONS; i++){
            Person person = new Person(club, row, "belangrijk");
            person.start();
        }

        row.start();
    }
}

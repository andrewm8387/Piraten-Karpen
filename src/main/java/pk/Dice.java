package pk;
import java.util.Random;

public class Dice {

    public Faces roll() {
        int howManyFaces = Faces.values().length; //gets number of sides of dice from number of states in the enum
//        System.out.println("  (DEBUG) there are " + howManyFaces + " faces");   //Debug outputs
//        System.out.println("  (DEBUG) " + Arrays.toString(Faces.values()));     //
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];//Gets Faces in an array and gets a random index
    }

    
}

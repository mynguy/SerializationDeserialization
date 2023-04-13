/**
 * Name: My Nguyen
 * Main.java
 *
 * Description: This is the driver program for Movie.java. It demonstrates how to create a Movie object and serialize
 * it to a CSV file using the Java NIO API. It also shows how to deserialize the CSV file to a new Movie object and
 * compare it to the original object for equivalence.
 */

import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {

        // Create a new Movie object and set its attributes
        Movie actionMovie = new Movie("John Wick", 2014, "Action");

        // Serialize the object to a file named "action.txt"
        Movie.serialize(actionMovie, "action.txt");

        // Print the object's details to the console
        actionMovie.prettyPrint();

        // Deserialize the object from the "action.txt" file
        Movie DeserializeActionMovie = Movie.deserialize("action.txt");

        // Print the deserialized object's details to the console
        System.out.println("\nThis is DeserializeActionMovie:\n");
        DeserializeActionMovie.prettyPrint();
    }
}1
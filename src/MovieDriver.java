/**
 * Name: My Nguyen
 * MovieDriver.java
 *
 * Description: This is the driver program for Movie.java. It demonstrates how to create a Movie object and serialize
 * it to a CSV file using the Java NIO API. It also shows how to deserialize the CSV file to a new Movie object and
 * compare it to the original object for equivalence.
 */

import java.io.IOException;
public class MovieDriver {
    public static void main(String[] args) throws IOException {

        Movie actionMovie = new Movie("John Wick", 2014, "Action");

        Movie.serializeToCSV(actionMovie, "movie.txt");

        System.out.println("\nPrinting the serializedToCSV object:\n");
        System.out.println(actionMovie.prettyPrint());

        Movie DeserializeActionMovie = Movie.deserializeFromCSV("movie.txt");

        System.out.println("\nPrinting the deserializeFromCSV object:\n");
        System.out.println(DeserializeActionMovie.prettyPrint());

        System.out.println("\nAre the two movie objects equal?\n");
        System.out.println(DeserializeActionMovie.equals(actionMovie));
    }
}


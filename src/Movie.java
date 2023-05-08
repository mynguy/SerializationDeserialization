/**
 * Name: My Nguyen
 * Movie.java - Version 1.02
 *
 * Description: This project converts Java objects to CSV format and saves them to a file. It also reads and restores
 * objects from the CSV file, verifying equivalence with the original object. Java NIO API ensures efficient file
 * reading and writing, while Java reflection dynamically accesses object fields for serialization and deserialization.
 */

import java.io.*;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Movie implements Comparable<Movie> {

    private String name;
    private String genre;
    private int release;

    public Movie(String name, int release, String genre) {
        this.name = name;
        this.release = release;
        this.genre = genre;
    }
    public Movie() {
        this("", 0, "");
    }

    /**
     This method serializes a Movie object to a CSV file.
     @param movie The Movie object to be serialized.
     @param filename The name of the file to which the Movie object will be serialized.
     @throws IOException If an error occurs during the file writing process.
     */
    public static void serializeToCSV(Movie movie, String filename) throws IOException {
        Path filepath = Paths.get(filename);

        StringBuilder createStr = new StringBuilder();
        createStr.append("name, genre, release").append(System.getProperty("line.separator"));
        createStr.append(movie.getName()).append(", ").append(movie.getGenre()).append(", ").append(movie.getYear());

        String output = createStr.toString();
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8.name()));
        out.write(output);
        out.close();
    }

    /**
     This method deserializes a Movie object from a CSV file.
     @param filename The name of the CSV file from which to deserialize the Movie object.
     @return The deserialized Movie object.
     @throws IOException If an error occurs during the file reading process.
     */
    public static Movie deserializeFromCSV(String filename) throws IOException {
        Path file = Paths.get(filename);
        List data = Files.readAllLines(file);

        String state = (String) data.get(1);
        String[] stateArray = state.split(",");
        Movie copy = new Movie();

        copy.setName(stateArray[0].trim());
        copy.setGenre(stateArray[1].trim());
        copy.setYear(Integer.parseInt(stateArray[2].trim()));

        return copy;
    }

    /**
     This method returns a formatted string representation of the Movie object.
     The returned string includes the movie's name, genre, and release year in a structured manner.
     @return A string representing the pretty-printed movie information.
     */
    public String prettyPrint() {
        return "Movie Name: " + getName() + System.getProperty("line.separator") + "Genre: "
                + getGenre() + System.getProperty("line.separator") + "Year: " + getYear();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return release == movie.release && Objects.equals(name, movie.name) && Objects.equals(genre, movie.genre);
    }
    @Override
    public int compareTo(Movie otherMovie) {
        return this.name.compareToIgnoreCase(otherMovie.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, genre, release);
    }
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public int getYear() {
        return release;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setYear(int release) {
        this.release = release;
    }

}



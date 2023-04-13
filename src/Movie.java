/**
 * Name: My Nguyen
 * Movie.java
 *
 * Description: This project converts Java objects to CSV format and saves them to a file. It also reads and restores
 * objects from the CSV file, verifying equivalence with the original object. Java NIO API ensures efficient file
 * reading and writing, while Java reflection dynamically accesses object fields for serialization and deserialization.
 */

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.*;

public class Movie {

    // Variables
    private String name;
    private String genre;
    private int release;

    // Constructor
    public Movie(String name, int release, String genre) {
        this.name = name;
        this.release = release;
        this.genre = genre;
    }
    public Movie() {
        this.name = "";
        this.release = 0;
        this.genre = "";
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public int getYear() {
        return release;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setYear(int release) {
        this.release = release;
    }

    /**
     This function serializes a Movie object to a CSV file with the given filename, using UTF-8 encoding. Each field of
     the Movie object is written as a comma-separated value to a single line in the CSV file, with the field names as
     headers on the first line.
     @param obj the Movie object to serialize to CSV
     @param filename the filename of the CSV file to write to
     @throws IOException if an error occurs while writing to the file
     */
    public static void serialize(Movie obj, String filename) throws IOException {
        // Get the list of fields in the Movie object
        List<String> headers = Arrays.stream(obj.getClass().getDeclaredFields())
                .map(Field::getName)
                .toList();

        // Set the character encoding to UTF-8
        Charset charset = StandardCharsets.UTF_8;

        // Open the file and create a channel
        try (FileChannel channel = FileChannel.open(Paths.get(filename), StandardOpenOption.WRITE,
                StandardOpenOption.CREATE)) {

            // Encode the headers as a CSV line and write it to the file
            String headerLine = String.join(",", headers) + "\n";
            ByteBuffer headerBuffer = charset.encode(headerLine);
            channel.write(headerBuffer);

            // Loop through the fields and add their values to the buffer
            ByteBuffer fieldBuffer = ByteBuffer.allocate(1024);
            for (int i = 0; i < headers.size(); i++) {
                Field field = obj.getClass().getDeclaredFields()[i];
                field.setAccessible(true);
                String fieldValue = "";
                try {
                    fieldValue = Objects.toString(field.get(obj), "");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                fieldBuffer.put(charset.encode(fieldValue));
                if (i < headers.size() - 1) {
                    fieldBuffer.put(charset.encode(","));
                }
            }
            // Add a new line character to the end of the line
            fieldBuffer.put(charset.encode("\n"));

            // Flip the buffer to prepare it for writing to the file
            fieldBuffer.flip();
            // Write the buffer to the file
            channel.write(fieldBuffer);
        }
    }

    /**
     Deserializes a Movie object from a CSV file.
     @param filename The name of the file to deserialize from
     @return The deserialized Movie object, or null if there was an error
     */
    public static Movie deserialize(String filename) {
        // Initialize a new Movie object
        Movie movie = new Movie();

        // Use try-with-resources to ensure the file is properly closed after reading
        try (FileChannel channel = FileChannel.open(Paths.get(filename), StandardOpenOption.READ)) {

            // Set the character encoding to UTF-8 and create a CharsetDecoder for it
            Charset charset = StandardCharsets.UTF_8;
            CharsetDecoder decoder = charset.newDecoder();

            // Allocate a buffer to hold the CSV data
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // Read the CSV data from the file into the buffer
            channel.read(buffer);

            // Flip the buffer to prepare it for reading
            buffer.flip();

            // Decode the buffer as a string
            String csvData = decoder.decode(buffer).toString();

            // Split the CSV data into lines and remove the header line
            String[] lines = csvData.split("\n");

            if (lines.length > 1) {
                // Split the first line into headers and use them to create a map of field names to indices
                String[] headers = lines[0].split(",");
                Map<String, Integer> fieldMap = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    fieldMap.put(headers[i], i);
                }
                // Split the second line into field values and use them to set the Movie object's instance variables
                String[] fields = lines[1].split(",");
                if (fields.length > 0) {
                    // Set the name field if it is present
                    if (fieldMap.containsKey("name")) {
                        movie.setName(fields[fieldMap.get("name")]);
                    }
                    // Set the release year field if it is present and can be parsed as an integer
                    if (fieldMap.containsKey("release")) {
                        try {
                            int releaseYear = Integer.parseInt(fields[fieldMap.get("release")]);
                            movie.release = releaseYear;
                        } catch (NumberFormatException e) {
                            // If there is an error parsing the release year, ignore it and leave the value at zero
                        }
                    }
                    // Set the genre field if it is present
                    if (fieldMap.containsKey("genre")) {
                        movie.setGenre(fields[fieldMap.get("genre")]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return movie;
    }

    public void prettyPrint() {
        // Get the list of fields in the current object
        List<Field> fields = Arrays.asList(this.getClass().getDeclaredFields());

        // Map each field to a string representation of its name and value, separated by an equals sign
        String attributes = fields.stream()
                .map(field -> {
                    // Set the field to be accessible so that we can read its value
                    field.setAccessible(true);
                    // Try to get the value of the field in the current object and add it to the string
                    try {
                        return field.getName() + "= " + field.get(this);
                    } catch (IllegalAccessException e) {
                        // If there is an error getting the field value, return an empty string
                        return "";
                    }
                })
                .collect(Collectors.joining(", "));

        // Print the string of field names and values
        System.out.println("Attributes: " + attributes);
    }
}



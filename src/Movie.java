/**
 * Name: My Nguyen
 * Movie.java
 *
 * Description: Summer Project on Serialization/Deserialization with Dr. Bowring
 *
 */
public class Movie {

    // Variables
    private String name;
    private int year;

    // Constructor
    public Movie(String name, int year) {
        this.name = name;
        this.year = year;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getYear() {
        return year;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setYear(int year) {
        this.year = year;
    }

    // Static method Serialization
    public static void serialize(Movie name,String csvFile) {

    }

    // Print
    public String toString() {
        return "Name: " + name + " Year: " + year;
    }
}



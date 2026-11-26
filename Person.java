/*public class Person {

    private char name;
    private char[] persons; // tableau des personnes avec qui il a une relation
    private int[] preferences; // tableau des chambres dans l'ordre de préférence

    public Person(char name, char[] persons, int[] preferences) {
        this.name = name;
        this.persons = persons;
        this.preferences = preferences;
    }


} */

import java.util.Arrays;

public class Person {

    private char name;
    private int[] preferences;

    public Person(char name) {
        this.name = name;
        this.preferences = new int[0];
    }

    public char getName() {
        return name;
    }

    public int[] getPreferences() {
        return preferences;
    }

    public void setPreferences(int[] preferences) {
        this.preferences = Arrays.copyOf(preferences, preferences.length);
    }
}
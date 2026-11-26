import java.util.Arrays;

public class Person {

    private char name;
    private Room[] preferences;

    public Person(char name) {
        this.name = name;
        this.preferences = new Room[0]; // initialiser le tableau des préférences à une taille de 0
    }

    public char getName() {
        return name;
    }

    public Room[] getPreferences() {
        return preferences;
    }

    public void setPreferences(Room[] preferences) {
        this.preferences = Arrays.copyOf(preferences, preferences.length);
    }
}

import java.util.Arrays;

public class Person {

    private char name;
    private int[] preferences;

    public Person(char name) {
        this.name = name;
        this.preferences = new int[0]; // initialiser le tableau des préférences à une taille de 0
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

import java.util.Arrays;

public class Person {

    private char name;
    private Room assignedRoom;
    private Room[] preferences;

    public Person(char name) {
        this.name = name;
        this.assignedRoom = new Room(1); // initialiser la chambre par défaut à la chambre 1
        this.preferences = new Room[0]; // initialiser le tableau des préférences à une taille de 0
    }

    public char getName() {
        return name;
    }

    public Room[] getPreferences() {
        return preferences;
    }

    public Room getAssignedRoom() {
        return assignedRoom;
    }

    public void setRoom(Room room) {
        this.assignedRoom = room;
    }

    public void setPreferences(Room[] preferences) {
        this.preferences = Arrays.copyOf(preferences, preferences.length);
    }
}

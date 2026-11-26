import java.util.Arrays;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Entrez le nombre de personnes: ");
        int n = scanner.nextInt();

        Person[] persons = createPerson(n);
        Room[] rooms = createRoom(n);
        Graph g = new Graph(persons);
        // g.printGraph();

        System.out.print(
                "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
        int value = scanner.nextInt();

        while (value != 3) {
            switch (value) {
                case 1:
                    associatePersons(persons, g);
                    break;
                case 2:
                    // définit les préférences de la personne
                    definePreferences(persons, rooms);
                    break;
                default:
                    System.out.print("Vous avez entré une valeur invalide. Choisissez entre les valeurs proposées.\n");
                    System.out.print(
                            "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
                    value = scanner.nextInt();
                    break;
            }

            System.out.print(
                    "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
            value = scanner.nextInt();
        }

        if (value == 3) {
            // faudra vérifier que toutes les personnes ont
            // des relations et que toutes
            // leurs préférences sont définies
            System.out.print(
                    "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
            value = scanner.nextInt();

        }
    }

    public static Person[] createPerson(int n) {
        Person[] persons = new Person[n];
        for (int i = 0; i < n; i++) {
            persons[i] = new Person((char) (i + 'A'));
        }

        return persons;
    }

    public static void printPersons(Person[] persons) {
        System.out.print("Voici la liste des personnes: ");
        for (Person person : persons) {
            System.out.print(person.getName() + " ");
        }
        System.out.println();
    }

    public static void associatePersons(Person[] persons, Graph g) {
        printPersons(persons);
        System.out.print("Première personne de la relation «ne s’aiment pas» ");
        char person_1 = scanner.next().charAt(0);

        Person p1 = findPerson(persons, person_1);
        while (p1 == null) {
            System.out.println("Personne non trouvée.");
            person_1 = scanner.next().charAt(0);
            p1 = findPerson(persons, person_1);
        }

        System.out.print("Deuxième personne de la relation «ne s’aiment pas» ");
        char person_2 = scanner.next().charAt(0);

        Person p2 = findPerson(persons, person_2);
        while (p2 == null) {
            System.out.println("Personne non trouvée.");
            person_2 = scanner.next().charAt(0);
            p2 = findPerson(persons, person_2);
        }

        g.addEdge(person_1, person_2);

    }

    public static Person findPerson(Person[] persons, char person) {
        for (Person p : persons) {
            if (p.getName() == person) {
                return p;
            }
        }
        return null;
    }

    public static Room[] createRoom(int n) {
        Room[] rooms = new Room[n];
        for (int i = 0; i < n; i++) {
            rooms[i] = new Room(i + 1);
        }
        return rooms;
    }

    public static Room findRoom(Room[] rooms, int number) {
        for (Room room : rooms) {
            if (room.getNumber() == number) {
                return room;
            }
        }
        return null;
    }

    public static void definePreferences(Person[] persons, Room[] rooms) {
        printPersons(persons);

        System.out.print(
                "Entrez la personne pour laquelle vous souhaitez ajouter les préférences\n");
        char person = scanner.next().charAt(0);

        // vérifie si la personne existe dans le tableau persons
        Person p = findPerson(persons, person);
        while (p == null) {
            System.out.println("Personne non trouvée.");
            person = scanner.next().charAt(0);
            p = findPerson(persons, person);
        }

        System.out.print("Voici les chambres disponibles: ");
        for (Room room : rooms) {
            System.out.print(room.getNumber() + " ");
        }
        System.out.println();

        System.out.print("Entrez les chambres dans l'ordre de préférence: ");
        Room[] preferences = new Room[rooms.length];

        for (int i = 0; i < rooms.length; i++) {

            int roomNumber = scanner.nextInt();
            Room room = findRoom(rooms, roomNumber);

            // Vérifie que la chambre existe et n'est pas déjà choisie
            while (room == null || findRoom(preferences, roomNumber) != null) {

                if (room == null) {
                    System.out.println("Chambre non trouvée.");
                } else {
                    System.out.println("Chambre déjà dans les préférences.");
                }

                roomNumber = scanner.nextInt();
                room = findRoom(rooms, roomNumber);
            }

            preferences[i] = room;
        }

        p.setPreferences(preferences);
    }
}

// chaque chambre aura un etat ocupé ou non et
// l'algo naif sera d'attribuer à une personne sa
// chambre préférée si elle est libre et sion sa
// deuxième et sinon sa troisième et ainsi de suite.
// les relations entre les personnes sera un graphe et
// avec pour avoir l'ordre de préférence des chambres
// de chaque personne, il faudra créer
// un tableau pour enregistrer
// les préférences de chaque personne.
import java.util.Arrays;
import java.util.Scanner;

public class App2 {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
      do {
        System.out.println("Entrez le nombre de personnes entre 1 et 26: ");
        int n = scanner.nextInt();
      }while(n<1 || n>26)

        char[] persons = createPerson(n);
        int[] rooms = createRoom(n);
        Graph g = new Graph(persons);
        // g.printGraph();

        Person[] people = createPeople(persons); 
        int value;
        boolean done = false;
        while (!done) {
            System.out.print(
                "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
            value = scanner.nextInt();
            switch (value) {
                case 1:
                    associatePersons(persons, g);
                    break;
                case 2:
                    scanner.nextLine();
                     definePreferences(people);
                    break;
                case 3: 
                    done = checkPreferences(people);
                    break;
                default:
                    System.out.print("Vous avez entré une valeur invalide. Choisissez entre les valeurs proposées.\n");
            }
        }

    }

    public static Person[] createPeople(char[] persons) {
        Person[] people = new Person[persons.length];
        for (int i = 0; i < persons.length; i++) {
            people[i] = new Person(persons[i]);
        }
        return people;
    }
    public static char[] createPerson(int n) {
        char[] persons = new char[n];
        for (int i = 0; i < n; i++) {
            persons[i] = (char) (i + 'A');
            new Person(persons[i]);
        }

        return persons;
    }

    public static void printPersons(char[] persons) {
        System.out.print("Voici la liste des personnes: ");
        for (char person : persons) {
            System.out.print(person + " ");
        }
        System.out.println();
    }

    public static void associatePersons(char[] persons, Graph g) {
        printPersons(persons);
        System.out.print("Première personne de la relation «ne s’aiment pas» ");
        char person_1 = scanner.next().charAt(0);

        while (Arrays.binarySearch(persons, person_1) < 0) {
            System.out.println("Personne non trouvée.");
            person_1 = scanner.next().charAt(0);
        }

        System.out.print("Deuxième personne de la relation «ne s’aiment pas» ");
        char person_2 = scanner.next().charAt(0);

        while (Arrays.binarySearch(persons, person_2) < 0) {
            System.out.println("Personne non trouvée.");
            person_2 = scanner.next().charAt(0);
        }

        g.addEdge(person_1, person_2);

    }

    public static int[] createRoom(int n) {
        int[] rooms = new int[n];
        for (int i = 0; i < n; i++) {
            rooms[i] = i + 1;
            new Room(rooms[i], false);

        }
        return rooms;
    }

        /** Lit une ligne du type « A 1 2 3 » et enregistre les préférences. */
    public static void definePreferences(Person[] people) {
        System.out.println("Entrez le nom puis les chambres par ordre de préférence (ex. A 1 2 3):");
        String[] words = scanner.nextLine().trim().split("\\s+");

        Person person = null;
        for (Person p : people) {
            if (words[0].equals(String.valueOf(p.getName()))) {
                person = p;
            }
        }
        if (person == null) {
            System.out.println("Personne non trouvée.");
            return;
        }

        int[] preferences = new int[words.length - 1];
        for (int i = 0; i < preferences.length; i++) {
            preferences[i] = Integer.parseInt(words[i + 1]);
        }
        person.setPreferences(preferences);
    }


    public static boolean checkPreferences(Person[] people) {
        boolean valid = true;
        for (Person person : people) {
            int[] preferences = person.getPreferences();
            if (preferences.length == 0) {
                System.out.println(person.getName() + " : préférences absentes");
                valid = false;
            } else if (!isComplete(preferences, people.length)) {
                System.out.println(person.getName() + " : préférences incomplètes ou incorrectes");
                valid = false;
            }
        }
        return valid;
    }

    // true si chaque chambre de 1 à n apparaît exactement une fois. */
    public static boolean isComplete(int[] preferences, int n) {
        if (preferences.length != n) {
            return false;
        }
        boolean[] seen = new boolean[n + 1];
        for (int room : preferences) {
            if (room < 1 || room > n || seen[room]) {
                return false;
            }
            seen[room] = true;
        }
        return true;
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

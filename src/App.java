import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Entrez le nombre de personnes: ");
        int n = scanner.nextInt();

        // vérifier qu'il n'zentre pas un nombre inférieur à 0 ou supérieur à 26
        while (n < 0 || n > 26) {
            System.out.println("Veuillez entrer un nombre entre 0 et 26: ");
            n = scanner.nextInt();
        }

        Person[] persons = createPerson(n);
        Room[] rooms = createRoom(n);
        Graph g = new Graph(persons);

        boolean done = false;
        while (!done) {
            System.out.print(
                    "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
            int value = scanner.nextInt();

            switch (value) {
                case 1:
                    // crée une relation entre deux personnes
                    associatePersons(persons, g);
                    break;
                case 2:
                    // définit les préférences de la personne
                    definePreferences(persons, rooms);
                    break;
                case 3:
                    // vérifie qu eles préférences de toutes les personnes ont été définies
                    done = checkPreferences(persons);
                    break;
                default:
                    System.out.print("Vous avez entré une valeur invalide. Choisissez entre les valeurs proposées.\n");
                    System.out.print(
                            "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
                    value = scanner.nextInt();
                    break;
            }
        }

        g.printGraph();

        assignRoom(persons);

        System.out.print("Voici la liste des personnes avec leurs chambres attibuées: ");
        for (Person person : persons) {
            System.out.print(person.getName() + " " + person.getAssignedRoom() + " ");
        }
        System.out.println();
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

    public static Person verifyPersonExistence(Person[] persons) {
        String input = scanner.next();

        // vérifie si l'entrée de l'utisalateur a bien un seul caractère
        while (input.length() >= 2) {
            System.out.println("Veuillez un seul caractère pour la personne.");
            input = scanner.next();
        }

        char person = input.charAt(0);

        // vérifie si la personne existe dans le tableau persons
        Person p = findPerson(persons, person);
        while (p == null) {
            System.out.println("Personne non trouvée.");
            person = scanner.next().charAt(0);
            p = findPerson(persons, person);
        }
        return p;
    }

    public static void associatePersons(Person[] persons, Graph g) {
        // crée l'arête signifiant la relation "ne s'aime pas" entre deux personnes
        printPersons(persons);
        System.out.print("Première personne de la relation «ne s’aiment pas» ");
        Person p1 = verifyPersonExistence(persons);

        System.out.print("Deuxième personne de la relation «ne s’aiment pas» ");
        Person p2 = verifyPersonExistence(persons);

        g.addEdge(p1.getName(), p2.getName());

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
            if (room == null)
                return null;
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

        Person p = verifyPersonExistence(persons);

        if (p.getPreferences().length != 0) {
            System.out.print(
                    "Les préférences de cette personne sont déjà définies, voulez-vous les modifiées? \n");
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

    public static boolean checkPreferences(Person[] people) {
        boolean valid = true;
        for (Person person : people) {
            Room[] preferences = person.getPreferences();
            if (preferences.length == 0) {
                System.out.println(person.getName() + " : préférences absentes");
                valid = false;
            }
        }
        return valid;
    }

    public static void assignRoom(Person[] persons) {
        for (Person person : persons) {
            Room[] prefs = person.getPreferences();

            for (Room room : prefs) {
                if (room.isOccupied()) {
                    return;
                }
                room.setOccupied(true);
                person.setRoom(room);
            }
        }
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
    /** Retourne la position d'une personne dans le tableau, ou -1 si elle n'existe pas. */
    public static int findPersonIndex(Person[] persons, char name) {
        for (int i = 0; i < persons.length; i++) {
            if (persons[i].getName() == name) {
                return i;
            }
        }
        return -1;
    }

    /** Échange les chambres de deux personnes. */
    public static void swapRooms(Person[] persons, Room[] assignment) {
        System.out.println("Entrez les noms des deux personnes (ex. A B):");
        int first = findPersonIndex(persons, scanner.next().charAt(0));
        int second = findPersonIndex(persons, scanner.next().charAt(0));

        if (first < 0 || second < 0) {
            System.out.println("Personne non trouvée.");
        } else if (first == second) {
            System.out.println("Il faut deux personnes différentes.");
        } else {
            Room temp = assignment[first];
            assignment[first] = assignment[second];
            assignment[second] = temp;
        }
    }

    public static int rank(Person person, Room room) {
        Room[] preferences = person.getPreferences(); 
        int i = 0;                                    
        while (preferences[i] != room) {              
            i++;                                      
        }
        return i;                                     
    }

    /** true si la personne i préfère la chambre d'au moins une personne qu'elle n'aime pas. */
    public static boolean isJealous(int i, Person[] persons, Room[] assignment, Graph g) {
        boolean jealous = false;
        int j = 0;
        while (j < persons.length && !jealous) {
            jealous = g.hasEdge(persons[i].getName(), persons[j].getName()) && rank(persons[i], assignment[j]) < rank(persons[i], assignment[i]);
            j++;
        }
        return jealous;
    }

    /** Compte les personnes jalouses, chacune une seule fois. */
    public static int countJealous(Person[] persons, Room[] assignment, Graph g) {
        int count = 0;
        for (int i = 0; i < persons.length; i++) {
            if (isJealous(i, persons, assignment, g)) {
                count++;
            }
        }
        return count;
    }
}

// faire en sorte que si les préférences d'une personne ont déjà été remplie
// qu'on
// ne le refasse pas sans savoir

// chaque chambre aura un etat ocupé ou non et
// l'algo naif sera d'attribuer à une personne sa
// chambre préférée si elle est libre et sion sa
// deuxième et sinon sa troisième et ainsi de suite.
// les relations entre les personnes sera un graphe et
// avec pour avoir l'ordre de préférence des chambres
// de chaque personne, il faudra créer
// un tableau pour enregistrer
// les préférences de chaque personne.
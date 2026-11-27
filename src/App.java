import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Entrez le nombre de personnes: ");
        int n = scanner.nextInt();

        // vérifier qu'il n'entre pas un nombre inférieur à 1 ou supérieur à 26
        while (n < 1 || n > 26) {
            System.out.println("Veuillez entrer un nombre entre 1 et 26: ");
            n = scanner.nextInt();
        }

        Person[] persons = createPerson(n);
        Room[] rooms = createRoom(n);
        Graph g = new Graph(persons);

        boolean done = false;
        while (!done) {
            System.out.print(
                    "Choisissez l'action à effectuer: \n1.Ajouter une relation entre deux personnes;\n2.Ajouter les préférences d’une personne;\n3.Fin;\n");
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
                    // vérifie que les préférences de toutes les personnes ont été définies
                    done = checkPreferences(persons);
                    break;
                default:
                    System.out.print("Vous avez entré une valeur invalide. Choisissez entre les valeurs proposées.\n");
                    break;
            }
        }

        g.printGraph();

        assignRoom(persons);

        printAssignments(persons);

        int choice = 0;

        while (choice != 3) {
            System.out.print(
                    "Choisissez l'action à effectuer: \n1.Echanger les chambres de deux personnes;\n2.Afficher le nombre de personnes jalouses;\n3.Fin;\n");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // échange les chambres de deux personnes
                    swapRooms(persons);
                    printAssignments(persons);
                    break;
                case 2:
                    // affiche le nombre de personne jalouse
                    int jealous = countJealous(persons, g);
                    System.out.println("Voici le nombre de personnes jalouses: " + jealous);
                    break;
                case 3:
                    // fin du programme
                    break;
                default:
                    System.out.print("Vous avez entré une valeur invalide. Choisissez entre les valeurs proposées.\n");

                    break;
            }

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

        if (p1.getName() == p2.getName()) {
            System.out.println("Il faut choisir deux personnes différentes.");
        } else {
            g.addEdge(p1.getName(), p2.getName());
        }

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

    public static void setPreferences(Person person, Room[] rooms) {
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

        person.setPreferences(preferences);
    }

    public static void definePreferences(Person[] persons, Room[] rooms) {
        printPersons(persons);

        System.out.print(
                "Entrez la personne pour laquelle vous souhaitez ajouter les préférences\n");

        Person p = verifyPersonExistence(persons);

        if (p.getPreferences().length != 0) {
            int input;

            do {
                System.out.println(
                        "Les préférences de cette personne sont déjà définies.\n" +
                                "1. Maintenir la définition actuelle\n" +
                                "2. Modifier les préférences");
                input = scanner.nextInt();

                if (input != 1 && input != 2) {
                    System.out.println("Entrez un chiffre entre 1 et 2.");
                }

            } while (input != 1 && input != 2);

            if (input == 2) {
                setPreferences(p, rooms);
            }
        } else {
            setPreferences(p, rooms);
        }

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
                    continue;
                }
                room.setOccupied(true);
                person.setRoom(room);

                break;
            }
        }
    }

    /** Échange les chambres de deux personnes. */
    public static void swapRooms(Person[] persons) {
        System.out.println("Entrez les noms des deux personnes (ex. A B):");

        // j'ai remplacé les findIndex et la vérification de l'existance
        // des personnes entrées par la fonction écrite our ça
        Person p1 = verifyPersonExistence(persons);
        Person p2 = verifyPersonExistence(persons);

        if (p1.getName() == p2.getName() /*
                                          * je compare les noms des deux personnes pour être sûre qu'on ne remet pas la
                                          * mm personne deux fois
                                          */) {
            System.out.println("Il faut deux personnes différentes.");
        } else {

            Room temp = p1.getAssignedRoom();

            p1.setRoom(p2.getAssignedRoom());
            p2.setRoom(temp);
        }
    }

    public static int rank(Person person, Room room) {
        Room[] preferences = person.getPreferences();

        for (int i = 0; i < preferences.length; i++) {
            if (preferences[i] == room) {
                return i;
            }
        }

        return preferences.length;
    }

    /**
     * true si la personne i préfère la chambre d'au moins une personne qu'elle
     * n'aime pas.
     */
    public static boolean isJealous(int i, Person[] persons, Graph g) {
        if (persons[i].getAssignedRoom() == null) {
            return false;
        }

        int j = 0;

        while (j < persons.length) {
            if (i != j
                    && persons[j].getAssignedRoom() != null
                    && g.hasEdge(persons[i].getName(), persons[j].getName())
                    && rank(persons[i], persons[j].getAssignedRoom()) < rank(persons[i],
                            persons[i].getAssignedRoom())) {
                return true;
            }
            j++;
        }

        return false;
    }

    /** Compte les personnes jalouses, chacune une seule fois. */
    public static int countJealous(Person[] persons, Graph g) {
        int count = 0;
        for (int i = 0; i < persons.length; i++) {
            if (isJealous(i, persons, g)) {
                count++;
            }
        }
        return count;
    }

    public static void printAssignments(Person[] persons) {
        System.out.println("\n--- Attribution des chambres ---");

        for (Person person : persons) {
            if (person.getAssignedRoom() != null) {
                System.out.println(person.getName() + " -> " + person.getAssignedRoom().getNumber());
            } else {
                System.out.println(person.getName() + " -> Aucune chambre attribuée");
            }
        }
    }
}
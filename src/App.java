import java.util.Arrays;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Entrez le nombre de personnes: ");
        int n = scanner.nextInt();

        char[] persons = createPerson(n);
        int[] rooms = createRoom(n);
        Graph g = new Graph(persons);
        // g.printGraph();

        int value;
        System.out.print(
                "Choisissez l'action à effectuer: \n1.ajouter une relation entre deux personnes;\n2.ajouter les préférences d’une personne;\n3.fin;\n");
        value = scanner.nextInt();
        while (value != 3) {
            switch (value) {
                case 1:
                    associatePersons(persons, g);
                    value = scanner.nextInt();
                    break;
                case 2:
                    printPersons(persons);
                    System.out.print(
                            "Entrez la personne pour laquelle vous souhaitez ajouter les préférences\n");
                    char person = scanner.next().charAt(0);

                    while (Arrays.binarySearch(persons, person) < 0) {
                        System.out.println("Personne non trouvée.");
                        person = scanner.next().charAt(0);
                    }

                    System.out.print("Voici les chambres disponibles: ");
                    for (int room : rooms) {
                        System.out.print(room + " ");
                    }
                    System.out.println();

                    System.out.print("Entrez les chambres dans l'ordre de préférence: ");
                    int[] preferences = new int[n];
                    for (int i = 0; i < n; i++) {
                        // System.out.print("Préférence " + (i + 1) + ": ");
                        preferences[i] = scanner.nextInt();
                        // relier les personnes aux chambres dans l'ordre de préférence
                        while (Arrays.binarySearch(rooms, preferences[i]) < 0) {
                            System.out.println("Chambre non trouvée.");
                            preferences[i] = scanner.nextInt();
                        }
                    }

                    // L'utilisateur doit taper le nom d'une personne
                    // de la liste (à vérifier) et ensuite préciser dans
                    // l'ordre de préférence les chambres qu'elle souhaite.

                    break;
                // case 3:

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

    public static char[] createPerson(int n) {
        char[] persons = new char[n];
        for (int i = 0; i < n; i++) {
            persons[i] = (char) (i + 'A');
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
        }
        return rooms;
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
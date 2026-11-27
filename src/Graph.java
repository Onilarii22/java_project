import java.util.Arrays;

public class Graph {

    private int[][] adjacencyMatrix;
    private Person[] vertice;

    public Graph(Person[] vertice) {
        this.vertice = vertice;
        adjacencyMatrix = new int[vertice.length][vertice.length];
    }

    private int findIndex(char name) {
        for (int i = 0; i < vertice.length; i++) {
            if (vertice[i].getName() == name) {
                return i;
            }
        }
        return -1;
    }

    public void addEdge(char source, char destination) {
        int sourceIndex = findIndex(source);
        int destinationIndex = findIndex(destination);

        adjacencyMatrix[sourceIndex][destinationIndex] = 1;
        adjacencyMatrix[destinationIndex][sourceIndex] = 1;
    }

    public boolean hasEdge(char source, char destination) {
        int sourceIndex = Arrays.binarySearch(vertice, source);
        int destinationIndex = Arrays.binarySearch(vertice, destination);

        return adjacencyMatrix[sourceIndex][destinationIndex] == 1;
    }

    public void printGraph() {
        for (int i = 0; i < vertice.length; i++) {
            System.out.print(vertice[i].getName() + ": ");
            for (int j = 0; j < vertice.length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    System.out.print(vertice[j].getName() + " ");
                }
            }
            System.out.println();
        }
    }

}

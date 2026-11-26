import java.util.Arrays;

public class Graph {

    private int[][] adjacencyMatrix;
    private char[] vertice;

    public Graph(char[] vertice) {
        this.vertice = vertice;
        adjacencyMatrix = new int[vertice.length][vertice.length];
    }

    public void addEdge(char source, char destination) {
        int sourceIndex = Arrays.binarySearch(vertice, source);
        int destinationIndex = Arrays.binarySearch(vertice, destination);

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
            System.out.print(vertice[i] + ": ");
            for (int j = 0; j < vertice.length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    System.out.print(vertice[j] + " ");
                }
            }
            System.out.println();
        }
    }

}
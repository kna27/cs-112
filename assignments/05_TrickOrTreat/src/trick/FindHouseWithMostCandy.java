package trick;

import java.util.*;

public class FindHouseWithMostCandy {
    public static void main(String[] args) {
        if (args.length < 4) {
            StdOut.println(
                    "Too few arguments. Did you put in command line arguments? If using the debugger, add args to launch.json.");
            StdOut.println(
                    "Execute: java -cp bin trick.FindHouseWithMostCandy input1.in h1 Skittles findcandy1.out");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[3]);

        int n = StdIn.readInt();

        Graph graph = new Graph();

        for (int i = 0; i < n; i++) {
            String house = StdIn.readString();
            graph.addVertex(house);

            int numCandies = StdIn.readInt();
            for (int j = 0; j < numCandies; j++) {
                graph.addCandy(house, new Candy(StdIn.readString(), StdIn.readInt()));
            }
        }

        int e = StdIn.readInt();
        for (int i = 0; i < e; i++) {
            String from = StdIn.readString();
            String to = StdIn.readString();
            int weight = StdIn.readInt();
            graph.addEdge(from, to, weight);
            graph.addEdge(to, from, weight);
        }

        Set<String> visited = new HashSet<>();
        List<String> order = new ArrayList<>();

        Graph.dfs(graph, args[1], visited, order);

        String maxHouse = null;
        int max = -1;

        for (String houseName : order) {
            List<Candy> candies = graph.getCandies(houseName);
            if (candies != null) {
                for (Candy candy : candies) {
                    if (candy.name.equals(args[2])) {
                        if (candy.count > max) {
                            max = candy.count;
                            maxHouse = houseName;
                        }
                        break;
                    }
                }
            }
        }
        if (maxHouse != null) {
            StdOut.println(maxHouse);
        }
    }
}

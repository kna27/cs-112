package trick;

import java.util.*;

public class CanAvoidCurfew {
    public static void main(String[] args) {
        if (args.length < 5) {
            StdOut.println(
                    "Too few arguments. Did you put in command line arguments? If using the debugger, add args to launch.json.");
            StdOut.println(
                    "Execute: java -cp bin trick.CanAvoidCurfew input1.in h1 h8 100 shortestpaths1.out");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[4]);

        int n = StdIn.readInt();
        int curfew = Integer.parseInt(args[3]);
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

        Map<String, String> pred = new HashMap<>();
        int dist = graph.dijkstra(args[1], pred).get(args[2]);

        if (dist <= curfew) {
            StdOut.println("true " + dist);
        } else {
            StdOut.println("false " + dist);
        }
    }
}

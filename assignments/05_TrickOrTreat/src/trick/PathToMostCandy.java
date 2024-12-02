package trick;

import java.util.*;

public class PathToMostCandy {
    public static void main(String[] args) {
        if (args.length < 4) {
            StdOut.println(
                    "Too few arguments. Did you put in command line arguments? If using the debugger, add args to launch.json.");
            StdOut.println(
                    "execute: java -cp bin trick.PathToMostCandy input1.in h1 KitKat mostcandy1.out");
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
        Map<String, String> edgeTo = new HashMap<>();
        Queue<String> queue = new LinkedList<>();

        String maxHouse = null;
        int max = -1;

        visited.add(args[1]);
        queue.add(args[1]);

        while (!queue.isEmpty()) {
            String house = queue.poll();
            List<Candy> candies = graph.getCandies(house);
            if (candies != null) {
                for (Candy candy : candies) {
                    if (candy.name.equals(args[2])) {
                        if (candy.count > max) {
                            max = candy.count;
                            maxHouse = house;
                        }
                        break;
                    }
                }
            }

            List<House> neighbors = graph.adj(house);
            if (neighbors != null) {
                for (House neighbor : neighbors) {
                    String neighborName = neighbor.name;
                    if (!visited.contains(neighborName)) {
                        visited.add(neighborName);
                        edgeTo.put(neighborName, house);
                        queue.add(neighborName);
                    }
                }
            }
        }

        if (maxHouse != null) {
            List<String> path = new ArrayList<>();
            String v = maxHouse;
            while (v != null && !v.equals(args[1])) {
                path.add(v);
                v = edgeTo.get(v);
            }
            path.add(args[1]);
            Collections.reverse(path);

            for (String house : path) {
                StdOut.print(house + " ");
            }
        }
    }
}

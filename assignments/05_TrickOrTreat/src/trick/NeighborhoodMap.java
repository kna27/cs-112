package trick;

import java.util.ArrayList;
import java.util.List;

public class NeighborhoodMap {
    public static void main(String[] args) {
        if (args.length < 2) {
            StdOut.println(
                    "Too few arguments. Did you put in command line arguments? If using the debugger, add args to launch.json.");
            StdOut.println(
                    "Execute: java -cp bin trick.NeighborhoodMap <neighborhoodmap INput file> <neighborhoodmap OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int n = StdIn.readInt();

        Graph graph = new Graph();
        List<String> houses = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String house = StdIn.readString();
            houses.add(house);
            graph.addVertex(house);

            int candies = StdIn.readInt();
            for (int j = 0; j < candies; j++) {
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

        for (String houseName : houses) {
            StdOut.print(houseName);
            List<Candy> candies = graph.getCandies(houseName);
            if (candies != null) {
                for (Candy candy : candies) {
                    StdOut.print(" " + candy.getName() + " " + candy.getCount());
                }
            }
            StdOut.println();
        }

        for (String house : houses) {
            StdOut.print(house);
            List<House> adjHouses = graph.adj(house);
            if (adjHouses != null) {
                for (House neighbor : adjHouses) {
                    StdOut.print(" " + neighbor.getName() + " " + neighbor.getWeight());
                }
            }
            StdOut.println();
        }
    }
}

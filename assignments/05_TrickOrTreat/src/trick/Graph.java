package trick;

import java.util.*;

public class Graph {
    private HashMap<String, List<House>> adjList;
    private HashMap<String, List<Candy>> candies;

    public Graph() {
        adjList = new HashMap<>();
        candies = new HashMap<>();
    }

    public void addVertex(String houseName) {
        adjList.putIfAbsent(houseName, new ArrayList<>());
    }

    public void addEdge(String from, String to, int weight) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.get(from).add(new House(to, weight));
    }

    public List<House> adj(String houseName) {
        return adjList.get(houseName);
    }

    public void addCandy(String houseName, Candy candy) {
        candies.putIfAbsent(houseName, new ArrayList<>());
        candies.get(houseName).add(candy);
    }

    public List<Candy> getCandies(String houseName) {
        return candies.get(houseName);
    }

    static void dfs(Graph graph, String current, Set<String> visited, List<String> order) {
        visited.add(current);
        order.add(current);
        List<House> neighbors = graph.adj(current);
        if (neighbors != null) {
            for (House neighbor : neighbors) {
                String name = neighbor.name;
                if (!visited.contains(name)) {
                    dfs(graph, name, visited, order);
                }
            }
        }
    }

    public Map<String, Integer> dijkstra(String s, Map<String, String> pred) {
        Map<String, Integer> d = new HashMap<>();
        Set<String> done = new HashSet<>();
        PriorityQueue<String> fringe = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(d.get(o1), d.get(o2));
            }
        });

        for (String vertex : adjList.keySet()) {
            d.put(vertex, Integer.MAX_VALUE);
            pred.put(vertex, null);
        }
        d.put(s, 0);
        pred.put(s, null);

        fringe.add(s);

        while (!fringe.isEmpty()) {
            String m = fringe.poll();
            done.add(m);

            List<House> neighbors = adjList.get(m);
            if (neighbors != null) {
                for (House neighbor : neighbors) {
                    String w = neighbor.name;
                    int weight = neighbor.weight;

                    if (!done.contains(w)) {
                        if (d.get(w) == Integer.MAX_VALUE) {
                            d.put(w, d.get(m) + weight);
                            fringe.add(w);
                            pred.put(w, m);
                        } else if (d.get(w) > (d.get(m) + weight)) {
                            d.put(w, d.get(m) + weight);
                            pred.put(w, m);
                        }
                    }
                }
            }
        }

        return d;
    }
}

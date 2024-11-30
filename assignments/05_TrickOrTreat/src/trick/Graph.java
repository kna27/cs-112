package trick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}

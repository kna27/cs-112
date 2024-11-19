package test;

import static org.junit.Assert.*;
import org.junit.*;
import graph.FlightPathGraph;

/**
 * JUnit class for testing FlightPathGraph
 */
public class FlightPathGraphTest {
    
    // For reference, the autolab input. Some test cases are hidden from you.
    // Three graphs, so citiesInput[0], edgesInput[0], and removeInput[0] all correspond to the data for one test case.
    String[][] citiesInput =  {{"New York", "London", "Shanghai", "Tokyo", "Paris", "Berlin"}, // First graph vertices
                                {"A", "B", "C", "D", "E", "F"}}; // Second graph vertices

    String[][][] edgesInput = { {{"New York", "London"}, {"Shanghai", "Berlin"}, {"Berlin", "Paris"}, {"New York", "Paris"}, {"New York", "Tokyo"}}, // First graph edges to add
                                {{"A", "B"}, {"B", "D"}, {"A", "C"}, {"E", "F"}, {"D", "B"}} }; // Second graph edges to add

    String[][][] removeInput = { {{"New York", "London"}, {"Shanghai", "Berlin"}, {"New York", "Paris"}}, // First graph edges to remove
                                {{"A", "D"}, {"E", "F"}, {"D", "B"}}}; // Second graph edges to remove


    @Test
    public void testConstructor() {
        String[] testCities = {"0","1","2","3","4","5"};
        FlightPathGraph graph = new FlightPathGraph(testCities);
        assertEquals(graph.flightPaths.length, testCities.length);
        for (int i = 0; i < graph.flightPaths.length; i++) {
            assertEquals(graph.flightPaths[i].city, testCities[i]);
            assertNull(graph.flightPaths[i].next);
        }
    } 

    @Test
    public void testAddEdge() {
        String[] testCities = {"0","1","2","3","4","5"};
        FlightPathGraph graph = new FlightPathGraph(testCities);
        
        fail("You haven't written this test case yet!");
    } 

    @Test
    public void testRemoveEdge() {
        String[] testCities = {"0","1","2","3","4","5"};
        FlightPathGraph graph = new FlightPathGraph(testCities);
        
        fail("You haven't written this test case yet!");
    } 
}

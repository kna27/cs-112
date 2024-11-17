package quality;

import java.util.ArrayList;

/**
 * This class represents the AirQuality system which populates a hashtable with
 * states and counties
 * and calculates various statistics related to air quality.
 * 
 * This class is a part of the AirQuality project.
 * 
 * @author Anna Lu
 * @author Srimathi Vadivel
 */

public class AirQuality {

    private State[] states; // hash table used to store states. This HT won't need rehashing.

    /**
     * **DO NOT MODIFY THIS METHOD**
     * Constructor creates a table of size 10.
     */

    public AirQuality() {
        states = new State[10];
    }

    /**
     ** *DO NOT MODIFY THIS METHOD**
     * Returns the hash table.
     * 
     * @return the value held to represent the hash table
     */
    public State[] getHashTable() {
        return states;
    }

    /**
     * 
     * DO NOT UPDATE THIS METHOD
     * 
     * This method populates the hashtable with the information from the inputFile
     * parameter.
     * It is expected to insert a state and then the counties into each state.
     * 
     * Once a state is added, use the load factor to check if a resize of the hash
     * table
     * needs to occur.
     * 
     * @param inputLine A line from the inputFile with the following format:
     *                  State Name,County Name,AQI,Latitude,Longitude,Pollutant
     *                  Name,Color
     */

    public void buildTable(String inputFile) {

        StdIn.setFile(inputFile); // opens the inputFile to be read
        StdIn.readLine(); // skips header

        while (!StdIn.isEmpty()) {

            String line = StdIn.readLine();
            State s = addState(line);
            addCountyAndPollutant(s, line);
        }
    }

    /**
     * Inserts a single State into the hash table states.
     * 
     * Note: No duplicate states allowed. If the state is already present, simply
     * return the State object. Otherwise, insert at the front of the list.
     * 
     * @param inputLine A line from the inputFile with the following format:
     *                  State Name,County Name,AQI,Latitude,Longitude,Pollutant
     *                  Name,Color
     * 
     *                  USE: Math.abs("State Name".hashCode()) as the key into the
     *                  states hash table.
     *                  USE: hash function as: hash(key) = key % array length
     * 
     * @return the State object if already present in the table or the newly created
     *         State object inserted.
     */

    public State addState(String inputLine) {
        String[] token = inputLine.split(",");
        String name = token[0];
        int key = Math.abs(name.hashCode());
        int index = key % states.length;
        State curr = states[index];

        while (curr != null) {
            if (curr.getName().equals(name)) {
                return curr;
            }
            curr = curr.getNext();
        }

        State newState = new State(name);
        newState.setNext(states[index]);
        states[index] = newState;

        return newState;
    }

    /**
     * Returns true if the counties hash table (within State) needs to be resized
     * (re-hashed)
     *
     * Resize the hash table when (number of counties)/(array size) >= loadFactor
     * 
     * @return true if resizing needs to happen, false otherwise
     */
    public boolean checkCountiesHTLoadFactor(State state) {
        return (state.getNumberOfCounties() / state.getCounties().length) >= state.getLoadFactor();
    }

    /**
     * Resizes (rehashes) the State's counties hashtable by doubling its size.
     * 
     * USE: county.hashCode() as the key into the State's counties hash table.
     */
    public void rehash(State state) {
        County[] old = state.getCounties();
        County[] resized = new County[old.length * 2];
        for (County county : old) {
            while (county != null) {
                County next = county.getNext();

                int newKey = Math.abs(county.hashCode());
                int newIndex = newKey % resized.length;

                county.setNext(resized[newIndex]);
                resized[newIndex] = county;

                county = next;
            }
        }
        state.setCounties(resized);
    }

    /**
     * This method:
     * 1) Inserts the county (from the input line) into State, if not already
     * present.
     * Check the State's counties hash table load factor after inserting. The hash
     * table may need
     * to be resized.
     * 
     * 2) Then inserts the pollutant (from the input line) into County (from the
     * input line), if not already present.
     * If pollutant is present, update AQI.
     * 
     * Note: no duplicate counties in the State.
     * Note: no duplicate pollutants in the County.
     * 
     * @param inputLine A line from the inputFile with the following format:
     *                  State Name,County Name,AQI,Latitude,Longitude,Pollutant
     *                  Name,Color
     * 
     *                  USE: Math.abs("County Name".hashCode()) as the key into the
     *                  State's counties hash table.
     *                  USE: the hash function as: hash(key) = key % array length
     */

    public void addCountyAndPollutant(State state, String inputLine) {
        String[] tokens = inputLine.split(",");

        String name = tokens[1];
        int aqi = Integer.parseInt(tokens[2]);
        double latitude = Double.parseDouble(tokens[3]);
        double longitude = Double.parseDouble(tokens[4]);
        String pollutant = tokens[5];
        String color = tokens[6];

        int key = Math.abs(name.hashCode());
        County[] counties = state.getCounties();
        int index = key % counties.length;

        County curr = counties[index];
        County county = null;

        while (curr != null) {
            if (curr.getName().equals(name)) {
                county = curr;
                break;
            }
            curr = curr.getNext();
        }

        if (county == null) {
            County newCounty = new County(name, latitude, longitude, null);
            state.addCounty(newCounty);
            county = newCounty;
        }

        if (checkCountiesHTLoadFactor(state)) {
            rehash(state);
            counties = state.getCounties();
            index = key % counties.length;
            curr = counties[index];
            county = null;
            while (curr != null) {
                if (curr.getName().equals(name)) {
                    county = curr;
                    break;
                }
                curr = curr.getNext();
            }
        }

        ArrayList<Pollutant> pollutants = county.getPollutants();
        boolean found = false;

        for (Pollutant countyPollutant : pollutants) {
            if (countyPollutant.getName().equals(pollutant)) {
                countyPollutant.setAQI(aqi);
                countyPollutant.setColor(color);
                found = true;
                break;
            }
        }

        if (!found) {
            pollutants.add(new Pollutant(pollutant, aqi, color));
        }
    }

    /**
     * Sets states' simple stats AQI for each State in the hash table.
     */
    public void setStatesAQIStats() {
        for (int i = 0; i < states.length; i++) {
            State currState = states[i];
            while (currState != null) {
                double total = 0;
                int count = 0;
                double highest = Double.NEGATIVE_INFINITY;
                double lowest = Double.POSITIVE_INFINITY;
                County highestAQICounty = null;
                County lowestAQICounty = null;

                County[] counties = currState.getCounties();

                for (int j = 0; j < counties.length; j++) {
                    County currCounty = counties[j];
                    while (currCounty != null) {
                        ArrayList<Pollutant> pollutants = currCounty.getPollutants();

                        for (Pollutant pollutant : pollutants) {
                            int aqi = pollutant.getAQI();
                            total += aqi;
                            count++;

                            if (aqi > highest) {
                                highest = aqi;
                                highestAQICounty = currCounty;
                            }

                            if (aqi < lowest) {
                                lowest = aqi;
                                lowestAQICounty = currCounty;
                            }
                        }

                        currCounty = currCounty.getNext();
                    }
                }

                if (count > 0) {
                    currState.setAvgAQI(total / count);
                } else {
                    currState.setAvgAQI(0);
                }

                currState.setHighestAQI(highestAQICounty);
                currState.setLowestAQI(lowestAQICounty);

                currState = currState.getNext();
            }
        }
    }

    /**
     * In this method you will find all the counties within a state that have the
     * same parameter name
     * and above the AQI threshold.
     * 
     * @param stateName     The name of the state
     * @param pollutantName The parameter name to filter by
     * @param AQIThreshold  The AQI threshold
     * @return ArrayList<County> List of counties that meet the criteria
     */

    public ArrayList<County> meetsThreshold(String stateName, String pollutantName, int AQIThreshold) {
        ArrayList<County> met = new ArrayList<>();
        int key = Math.abs(stateName.hashCode());
        int index = key % states.length;
        State state = states[index];

        while (state != null) {
            if (state.getName().equals(stateName)) {
                County[] counties = state.getCounties();
                for (County county : counties) {
                    County curr = county;
                    while (curr != null) {
                        ArrayList<Pollutant> pollutants = curr.getPollutants();
                        for (Pollutant pollutant : pollutants) {
                            if (pollutant.getName().equals(pollutantName) && pollutant.getAQI() >= AQIThreshold) {
                                met.add(curr);
                                break;
                            }
                        }
                        curr = curr.getNext();
                    }
                }
                break;
            }
            state = state.getNext();
        }

        return met;
    }

}

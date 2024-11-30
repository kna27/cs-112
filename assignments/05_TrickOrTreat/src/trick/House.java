package trick;

public class House {
    private String name;
    private int weight;

    public House(String name) {
        this.name = name;
        this.weight = 0;
    }

    public House(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}

public class Product {
    private String name;
    private String description;
    private String id;
    private double cost;

    public Product(String name, String description, String id, double cost) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return String.format("%-35s%-75s%-6s", name, description, id);
    }
}

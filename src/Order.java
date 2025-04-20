public class Order {
    private Blueprint blueprint;
    private int quantity;

    public Order(Blueprint blueprint, int quantity) {
        this.blueprint = blueprint;
        this.quantity = quantity;
    }

    public Blueprint getBlueprint() {
        return blueprint;
    }

    public int getQuantity() {
        return quantity;
    }
    
} 
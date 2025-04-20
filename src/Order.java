public class Order {
    private Blueprint blueprint;
    private int quantity;

    public Order(Blueprint blueprint, int quantity) {
        if (blueprint == null) {
            throw new IllegalArgumentException("Blueprint cannot be null");
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Order quantity must be positive, got: " + quantity);
        }
        
        this.blueprint = blueprint;
        this.quantity = quantity;
    }

    public Blueprint getBlueprint() {
        return blueprint;
    }

    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Validates that the order has all required information
     * @return true if the order is valid, false otherwise
     */
    public boolean isValid() {
        // Check if blueprint is valid
        if (blueprint == null || !blueprint.isValid()) {
            return false;
        }
        
        // Check if quantity is valid
        return quantity > 0;
    }
} 
/**
 * Order class for the orders in the csv files
 * holds the blueprint of the product and the number of orders
 */
public class Order {
    private Blueprint blueprint;
    private Double quantity;

    public Order(Blueprint blueprint, Double quantity) {
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

    public Double getQuantity() {
        return quantity;
    }

    public void markOneCompleted() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public boolean isFinished() {
        return quantity == 0;
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
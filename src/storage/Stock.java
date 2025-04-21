package storage;

import exception.InvalidStockException;
import component_composite.Component;

public class Stock {
    private Component component;
    private Double quantity;
    private Boolean isStockValid = false;

    // Default constructor
    public Stock() {
        this.component = null;
        this.quantity = 0.0;
        this.isStockValid = false;
    }
    // Full constructor
    public Stock(Component component, Double quantity) {
        this.component = component;
        this.quantity = quantity;
        this.isStockValid = true;
    }
    // Copy constructor
    public Stock(Stock stock) {
        this.component = stock.component;
        this.quantity = stock.quantity;
        this.isStockValid = stock.isStockValid;
    }

    // Getters and Setters
    public void setComponent(Component component) {
        this.component = component;
    }
    public Component getComponent() {
        return component;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    public Double getQuantity() {
        return quantity;
    }

    // New getter/setter for validity
    public Boolean getIsStockValid() {
        return isStockValid;
    }
    public void setIsStockValid(Boolean isStockValid) {
        this.isStockValid = isStockValid;
    }

    /**
     * Call before any business logic that requires the stock to be valid.
     * @throws InvalidStockException if this.isStockValid == false
     */
    public void validate() throws InvalidStockException {
        if (Boolean.FALSE.equals(isStockValid)) {
            throw new InvalidStockException(
                "Stock entry is invalid for component: " +
                (component != null ? component.getName() : "UNKNOWN")
            );
        }
    }
}

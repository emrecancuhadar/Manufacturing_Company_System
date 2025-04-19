package main.java.com.manufacturing.model;

/**
 * Component abstract class - base class for all components
 * Component node in the Composite pattern
 */
public abstract class Component {
    private int id;
    private String name;
    private double unitCost;
    private double unitWeight;
    private int stockQuantity;
    
    /**
     * Create a new component
     * @param id Unique identifier
     * @param name Component name
     * @param unitCost Cost per unit
     * @param unitWeight Weight per unit
     * @param stockQuantity Initial stock quantity
     */
    public Component(int id, String name, double unitCost, double unitWeight, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.unitCost = unitCost;
        this.unitWeight = unitWeight;
        this.stockQuantity = stockQuantity;
    }
    
    /**
     * Calculate the total cost of this component
     * @return The cost
     */
    public abstract double calculateCost();
    
    /**
     * Calculate the total weight of this component
     * @return The weight
     */
    public abstract double calculateWeight();
    
    /**
     * Check if there is enough stock of this component
     * @param requiredQty The required quantity
     * @return true if sufficient stock available, false otherwise
     */
    public boolean checkStock(int requiredQty) {
        return stockQuantity >= requiredQty;
    }
    
    /**
     * Reduce the stock quantity of this component
     * @param quantity The quantity to reduce by
     * @return true if successful, false if not enough stock
     */
    public boolean reduceStock(int quantity) {
        if (quantity <= 0) {
            return true; // Nothing to reduce
        }
        
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Increase the stock quantity of this component
     * @param quantity The quantity to increase by
     */
    public void increaseStock(int quantity) {
        if (quantity > 0) {
            stockQuantity += quantity;
        }
    }
    
    // Getters and setters
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getUnitCost() {
        return unitCost;
    }
    
    public double getUnitWeight() {
        return unitWeight;
    }
    
    public int getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    @Override
    public String toString() {
        return name + " (Stock: " + stockQuantity + ")";
    }
} 
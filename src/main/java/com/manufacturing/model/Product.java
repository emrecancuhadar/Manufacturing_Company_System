package main.java.com.manufacturing.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Product class - represents a composite component that contains other components
 * Composite node in the Composite pattern
 */
public class Product extends Component {
    
    // Map of component to quantity needed for this product
    private Map<Component, Double> components;
    
    public Product(int id, String name, double unitCost, double unitWeight, int stockQuantity) {
        super(id, name, unitCost, unitWeight, stockQuantity);
        this.components = new HashMap<>();
    }
    
    /**
     * Add a component to this product with the specified quantity
     * @param component The component to add
     * @param quantity Quantity needed for one unit of this product
     */
    public void addComponent(Component component, double quantity) {
        if (component != null && quantity > 0) {
            components.put(component, quantity);
        }
    }
    
    /**
     * Remove a component from this product
     * @param component The component to remove
     */
    public void removeComponent(Component component) {
        if (component != null) {
            components.remove(component);
        }
    }
    
    /**
     * Get all the components of this product
     * @return Map of components to their quantities
     */
    public Map<Component, Double> getComponents() {
        return new HashMap<>(components);
    }
    
    @Override
    public double calculateCost() {
        // Base cost of the product itself
        double totalCost = getUnitCost();
        
        // Add the cost of all child components
        for (Map.Entry<Component, Double> entry : components.entrySet()) {
            Component component = entry.getKey();
            Double quantity = entry.getValue();
            totalCost += component.calculateCost() * quantity;
        }
        
        return totalCost;
    }
    
    @Override
    public double calculateWeight() {
        // Base weight of the product itself
        double totalWeight = getUnitWeight();
        
        // Add the weight of all child components
        for (Map.Entry<Component, Double> entry : components.entrySet()) {
            Component component = entry.getKey();
            Double quantity = entry.getValue();
            totalWeight += component.calculateWeight() * quantity;
        }
        
        return totalWeight;
    }
    
    @Override
    public boolean checkStock(int requiredQty) {
        // First check if we have enough stock of the product itself
        if (!super.checkStock(requiredQty)) {
            System.out.println("DEBUG: Not enough product stock for " + getName() + 
                              ": required=" + requiredQty + 
                              ", available=" + getStockQuantity());
            return false;
        }
        
        // Then check if there's enough stock of all components
        for (Map.Entry<Component, Double> entry : components.entrySet()) {
            Component component = entry.getKey();
            Double quantityPerProduct = entry.getValue();
            
            // Calculate total quantity needed of this component
            double totalQuantityNeeded = quantityPerProduct * requiredQty;
            int roundedQuantity = (int)Math.ceil(totalQuantityNeeded);
            
            if (!component.checkStock(roundedQuantity)) {
                System.out.println("DEBUG: Product " + getName() + " - Not enough component " + 
                                  component.getName() + ": required=" + roundedQuantity + 
                                  ", available=" + component.getStockQuantity());
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Reduce the stock of all components needed for this product
     * @param quantity Number of products to manufacture
     * @return true if successful, false if not enough stock
     */
    public boolean consumeComponents(int quantity) {
        // Check if we have enough stock first
        if (!checkStock(quantity)) {
            return false;
        }
        
        // Consume the components
        System.out.println("DEBUG: Consuming components for " + getName());
        for (Map.Entry<Component, Double> entry : components.entrySet()) {
            Component component = entry.getKey();
            Double quantityPerProduct = entry.getValue();
            
            // Calculate and reduce the total quantity
            double totalQuantityNeeded = quantityPerProduct * quantity;
            int roundedQuantity = (int)Math.ceil(totalQuantityNeeded);
            System.out.println("DEBUG: Reducing " + component.getName() + 
                              " stock by " + roundedQuantity + 
                              " (from " + component.getStockQuantity() + ")");
            component.reduceStock(roundedQuantity);
        }
        
        return true;
    }
} 
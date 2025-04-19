package main.java.com.manufacturing.state;

import main.java.com.manufacturing.model.Component;
import main.java.com.manufacturing.model.Product;
import main.java.com.manufacturing.process.ManufacturingProcess;

import java.util.Map;

/**
 * WaitingForStockState - Initial state of manufacturing
 * Checks if all components are available in sufficient quantity
 */
public class WaitingForStockState implements ManufacturingState {
    
    @Override
    public void handle(ManufacturingProcess process) {
        Product product = process.getProduct();
        
        // Try to consume components for manufacturing
        if (consumeComponents(product, 1)) {
            // Successfully consumed components, proceed to manufacturing
            process.setState(new InManufacturingState());
        } else {
            // Not enough stock for components
            process.setFailureReason("Stock Shortage");
            process.setState(new FailedState());
        }
    }
    
    /**
     * Check and consume components without relying on Product's methods
     * to avoid checking finished product stock
     */
    private boolean consumeComponents(Product product, int quantity) {
        Map<Component, Double> components = product.getComponents();
        
        // First check if all components have sufficient stock
        for (Map.Entry<Component, Double> entry : components.entrySet()) {
            Component component = entry.getKey();
            double quantityNeeded = entry.getValue() * quantity;
            int roundedQuantity = (int)Math.ceil(quantityNeeded);
            
            if (!component.checkStock(roundedQuantity)) {
                System.out.println("Stock shortage for " + component.getName() + 
                                  ": required=" + roundedQuantity + 
                                  ", available=" + component.getStockQuantity());
                return false;
            }
        }
        
        // All components available, consume them
        System.out.println("All components available for " + product.getName() + ", consuming stock");
        for (Map.Entry<Component, Double> entry : components.entrySet()) {
            Component component = entry.getKey();
            double quantityNeeded = entry.getValue() * quantity;
            int roundedQuantity = (int)Math.ceil(quantityNeeded);
            
            System.out.println("  - Using " + roundedQuantity + " units of " + component.getName());
            component.reduceStock(roundedQuantity);
        }
        
        return true;
    }
    
    @Override
    public String getStateName() {
        return "Waiting For Stock";
    }
} 
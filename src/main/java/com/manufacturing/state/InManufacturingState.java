package main.java.com.manufacturing.state;

import main.java.com.manufacturing.model.Component;
import main.java.com.manufacturing.model.Product;
import main.java.com.manufacturing.process.ManufacturingProcess;

import java.util.Map;
import java.util.Random;

/**
 * InManufacturingState - State representing the manufacturing process in progress
 */
public class InManufacturingState implements ManufacturingState {
    
    private final Random random = new Random();
    
    @Override
    public void handle(ManufacturingProcess process) {
        // Generate a random number between 1-3 as per assignment requirements
        int outcome = random.nextInt(3) + 1;
        
        System.out.println("  - Manufacturing in progress...");
        Product product = process.getProduct();
        
        if (outcome == 1) {
            // Outcome 1: Successful Manufacturing
            System.out.println("  - Manufacturing completed successfully");
            
            // Increase the stock of the finished product
            product.increaseStock(1);
            
            // Transition to completed state
            process.setState(new CompletedState());
        } else if (outcome == 2) {
            // Outcome 2: Unsuccessful Manufacturing — System Error
            System.out.println("  - Manufacturing failed: System Error");
            process.setFailureReason("System Error");
            
            // Return components to inventory
            returnComponentsToInventory(product);
            
            // Transition to failed state
            process.setState(new FailedState());
        } else { // outcome == 3
            // Outcome 3: Unsuccessful Manufacturing — Damaged Component
            System.out.println("  - Manufacturing failed: Damaged Component");
            process.setFailureReason("Damaged Component");
            
            // Return components to inventory
            returnComponentsToInventory(product);
            
            // Transition to failed state
            process.setState(new FailedState());
        }
        
        // Important: Do not call handle on the new state to avoid recursion
        // The state transition is complete here
    }
    
    /**
     * Helper method to return components to inventory when manufacturing fails
     * @param product The product being manufactured
     */
    private void returnComponentsToInventory(Product product) {
        // Return the components that were consumed
        product.getComponents().forEach((component, quantity) -> {
            int roundedQuantity = (int)Math.ceil(quantity);
            System.out.println("  - Returning " + roundedQuantity + " units of " + 
                               component.getName() + " to inventory");
            component.increaseStock(roundedQuantity);
        });
    }
    
    @Override
    public String getStateName() {
        return "In Manufacturing";
    }
} 
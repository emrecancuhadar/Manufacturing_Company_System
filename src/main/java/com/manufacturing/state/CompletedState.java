package main.java.com.manufacturing.state;

import main.java.com.manufacturing.model.Product;
import main.java.com.manufacturing.process.ManufacturingProcess;

/**
 * CompletedState - Terminal state for successful product manufacturing
 */
public class CompletedState implements ManufacturingState {
    
    @Override
    public void handle(ManufacturingProcess process) {
        // Manufacturing completed successfully
        // The stock of the finished product is already increased in InManufacturingState
        
        // No further transitions from this terminal state
    }
    
    @Override
    public String getStateName() {
        return "Completed";
    }
} 
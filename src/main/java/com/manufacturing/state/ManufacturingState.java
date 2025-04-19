package main.java.com.manufacturing.state;

import main.java.com.manufacturing.process.ManufacturingProcess;

/**
 * ManufacturingState interface - part of the State pattern
 * Defines the interface for different states in the manufacturing process
 */
public interface ManufacturingState {
    
    /**
     * Handle the current state's logic and determine the next state
     * @param process The manufacturing process context
     */
    void handle(ManufacturingProcess process);
    
    /**
     * Get a descriptive name for this state
     * @return State name
     */
    String getStateName();
} 
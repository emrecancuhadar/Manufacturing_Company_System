package main.java.com.manufacturing.state;

import main.java.com.manufacturing.process.ManufacturingProcess;

/**
 * FailedState - Terminal state for a failed manufacturing process
 */
public class FailedState implements ManufacturingState {
    
    @Override
    public void handle(ManufacturingProcess process) {
        // Manufacturing failed, no further processing needed
        // The failure reason should already be set before transitioning to this state
        
        // No further transitions from this terminal state
    }
    
    @Override
    public String getStateName() {
        return "Failed";
    }
} 
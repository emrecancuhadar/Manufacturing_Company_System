package main.java.com.manufacturing.process;

import main.java.com.manufacturing.model.Product;
import main.java.com.manufacturing.state.ManufacturingState;
import main.java.com.manufacturing.state.WaitingForStockState;

/**
 * ManufacturingProcess class - context in the State pattern
 * Manages the manufacturing process of a product and its current state
 */
public class ManufacturingProcess {
    
    private Product product;
    private ManufacturingState currentState;
    private String failureReason;
    private boolean processingComplete;
    
    /**
     * Create a new manufacturing process for a product
     * @param product The product to manufacture
     */
    public ManufacturingProcess(Product product) {
        this.product = product;
        // Initial state is WaitingForStock
        this.currentState = new WaitingForStockState();
        this.processingComplete = false;
    }
    
    /**
     * Start the manufacturing process
     */
    public void startManufacture() {
        // Delegate to the current state to handle the process
        if (currentState != null) {
            currentState.handle(this);
        }
    }
    
    /**
     * Change the current state
     * @param newState The new state to transition to
     */
    public void setState(ManufacturingState newState) {
        this.currentState = newState;
        
        // If this is a terminal state, mark processing as complete
        if (newState != null && (newState.getStateName().equals("Completed") || 
                                newState.getStateName().equals("Failed"))) {
            processingComplete = true;
        }
    }
    
    /**
     * Set failure reason when manufacturing fails
     * @param reason The reason for failure
     */
    public void setFailureReason(String reason) {
        this.failureReason = reason;
    }
    
    /**
     * Get the product being manufactured
     * @return The product
     */
    public Product getProduct() {
        return product;
    }
    
    /**
     * Get the current state
     * @return The current state
     */
    public ManufacturingState getCurrentState() {
        return currentState;
    }
    
    /**
     * Get the failure reason if any
     * @return The failure reason or null if not failed
     */
    public String getFailureReason() {
        return failureReason;
    }
    
    /**
     * Check if the manufacturing process is complete
     * @return true if complete, false otherwise
     */
    public boolean isComplete() {
        return processingComplete;
    }
} 
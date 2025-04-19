package main.java.com.manufacturing.controller;

import main.java.com.manufacturing.io.CSVReader;
import main.java.com.manufacturing.model.Component;
import main.java.com.manufacturing.model.Product;
import main.java.com.manufacturing.process.ManufacturingProcess;
import main.java.com.manufacturing.report.Report;
import main.java.com.manufacturing.state.CompletedState;
import main.java.com.manufacturing.state.FailedState;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ManufacturingController - Orchestrates the manufacturing process
 */
public class ManufacturingController {
    
    private CSVReader csvReader;
    private Report report;
    private List<Component> components;
    private Map<String, Component> componentMap;
    private Map<Product, Integer> products;
    private Random random = new Random();
    
    /**
     * Create a new manufacturing controller
     */
    public ManufacturingController() {
        this.csvReader = new CSVReader();
        this.report = new Report();
    }
    
    /**
     * Load data from CSV files
     * @param componentsFilePath Path to components.csv file
     * @param productsFilePath Path to products.csv file
     * @throws IOException If there is an issue reading the files
     */
    public void loadData(String componentsFilePath, String productsFilePath) throws IOException {
        // Parse components
        components = csvReader.parseComponents(componentsFilePath);
        
        // Create component name to component map
        componentMap = csvReader.createComponentMap(components);
        
        // Parse products and link with components
        products = csvReader.parseProducts(productsFilePath, componentMap);
        
        System.out.println("Loaded " + components.size() + " components and " + products.size() + " products.");
    }
    
    /**
     * Run the manufacturing process for all products
     */
    public void runManufacturing() {
        System.out.println("Starting manufacturing process...");
        
        // Process each product and its manufacturing quantity
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int targetQuantity = entry.getValue();
            
            System.out.println("Attempting to manufacture " + targetQuantity + " units of " + product.getName());
            
            int successCount = 0;
            int failedSystemErrorCount = 0;
            int failedDamagedCount = 0;
            int failedStockCount = 0;
            
            // Try to manufacture each unit until we run out of components or reach target
            while (successCount + failedSystemErrorCount + failedDamagedCount + failedStockCount < targetQuantity) {
                ManufacturingProcess process = new ManufacturingProcess(product);
                process.startManufacture();
                
                // Record the result based on the final state
                if (process.getCurrentState() instanceof CompletedState) {
                    successCount++;
                    report.recordSuccess(product);
                    System.out.println("  - Successfully manufactured 1 unit of " + product.getName() +
                                       " (" + process.getCurrentState().getStateName() + " state)");
                } else if (process.getCurrentState() instanceof FailedState) {
                    String reason = process.getFailureReason();
                    report.recordFailure(reason);
                    
                    if ("Stock Shortage".equals(reason)) {
                        failedStockCount++;
                        // If we're running out of stock, no point in continuing for this product
                        System.out.println("   - Stock shortage detected, stopping production");
                        break;
                    } else if ("System Error".equals(reason)) {
                        failedSystemErrorCount++;
                        System.out.println("  - Manufacturing failed due to system error");
                    } else if ("Damaged Component".equals(reason)) {
                        failedDamagedCount++;
                        System.out.println("  - Manufacturing failed due to damaged component");
                    }
                } else {
                    System.out.println("  - Unknown state detected: " + 
                                      (process.getCurrentState() != null ? 
                                       process.getCurrentState().getStateName() : "null"));
                }
            }
            
            System.out.println("   - Summary for " + product.getName() + ":");
            System.out.println("   - Manufactured: " + successCount + " units successfully");
            
            int totalFailures = failedStockCount + failedSystemErrorCount + failedDamagedCount;
            if (totalFailures > 0) {
                System.out.println("   - Failed: " + totalFailures + " units");
                if (failedStockCount > 0) System.out.println("     * Stock shortage: " + failedStockCount);
                if (failedSystemErrorCount > 0) System.out.println("     * System errors: " + failedSystemErrorCount);
                if (failedDamagedCount > 0) System.out.println("     * Damaged components: " + failedDamagedCount);
            }
        }
        
        System.out.println("Manufacturing complete.");
    }
    
    /**
     * Generate and print the manufacturing report
     */
    public void generateReport() {
        System.out.println("\n" + report.generateReport());
    }
    
    /**
     * Run the entire manufacturing system
     * @param componentsFilePath Path to components.csv file
     * @param productsFilePath Path to products.csv file
     */
    public void run(String componentsFilePath, String productsFilePath) {
        try {
            // Load data
            loadData(componentsFilePath, productsFilePath);
            
            // Run manufacturing process
            runManufacturing();
            
            // Generate report
            generateReport();
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Getters for testing and analysis
    
    public List<Component> getComponents() {
        return components;
    }
    
    public Map<Product, Integer> getProducts() {
        return products;
    }
    
    public Report getReport() {
        return report;
    }
} 
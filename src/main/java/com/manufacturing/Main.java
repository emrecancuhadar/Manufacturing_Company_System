package main.java.com.manufacturing;

import main.java.com.manufacturing.controller.ManufacturingController;

/**
 * Main class for the Manufacturing Company System
 */
public class Main {
    
    public static void main(String[] args) {
        // Default file paths
        String componentsFilePath = "data/components.csv";
        String productsFilePath = "data/products.csv";
        
        // Allow overriding file paths from command line arguments
        if (args.length >= 2) {
            componentsFilePath = args[0];
            productsFilePath = args[1];
        }
        
        // Create and run the manufacturing controller
        ManufacturingController controller = new ManufacturingController();
        controller.run(componentsFilePath, productsFilePath);
    }
} 
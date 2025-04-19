package main.java.com.manufacturing.io;

import main.java.com.manufacturing.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSVReader - Utility for reading component and product data from CSV files
 */
public class CSVReader {
    
    /**
     * Parse components from CSV file
     * @param filePath Path to the components.csv file
     * @return List of components
     * @throws IOException If there is an issue reading the file
     */
    public List<Component> parseComponents(String filePath) throws IOException {
        List<Component> components = new ArrayList<>();
        int idCounter = 1;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            String line = reader.readLine();
            
            // Read component data
            while ((line = reader.readLine()) != null) {
                // Split by semicolon
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    String name = parts[0].trim();
                    
                    // Parse cost and weight - replace comma with dot for decimal values
                    String costStr = parts[1].trim().replace(",", ".");
                    String weightStr = parts[2].trim().replace(",", ".");
                    double unitCost = Double.parseDouble(costStr);
                    double unitWeight = Double.parseDouble(weightStr);
                    
                    String type = parts[3].trim();
                    
                    // Parse stock quantity - remove any non-numeric characters
                    String stockStr = parts[4].trim().split(" ")[0]; // Remove units like "m²"
                    stockStr = stockStr.replace(",", "."); // Handle decimal commas
                    int stockQuantity = (int) Double.parseDouble(stockStr);
                    
                    Component component;
                    
                    // Create appropriate component type
                    switch (type) {
                        case "Raw Material":
                            component = new RawMaterial(idCounter++, name, unitCost, unitWeight, stockQuantity);
                            break;
                        case "Paint":
                            component = new Paint(idCounter++, name, unitCost, unitWeight, stockQuantity);
                            break;
                        case "Hardware":
                            component = new Hardware(idCounter++, name, unitCost, unitWeight, stockQuantity);
                            break;
                        default:
                            // Skip unknown component types
                            continue;
                    }
                    
                    components.add(component);
                }
            }
        }
        
        return components;
    }
    
    /**
     * Parse products from CSV file and link with components
     * @param filePath Path to the products.csv file
     * @param componentMap Map of component name to Component object
     * @return Map of product to manufacturing quantity
     * @throws IOException If there is an issue reading the file
     */
    public Map<Product, Integer> parseProducts(String filePath, Map<String, Component> componentMap) throws IOException {
        Map<Product, Integer> products = new HashMap<>();
        int idCounter = 1000; // Start product IDs from 1000 to distinguish from components
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read header to determine component columns
            String header = reader.readLine();
            if (header == null) {
                return products;
            }
            
            // Split by semicolon and get component names from header
            String[] headers = header.split(";");
            if (headers.length < 2) {
                throw new IOException("Invalid product CSV format");
            }
            
            // Read product data
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= headers.length) {
                    String productName = parts[0].trim();
                    
                    // Create new product (initially with no stock)
                    Product product = new Product(idCounter++, productName, 0, 0, 0);
                    
                    // Add components to product
                    // The first column is the product name, and the last column is quantity
                    // Component columns start from index 1 and end at length-2
                    for (int i = 1; i < parts.length - 1; i++) {
                        String componentName = headers[i].trim();
                        
                        // Parse component quantity - replace comma with dot for decimal values
                        String quantityStr = parts[i].trim().replace(",", ".");
                        
                        // Skip if empty or zero
                        if (quantityStr.isEmpty() || quantityStr.equals("0")) {
                            continue;
                        }
                        
                        // Parse as double
                        double quantity = Double.parseDouble(quantityStr);
                        
                        if (quantity > 0 && componentMap.containsKey(componentName)) {
                            Component component = componentMap.get(componentName);
                            product.addComponent(component, quantity);
                        }
                    }
                    
                    // Last column is the quantity to manufacture
                    String manufacturingQuantityStr = parts[parts.length - 1].trim();
                    int manufacturingQuantity = Integer.parseInt(manufacturingQuantityStr);
                    
                    if (manufacturingQuantity > 0) {
                        products.put(product, manufacturingQuantity);
                    }
                }
            }
        }
        
        return products;
    }
    
    /**
     * Helper method to create a map of component names to Component objects
     * @param components List of components
     * @return Map of component name to Component
     */
    public Map<String, Component> createComponentMap(List<Component> components) {
        Map<String, Component> componentMap = new HashMap<>();
        
        for (Component component : components) {
            componentMap.put(component.getName(), component);
        }
        
        return componentMap;
    }
} 
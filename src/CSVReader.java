import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class CSVReader {
    public static List<Stock> parseComponents(String filePath) {
        List<Stock> stocks = new ArrayList<>();
        
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("Error: Invalid file path for components");
            return stocks;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                
                try {
                    String[] data = line.split(",");
                    
                    if (data.length < 5) {
                        System.err.println("Warning: Skipping line " + lineNumber + " due to insufficient data");
                        continue;
                    }

                    String componentName = data[0];
                    if (componentName == null || componentName.trim().isEmpty()) {
                        System.err.println("Warning: Skipping line " + lineNumber + " due to missing component name");
                        continue;
                    }
                    
                    int unitCost = Integer.parseInt(data[1].trim());
                    int unitWeight = Integer.parseInt(data[2].trim());
                    String type = data[3];
                    int stockQuantity = Integer.parseInt(data[4].trim());

                    ComponentNode component = new ComponentNode(componentName, unitCost, unitWeight, type);
                    stocks.add(new Stock(component, stockQuantity));
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Skipping line " + lineNumber + " due to invalid number format: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Warning: Error processing line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Component file not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error: IOException while reading component file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: Unexpected error while parsing components: " + e.getMessage());
        }
        
        return stocks;
    }

    public static List<Order> parseProducts(String filePath) {
        List<Order> orders = new ArrayList<>();
        
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("Error: Invalid file path for products");
            return orders;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] componentNames = null;
            int lineNumber = 0;
            
            // Read the header line first
            if ((line = reader.readLine()) != null) {
                lineNumber++;
                componentNames = line.split(";");
                if (componentNames.length < 2) {
                    System.err.println("Error: Invalid header format in products file");
                    return orders;
                }
            } else {
                System.err.println("Error: Empty products file");
                return orders;
            }
            
            // Read product data lines
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                
                try {
                    String[] data = line.split(";");
                    
                    if (data.length < 2 || data.length != componentNames.length) {
                        System.err.println("Warning: Skipping line " + lineNumber + " due to incorrect number of fields");
                        continue;
                    }
                    
                    String productName = data[0];
                    if (productName == null || productName.trim().isEmpty()) {
                        System.err.println("Warning: Skipping line " + lineNumber + " due to missing product name");
                        continue;
                    }
                    
                    Blueprint blueprint = new Blueprint(productName);
                    
                    // Last column is the order quantity
                    int orderQuantity;
                    try {
                        orderQuantity = Integer.parseInt(data[data.length - 1].trim());
                        if (orderQuantity <= 0) {
                            System.err.println("Warning: Skipping line " + lineNumber + " due to invalid order quantity: " + orderQuantity);
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Skipping line " + lineNumber + " due to invalid order quantity format");
                        continue;
                    }
                    
                    // Process each component (skip first column which is product name and last which is quantity)
                    for (int i = 1; i < data.length - 1; i++) {
                        if (i >= componentNames.length) {
                            break; // Prevent index out of bounds
                        }
                        
                        String componentValue = data[i];
                        if (componentValue == null || componentValue.trim().isEmpty()) {
                            continue; // Skip empty component values
                        }
                        
                        // Skip components that have quantity 0
                        if (!componentValue.equals("0")) {
                            try {
                                // Replace comma with dot for decimal parsing
                                double doubleQuantity = Double.parseDouble(componentValue.replace(',', '.'));
                                if (doubleQuantity > 0) {
                                    String componentName = componentNames[i];
                                    if (componentName != null && !componentName.trim().isEmpty()) {
                                        // Convert to integer for Blueprint.addComponent
                                        int quantity = (int)Math.ceil(doubleQuantity);
                                        blueprint.addComponent(componentName, quantity);
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Warning: Invalid component quantity format at line " + lineNumber + 
                                        ", component " + componentNames[i] + ": " + componentValue);
                            }
                        }
                    }
                    
                    Order order = new Order(blueprint, orderQuantity);
                    orders.add(order);
                } catch (Exception e) {
                    System.err.println("Warning: Error processing line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Products file not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error: IOException while reading products file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: Unexpected error while parsing products: " + e.getMessage());
        }
        
        return orders;
    }
} 
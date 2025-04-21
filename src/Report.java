import java.util.ArrayList;
import java.util.List;

public class Report {
    private List<Product> successfulProducts;
    private int systemErrorCount;
    private int damagedCount;
    private int stockShortageCount;

    public Report() {
        this.successfulProducts = new ArrayList<>();
        this.systemErrorCount = 0;
        this.damagedCount = 0;
        this.stockShortageCount = 0;
    }

    public Report(Report other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from null Report");
        }
        this.successfulProducts = new ArrayList<>(other.successfulProducts);
        this.systemErrorCount = other.systemErrorCount;
        this.damagedCount = other.damagedCount;
        this.stockShortageCount = other.stockShortageCount;
    }
    
    /**
     * Adds the given product to the succesful products
     * @param product - the product object that has been succesfully manufactured
     */
    public void recordSuccess(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot record success for null product");
        }
        successfulProducts.add(product);
    }
    
    /**
     * Increases the counter for the given failure reason
     * @param reason - the reason for failure
     */
    public void recordFailure(FailureReason reason) {
        if (reason == null) {
            throw new IllegalArgumentException("Failure reason cannot be null");
        }
        
        switch (reason) {
            case SYSTEM_ERROR:
                systemErrorCount++;
                break;
            case DAMAGED_COMPONENT:
                damagedCount++;
                break;
            case STOCK_SHORTAGE:
                stockShortageCount++;
                break;
            default:
                throw new IllegalArgumentException("Unknown failure reason: " + reason);
        }
    }
    
    /**
     * Validates the report data before generating the report.
     * @return true if the report data is valid, false otherwise
     */
    public boolean validateReport() {
        // Check if failure counts are non-negative
        if (systemErrorCount < 0 || damagedCount < 0 || stockShortageCount < 0) {
            System.err.println("Invalid failure counts in report");
            return false;
        }
        
        // Check for null products in the successful products list
        for (int i = 0; i < successfulProducts.size(); i++) {
            if (successfulProducts.get(i) == null) {
                System.err.println("Null product found at index " + i + " in successful products list");
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Generates the final report for the manufacturing process
     */
    public void generateReport() {
        // Validate report data before generating
        if (!validateReport()) {
            System.err.println("Cannot generate report due to invalid data");
            return;
        }
        
        System.out.println("\n===== MANUFACTURING REPORT =====");
        
        System.out.println("\nManufactured Products:");
        double totalCost = 0;
        double totalWeight = 0;
        
        if (successfulProducts.isEmpty()) {
            System.out.println("No products were successfully manufactured.");
        } else {
            System.out.println("\nIndividual Products:");
            System.out.println("-------------------------------");
            System.out.printf("%-5s %-20s %-15s %-15s\n", 
                             "ID", "Product Type", "Cost ($)", "Weight (kg)");
            System.out.println("-------------------------------");
            
            int productId = 1;
            for (Product product : successfulProducts) {
                try {
                    double cost = product.calculateCost();
                    double weight = product.calculateWeight();
                    
                    // Validate cost and weight
                    if (cost < 0 || weight < 0) {
                        System.out.printf("%-5d %-20s %-15s %-15s\n", 
                                         productId++, 
                                         product.getName(), 
                                         "INVALID", "INVALID");
                        System.out.println("Invalid product cost or weight (negative values)");
                        continue;
                    }
                    
                    totalCost += cost;
                    totalWeight += weight;
                    
                    System.out.printf("%-5d %-20s %-15.2f %-15.2f\n", 
                                     productId++, 
                                     product.getName(), 
                                     cost,
                                     weight);
                } catch (InvalidComponentNodeException e) {
                    System.out.printf("%-5d %-20s %-15s %-15s\n", 
                                     productId++, 
                                     product.getName(), 
                                     "ERROR", "ERROR");
                    System.out.println("Error calculating product cost/weight: " + e.getMessage());
                }
            }
            
            System.out.println("-------------------------------");
            System.out.println("\nTotal Manufacturing Summary:");
            System.out.println("- Total Products Manufactured: " + successfulProducts.size());
            System.out.println("- Total Manufacturing Cost: $" + String.format("%.2f", totalCost));
            System.out.println("- Total Product Weight: " + String.format("%.2f", totalWeight) + " kg");
        }
        
        System.out.println("\nManufacturing Failures:");
        System.out.println("- System Errors: " + systemErrorCount);
        System.out.println("- Damaged Components: " + damagedCount);
        System.out.println("- Stock Shortages: " + stockShortageCount);
        
        int totalFailures = systemErrorCount + damagedCount + stockShortageCount;
        System.out.println("\nTotal Failed: " + totalFailures);
        
        int totalAttempted = successfulProducts.size() + totalFailures;
        if (totalAttempted > 0) {
            double successRate = (double) successfulProducts.size() / totalAttempted * 100;
            System.out.println("Success Rate: " + String.format("%.2f", successRate) + "%");
        }
        
        System.out.println("\n=================================");
    }
} 
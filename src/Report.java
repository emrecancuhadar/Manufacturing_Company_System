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
        this.successfulProducts = new ArrayList<>(other.successfulProducts);
        this.systemErrorCount = other.systemErrorCount;
        this.damagedCount = other.damagedCount;
        this.stockShortageCount = other.stockShortageCount;
    }
    
    public void recordSuccess(Product product) {
        successfulProducts.add(product);
    }
    
    public void recordFailure(FailureReason reason) {
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
        }
    }
    
    public void generateReport() {
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
                    
                    totalCost += cost;
                    totalWeight += weight;
                    
                    System.out.printf("%-5d %-20s %-15.2f %-15.2f\n", 
                                     productId++, 
                                     product.getClass().getSimpleName(), 
                                     cost,
                                     weight);
                } catch (InvalidComponentNodeException e) {
                    System.out.printf("%-5d %-20s %-15s %-15s\n", 
                                     productId++, 
                                     product.getClass().getSimpleName(), 
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
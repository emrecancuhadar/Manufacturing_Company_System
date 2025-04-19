package main.java.com.manufacturing.report;

import main.java.com.manufacturing.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Report class - Generates summary reports for the manufacturing process
 */
public class Report {
    
    private List<Product> successfulProducts;
    private Map<String, Integer> failureReasons;
    
    public Report() {
        this.successfulProducts = new ArrayList<>();
        this.failureReasons = new HashMap<>();
    }
    
    /**
     * Record a successful product manufacturing
     * @param product The product that was successfully manufactured
     */
    public void recordSuccess(Product product) {
        if (product != null) {
            successfulProducts.add(product);
        }
    }
    
    /**
     * Record a manufacturing failure with the given reason
     * @param reason The reason for failure
     */
    public void recordFailure(String reason) {
        if (reason != null && !reason.isEmpty()) {
            failureReasons.put(reason, failureReasons.getOrDefault(reason, 0) + 1);
        }
    }
    
    /**
     * Generate a formatted report of manufacturing outcomes
     * @return A string containing the formatted report
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== MANUFACTURING REPORT ===\n\n");
        
        // Product success report
        report.append("SUCCESSFUL PRODUCTS:\n");
        report.append("---------------------------------\n");
        
        if (successfulProducts.isEmpty()) {
            report.append("No products were successfully manufactured.\n");
        } else {
            // Count each product type
            Map<String, Integer> productCounts = new HashMap<>();
            Map<String, Product> productMap = new HashMap<>();
            
            for (Product product : successfulProducts) {
                String name = product.getName();
                productCounts.put(name, productCounts.getOrDefault(name, 0) + 1);
                productMap.put(name, product);
            }
            
            // Report on each product type
            for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
                String productName = entry.getKey();
                int count = entry.getValue();
                Product product = productMap.get(productName);
                
                report.append(String.format("%-15s: %d units\n", productName, count));
                report.append(String.format("  - Unit Cost   : %.2f TL\n", product.calculateCost()));
                report.append(String.format("  - Unit Weight : %.2f kg\n", product.calculateWeight()));
                report.append(String.format("  - Total Cost  : %.2f TL\n", product.calculateCost() * count));
                report.append("---------------------------------\n");
            }
        }
        
        // Failure report
        report.append("\nFAILURE SUMMARY:\n");
        report.append("---------------------------------\n");
        
        if (failureReasons.isEmpty()) {
            report.append("No manufacturing failures occurred.\n");
        } else {
            int totalFailures = 0;
            for (Map.Entry<String, Integer> entry : failureReasons.entrySet()) {
                String reason = entry.getKey();
                int count = entry.getValue();
                totalFailures += count;
                
                report.append(String.format("%-20s: %d\n", reason, count));
            }
            report.append("---------------------------------\n");
            report.append(String.format("Total Failures    : %d\n", totalFailures));
        }
        
        // Overall statistics
        report.append("\nOVERALL STATISTICS:\n");
        report.append("---------------------------------\n");
        int totalSuccess = successfulProducts.size();
        int totalFailures = failureReasons.values().stream().mapToInt(Integer::intValue).sum();
        int total = totalSuccess + totalFailures;
        
        if (total > 0) {
            double successRate = (double) totalSuccess / total * 100;
            report.append(String.format("Total Attempts    : %d\n", total));
            report.append(String.format("Success Rate      : %.1f%%\n", successRate));
        } else {
            report.append("No manufacturing attempts were made.\n");
        }
        
        return report.toString();
    }
    
    /**
     * Get the list of successfully manufactured products
     * @return List of products
     */
    public List<Product> getSuccessfulProducts() {
        return new ArrayList<>(successfulProducts);
    }
    
    /**
     * Get a map of failure reasons and their counts
     * @return Map of failure reasons to counts
     */
    public Map<String, Integer> getFailureReasons() {
        return new HashMap<>(failureReasons);
    }
} 
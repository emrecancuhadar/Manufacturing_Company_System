import java.util.Map;

public class Report {
    private Map<Product, Integer> successCounts;
    private int productErrorCount;
    private int damagedCount;
    private int stockShortageCount;
    
    public void recordSuccess(Product product) {
        // To be implemented
    }
    
    public void recordFailure(FailureReason reason) {
        // To be implemented
    }
    
    public void generateReport() {
        // To be implemented
    }
} 
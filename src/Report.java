import java.util.Map;

public class Report {
    private Map<Product, Integer> successCounts;
    private int systemErrorCount;
    private int damagedCount;
    private int stockShortageCount;
    
    public void recordSuccess(Product product) {
        // To be implemented
    }
    
    public void recordFailureReason(FailureReason reason) {
        // To be implemented
    }
    
    public void generateReport() {
        // To be implemented
    }
} 
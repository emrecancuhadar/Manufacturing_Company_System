import java.util.Map;

public class Blueprint {
    private Map<String, Integer> componentScheme;
    
    public Blueprint() {
        this.componentScheme = new HashMap<>();
    }
    
    public Map<String, Integer> getComponentScheme() {
        return componentScheme;
    }
    
    public void addComponent(String componentName, int quantity) {
        componentScheme.put(componentName, quantity);
    }
} 
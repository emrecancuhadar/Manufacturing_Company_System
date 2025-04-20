import java.util.HashMap;
import java.util.Map;

public class Blueprint {
    private Map<String, Integer> componentScheme;
    private String name;
    
    public Blueprint(String name) {
        this.componentScheme = new HashMap<>();
        this.name = name;
    }
    
    public Map<String, Integer> getComponentScheme() {
        return componentScheme;
    }
    
    public void addComponent(String componentName, int quantity) {
        componentScheme.put(componentName, quantity);
    }

    public String getName() {
        return name;
    }
    
} 
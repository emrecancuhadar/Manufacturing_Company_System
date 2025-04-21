import java.util.HashMap;
import java.util.Map;

public class Blueprint {
    private Map<String, Double> componentScheme;
    private String name;

    public Blueprint() {
        this.componentScheme = new HashMap<>();
        this.name = "";
    }
    
    public Blueprint(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Blueprint name cannot be null or empty");
        }
        this.componentScheme = new HashMap<>();
        this.name = name;
    }

    public Blueprint(Blueprint blueprint) {
        if (blueprint == null) {
            throw new IllegalArgumentException("Cannot copy from null Blueprint");
        }
        this.componentScheme = new HashMap<>(blueprint.componentScheme);
        this.name = blueprint.name;
    }
    
    public Map<String, Double> getComponentScheme() {
        return componentScheme;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Adds a new component to the blueprint. This function is used to
     * create a blueprint using the csv file.
     * @param componentName - the name of the component to add
     * @param quantity - the quantity of the component 
     */
    public void addComponent(String componentName, double quantity) {
        if (componentName == null || componentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Component name cannot be null or empty");
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Component quantity must be positive: " + quantity);
        }
        
        componentScheme.put(componentName, quantity);
    }
    
    /**
     * Validates that the blueprint has all required information
     * @return true if the blueprint is valid, false otherwise
     */
    public boolean isValid() {
        // Check if name is valid
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        // Check if at least one component is defined
        if (componentScheme == null || componentScheme.isEmpty()) {
            return false;
        }
        
        // Check all components have valid quantities
        for (Map.Entry<String, Double> entry : componentScheme.entrySet()) {
            String componentName = entry.getKey();
            Double quantity = entry.getValue();
            
            if (componentName == null || componentName.trim().isEmpty()) {
                return false;
            }
            
            if (quantity == null || quantity <= 0) {
                return false;
            }
        }
        
        return true;
    }
} 
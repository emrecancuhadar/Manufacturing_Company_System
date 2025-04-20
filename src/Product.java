import java.util.ArrayList;
import java.util.List;

public class Product {
    private List<Component> components;
    
    
    // Default constructor
    public Product() 
    {
        this.components = new ArrayList<>();
    }
    // Full constructor
    public Product(List<Component> components) 
    {
        this.components = components;
    }
    // Copy constructor
    public Product(Product product) 
    {
        this.components = new ArrayList<>(product.components);
    }
    
    // Getters and Setters
    public void setComponents(List<Component> components) 
    {
        this.components = components;
    }
    public List<Component> getComponents() 
    {
        return components;
    }

    /**
     * @return the sum of all component costs
     * @throws InvalidComponentNodeException if any component is invalid
     */
    public double calculateCost() throws InvalidComponentNodeException 
    {
        if (components == null) 
        {
            throw new InvalidComponentNodeException("Cannot calculate cost: components list is null.");
        }
        double total = 0;
        for (Component c : components) 
        {
            total += c.calculateCost();  // may throw InvalidComponentNodeException
        }
        return total;
    }

    /**
     * @return the sum of all component weights
     * @throws InvalidComponentNodeException if any component is invalid
     */
    public double calculateWeight() throws InvalidComponentNodeException {
        if (components == null) 
        {
            throw new InvalidComponentNodeException("Cannot calculate weight: components list is null.");
        }
        double total = 0;
        for (Component c : components) 
        {
            total += c.calculateWeight();  // may throw InvalidComponentNodeException
        }
        return total;
    }
} 
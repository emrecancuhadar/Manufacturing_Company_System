public abstract class Component extends ComponentNode {

    private ComponentEntry componentEntry;

    // Default constructor
    public Component() 
    {
        super();
        this.componentEntry = new ComponentEntry();
    }
    // Full constructor
    public Component(String name, double unitCost, double unitWeight, int quantity) 
    {
        super(name, unitCost, unitWeight);
        this.componentEntry = new ComponentEntry(quantity);
    }
    // Copy constructor
    public Component(Component component) 
    {
        super(component);
        this.componentEntry = new ComponentEntry(component.componentEntry);
    }
    // Getters and Setters
    public void setComponentEntry(ComponentEntry componentEntry) 
    {
        this.componentEntry = componentEntry;
    }
    public ComponentEntry getComponentEntry() 
    {
        return componentEntry;
    }
    public void setQuantity(int quantity) 
    {
        this.componentEntry.setQuantity(quantity);
    }
    public int getQuantity() 
    {
        return componentEntry.getQuantity();
    }
    
public double calculateCost() throws InvalidComponentNodeException
{
    if (!getIsComponentNodeValid()) 
    {
        throw new InvalidComponentNodeException(
            "Cannot calculate cost: component node is invalid."
        );
    }
    return getUnitCost() * getQuantity();
}

public double calculateWeight() throws InvalidComponentNodeException 
{
    if (!getIsComponentNodeValid()) 
    {
        throw new InvalidComponentNodeException(
            "Cannot calculate weight: component node is invalid."
        );
    }
    return getUnitWeight() * getQuantity();
}

} 
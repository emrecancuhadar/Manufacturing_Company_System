/**
 * 
 */
public class Component extends ComponentNode {

    private ComponentEntry componentEntry;
    private Type type;
    private QuantityUnit quantityUnit;
    
    // Default constructor
    public Component() 
    {
        super();
        this.componentEntry = new ComponentEntry();
        
    }
    // Full constructor
    public Component(String name, double unitCost, double unitWeight, double quantity, Type type, QuantityUnit quantityUnit) 
    {
        super(name, unitCost, unitWeight);
        this.type = type;
        this.quantityUnit = quantityUnit;
        this.componentEntry = new ComponentEntry(quantity);
    }
    // Copy constructor
    public Component(Component component) 
    {
        super(component);
        this.type = component.type;
        this.quantityUnit = component.quantityUnit;
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

    public void setQuantity(double quantity) 
    {
        this.componentEntry.setQuantity(quantity);
    }

    public void setType(Type type) 
    {
        this.type = type;
    }

    public void setQuantityUnit(QuantityUnit quantityUnit) 
    {
        this.quantityUnit = quantityUnit;
    }

    public double getQuantity() 
    {
        return componentEntry.getQuantity();
    }

    public Type getType() 
    {
        return type;
    }

    public QuantityUnit getQuantityUnit() 
    {
        return quantityUnit;
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
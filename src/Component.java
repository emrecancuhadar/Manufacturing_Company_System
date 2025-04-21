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
    public Component(String name,
                     double unitCost,
                     double unitWeight,
                     double quantity,
                     Type type,
                     QuantityUnit quantityUnit) {
        super(name, unitCost, unitWeight);
        this.type = type;
        this.quantityUnit = quantityUnit;
        this.componentEntry = new ComponentEntry(quantity);
    }
    // Copy constructor
    public Component(Component other) 
    {
        super(other);
        this.type = other.type;
        this.quantityUnit = other.quantityUnit;
        this.componentEntry = new ComponentEntry(other.componentEntry);
    }

    // Getters & setters
    public ComponentEntry getComponentEntry() 
    {
        return componentEntry;
    }
    public void setComponentEntry(ComponentEntry componentEntry) 
    {
        this.componentEntry = componentEntry;
    }
    public double getQuantity() 
    {
        return componentEntry.getQuantity();
    }
    public void setQuantity(double quantity) 
    {
        this.componentEntry.setQuantity(quantity);
    }
    public Type getType() 
    {
        return type;
    }
    public void setType(Type type) 
    {
        this.type = type;
    }
    public QuantityUnit getQuantityUnit() 
    {
        return quantityUnit;
    }
    public void setQuantityUnit(QuantityUnit quantityUnit) 
    {
        this.quantityUnit = quantityUnit;
    }

    /**
     * Single private guard for all operations that require a valid node.
     */
    private void ensureValid() throws InvalidComponentNodeException 
    {
        if (!getIsComponentNodeValid()) {
            throw new InvalidComponentNodeException(
                "Operation not allowed: component node is invalid ("
                + getName() + ")"
            );
        }
    }

    @Override
    public double calculateCost() throws InvalidComponentNodeException 
    {
        ensureValid();
        return getUnitCost() * getQuantity();
    }

    @Override
    public double calculateWeight() throws InvalidComponentNodeException 
    {
        ensureValid();
        return getUnitWeight() * getQuantity();
    }
}

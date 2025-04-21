package component_composite;

public class ComponentEntry {

    private double quantity;
    private boolean isComponentEntryValid = false;
    
    // Default constructor
    public ComponentEntry() 
    {
        this.quantity = 0;
    }
    // Full constructor
    public ComponentEntry(double quantity) 
    {
        this.quantity = quantity;
        this.isComponentEntryValid = true;
    }
    // Copy constructor
    public ComponentEntry(ComponentEntry other)
    {
        this.quantity = other.quantity;
        this.isComponentEntryValid = other.isComponentEntryValid;
    }
    // Getters and Setters
    public void setQuantity(double quantity) 
    {
        this.quantity = quantity;
    }
    
    public void setIsComponentEntryValid(boolean isComponentEntryValid) 
    {
        this.isComponentEntryValid = isComponentEntryValid;
    }

    public double getQuantity() 
    {
        return quantity;
    }

    public boolean getIsComponentEntryValid() 
    {
        return isComponentEntryValid;
    }
}

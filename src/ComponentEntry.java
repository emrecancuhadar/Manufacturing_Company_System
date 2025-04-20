public class ComponentEntry {

    private int quantity;
    private boolean isComponentEntryValid = false;
    
    // Default constructor
    public ComponentEntry() 
    {
        this.quantity = 0;
    }
    // Full constructor
    public ComponentEntry(int quantity) 
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
    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }
    
    public void setIsComponentEntryValid(boolean isComponentEntryValid) 
    {
        this.isComponentEntryValid = isComponentEntryValid;
    }

    public int getQuantity() 
    {
        return quantity;
    }

    public boolean getIsComponentEntryValid() 
    {
        return isComponentEntryValid;
    }
}

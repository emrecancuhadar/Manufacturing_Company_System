public class ComponentEntry {

    private int quantity;
    
    // Default constructor
    public ComponentEntry() 
    {
        this.quantity = 0;
    }
    // Full constructor
    public ComponentEntry(int quantity) 
    {
        this.quantity = quantity;
    }
    // Copy constructor
    public ComponentEntry(ComponentEntry componentEntry) 
    {
        this.quantity = componentEntry.quantity;
    }
    // Getters and Setters
    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }
    public int getQuantity() 
    {
        return quantity;
    }

} 
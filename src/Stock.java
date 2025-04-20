public class Stock {
    private ComponentNode component;
    private int quantity;

    // Default constructor
    public Stock() {
        this.component = null;
        this.quantity = 0;
    }
    // Full constructor
    public Stock(ComponentNode component, int quantity) {
        this.component = component;
        this.quantity = quantity;
    }
    // Copy constructor
    public Stock(Stock stock) {
        this.component = stock.component;
        this.quantity = stock.quantity;
    }

    // Getters and Setters
    public void setComponent(ComponentNode component) 
    {
        this.component = component;
    }
    public ComponentNode getComponent() 
    {
        return component;
    }

    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }
    
    public int getQuantity() 
    {
        return quantity;
    }
    
} 
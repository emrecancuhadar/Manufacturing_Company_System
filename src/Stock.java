public class Stock {
    private Component component;
    private int quantity;

    // Default constructor
    public Stock() {
        this.component = null;
        this.quantity = 0;
    }
    // Full constructor
    public Stock(Component component, int quantity) {
        this.component = component;
        this.quantity = quantity;
    }
    // Copy constructor
    public Stock(Stock stock) {
        this.component = stock.component;
        this.quantity = stock.quantity;
    }

    // Getters and Setters
    public void setComponent(Component component) 
    {
        this.component = component;
    }
    public Component getComponent() 
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
public class ComponentEntry {

    private int quantity;
    private Type type;
    private QuantityUnit quantityUnit;
    private boolean isComponentEntryValid = false;
    
    // Default constructor
    public ComponentEntry() {
        this.quantity = 0;
    }
    // Full constructor
    public ComponentEntry(int quantity, Type type, QuantityUnit quantityUnit) {
        this.quantity = quantity;
        this.type = type;
        this.quantityUnit = quantityUnit;
        this.isComponentEntryValid = true;
    }
    // Copy constructor
    public ComponentEntry(ComponentEntry other) {
        this.quantity = other.quantity;
        this.type = other.type;
        this.quantityUnit = other.quantityUnit;
        this.isComponentEntryValid = other.isComponentEntryValid;
    }
    // Getters and Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void settype(Type type) {
        this.type = type;
    }
    public void setQuantityUnit(QuantityUnit quantityUnit) {
        this.quantityUnit = quantityUnit;
    }
    public void setIsComponentEntryValid(boolean isComponentEntryValid) {
        this.isComponentEntryValid = isComponentEntryValid;
    }

    public int getQuantity() {
        return quantity;
    }
    public Type geTtype() {
        return type;
    }
    public QuantityUnit getQuantityUnit() {
        return quantityUnit;
    }
    public boolean getIsComponentEntryValid() {
        return isComponentEntryValid;
    }
}

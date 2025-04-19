package main.java.com.manufacturing.model;

/**
 * Hardware class - represents hardware components like screws, nails, etc.
 * Leaf node in the Composite pattern
 */
public class Hardware extends Component {
    
    public Hardware(int id, String name, double unitCost, double unitWeight, int stockQuantity) {
        super(id, name, unitCost, unitWeight, stockQuantity);
    }
    
    @Override
    public double calculateCost() {
        // For a leaf node, just return the unit cost
        return getUnitCost();
    }
    
    @Override
    public double calculateWeight() {
        // For a leaf node, just return the unit weight
        return getUnitWeight();
    }
} 
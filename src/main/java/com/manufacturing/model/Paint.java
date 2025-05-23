package main.java.com.manufacturing.model;

/**
 * Paint class - represents paint used in products
 * Leaf node in the Composite pattern
 */
public class Paint extends Component {
    
    public Paint(int id, String name, double unitCost, double unitWeight, int stockQuantity) {
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
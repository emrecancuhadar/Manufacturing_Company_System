package component_composite;
import java.util.ArrayList;
import java.util.List;

import exception.InvalidComponentNodeException;

public class Product extends ComponentNode {
    private List<Component> components;

    // Default constructor
    public Product() {
        super();
        this.components = new ArrayList<>();
    }

    public Product(String productName) {
        super(productName, 0.0, 0.0);
        this.components = new ArrayList<>();
    }
    
    // Copy constructor
    public Product(Product other) {
        super(other);
        this.components = new ArrayList<>(other.components);
    }

    public List<Component> getComponents() {
        return components;
    }
    
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * Single private guard to verify that:
     * 1) The Product node itself is valid
     * 2) The components list is non-null
     */
    private void ensureReady() throws InvalidComponentNodeException {
        if (!getIsComponentNodeValid()) {
            throw new InvalidComponentNodeException(
                "Operation not allowed: product node is invalid (" + getName() + ")"
            );
        }

        if (components == null) {
            throw new InvalidComponentNodeException(
                "Operation not allowed: components list is null for product (" + getName() + ")"
            );
        }
    }

    /**
     * @return the sum of all component costs
     * @throws InvalidComponentNodeException if this product or any component is invalid
     */
    @Override
    public double calculateCost() throws InvalidComponentNodeException {
        ensureReady();
        double total = 0.0;
        for (Component c : components) {
            // c.calculateCost() itself checks c.isComponentNodeValid()
            total += c.calculateCost();
        }
        return total;
    }

    /**
     * @return the sum of all component weights
     * @throws InvalidComponentNodeException if this product or any component is invalid
     */
    @Override
    public double calculateWeight() throws InvalidComponentNodeException {
        ensureReady();
        double total = 0.0;
        for (Component c : components) {
            // c.calculateWeight() itself checks c.isComponentNodeValid()
            total += c.calculateWeight();
        }
        return total;
    }

    /**
     * Adds a new component—guarding both this product and the incoming component.
     */
    public void addComponent(Component component)
            throws InvalidComponentNodeException
    {
        ensureReady();
        if (!component.getIsComponentNodeValid()) {
            throw new InvalidComponentNodeException(
                "Cannot add component: component is invalid (" +
                component.getName() + ")"
            );
        }
        this.components.add(component);
    }
}

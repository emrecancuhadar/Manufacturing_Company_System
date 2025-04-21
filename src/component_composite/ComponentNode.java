package component_composite;
import exception.InvalidComponentNodeException;

public abstract class ComponentNode {
    private String name;
    private double unitCost;
    private double unitWeight;
    private boolean isComponentNodeValid = false;

    //Default constructor
    public ComponentNode() 
    {
        this.name = "";
        this.unitCost = 0.0;
        this.unitWeight = 0.0;
    }
    //Full constructor
    public ComponentNode(String name, double unitCost, double unitWeight) 
    {
        this.name = name;
        this.unitCost = unitCost;
        this.unitWeight = unitWeight;
        this.isComponentNodeValid = true;
    }
    //Copy constructor
    public ComponentNode(ComponentNode componentNode) 
    {
        this.name = componentNode.name;
        this.unitCost = componentNode.unitCost;
        this.unitWeight = componentNode.unitWeight;
        this.isComponentNodeValid = componentNode.isComponentNodeValid;
    }
    //Getters and Setters
    public void setName(String name) 
    {
        this.name = name;
    }
    public void setUnitCost(double unitCost) 
    {
        this.unitCost = unitCost;
    }
    public void setUnitWeight(double unitWeight) 
    {
        this.unitWeight = unitWeight;
    }
    public String getName() 
    {
        return name;
    }
    public double getUnitCost() 
    {
        return unitCost;
    }
    public double getUnitWeight() 
    {
        return unitWeight;
    }

    public boolean getIsComponentNodeValid() 
    {
        return isComponentNodeValid;
    }
    
    public double calculateCost() throws InvalidComponentNodeException 
    {
        return 0;
    }
    
    public double calculateWeight() throws InvalidComponentNodeException
    {
        return 0;
    }
} 
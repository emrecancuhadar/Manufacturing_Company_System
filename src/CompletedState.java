import java.util.Map;

public class CompletedState implements ManufacturingState {
    
    @Override
    public void handle(ManufacturingProcess process) throws ManufacturingProcessNotValid {
        Product product = new Product();
        Blueprint blueprint = process.getBlueprint();

        for (Map.Entry<String, Integer> entry: blueprint.getComponentScheme().entrySet()) {
            Component component = process.getStorage().getStock(entry.getKey()).getComponent();
            product.addComponent(new Component(component.getName(), component.getUnitCost(), component.getUnitWeight(), component));
            process.getStorage().reduceStock(entry.getKey(), entry.getValue());
        }
    }
} 
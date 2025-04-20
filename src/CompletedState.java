import java.util.Map;

public class CompletedState implements ManufacturingState {
    
    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValid, 
                                                InvalidComponentNodeException, 
                                                InvalidStorageException {
        Product product = new Product();

        for (Map.Entry<String, Integer> entry: blueprint.getComponentScheme().entrySet()) {
            Component component = storage.getStock(entry.getKey()).getComponent();
            product.addComponent(new Component(component.getName(), component.getUnitCost(), 
                                                component.getUnitWeight(), entry.getValue(), 
                                                component.getType(), 
                                                component.getQuantityUnit()));
            storage.reduceStockQuantity(entry.getKey(), entry.getValue());
        }

        report.recordSuccess(product);
    }
} 
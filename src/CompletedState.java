import java.util.Map;

public class CompletedState implements ManufacturingState {
    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValid, 
                                                InvalidComponentNodeException, 
                                                InvalidStorageException {
        // when the manufacturing succeeds, create a new product object and add the 
        // necessary amount of components to the product as children
        System.out.println("-> Transitioned to: CompletedState");

        Product product = new Product();
        product.setName(blueprint.getName());

        for (Map.Entry<String, Double> entry: blueprint.getComponentScheme().entrySet()) {
            Component component = storage.getStock(entry.getKey()).getComponent();
            product.addComponent(new Component(component.getName(), component.getUnitCost(), 
                                                component.getUnitWeight(), entry.getValue(), 
                                                component.getType(), 
                                                component.getQuantityUnit()));
        }

        // save the status of the new product to the report
        report.recordSuccess(product);
    }
} 
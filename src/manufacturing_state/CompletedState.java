package manufacturing_state;
import java.util.Map;

import exception.InvalidComponentNodeException;
import exception.InvalidStockException;
import exception.InvalidStorageException;
import exception.ManufacturingProcessNotValidException;
import storage.Storage;
import component_composite.Component;
import component_composite.Product;
import manufacturing.ManufacturingProcess;
import manufacturing.Report;
import order.Blueprint;

public class CompletedState implements ManufacturingState {
    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValidException, 
                                                InvalidComponentNodeException, 
                                                InvalidStorageException, InvalidStockException {
        // when the manufacturing succeeds, create a new product object and add the 
        // necessary amount of components to the product as children
        System.out.println("-> Transitioned to: CompletedState");

        Product product = new Product(blueprint.getName());

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
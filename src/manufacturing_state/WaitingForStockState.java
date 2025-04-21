package manufacturing_state;
import java.util.Map;

import enums.FailureReason;
import exception.InvalidComponentNodeException;
import exception.InvalidStockException;
import exception.InvalidStorageException;
import exception.ManufacturingProcessNotValidException;
import storage.Stock;
import storage.Storage;
import component_composite.Component;
import manufacturing.ManufacturingProcess;
import manufacturing.Report;
import order.Blueprint;

public class WaitingForStockState implements ManufacturingState {
    @Override
    public void handle(ManufacturingProcess process,
                       Storage storage, Blueprint blueprint,
                       Report report) throws ManufacturingProcessNotValidException,
                                              InvalidStorageException,
                                              InvalidComponentNodeException, InvalidStockException {
        System.out.println("============================================================");
        System.out.println("-> State: WaitingForStockState");
        System.out.println("Manufacturing product: " + blueprint.getName());
        System.out.println("Blueprint Components:");

        for (Map.Entry<String, Double> entry : blueprint.getComponentScheme().entrySet()) {
            String componentName = entry.getKey();
            double requiredQuantity = entry.getValue();

            Stock stockEntry = storage.getStock(componentName);
            Component component = stockEntry.getComponent();

            System.out.printf("- %s (%s), Quantity: %.2f, Unit Cost: %.2f%n",
                    componentName, component.getType().getLabel(),
                    requiredQuantity, component.getUnitCost());

            if (!storage.checkStock(componentName, requiredQuantity)) {
                double availableQuantity = stockEntry.getQuantity();
                System.out.printf("-> Missing component(s): %s (%s), Required: %.2f, Available: %.2f%n",
                        componentName, component.getType().getLabel(), requiredQuantity, availableQuantity);

                process.setCurrentState(new FailedState(FailureReason.STOCK_SHORTAGE));
                process.getCurrentState().handle(process, storage, blueprint, report);
                return; // Stop processing this product
            } else {
                // if there is enough stock reduce it no matter the end state (failure or success)
                storage.reduceStockQuantity(entry.getKey(), entry.getValue());
            }
        }

        System.out.println("-> All required stock is available.");
        process.setCurrentState(new InManufacturingState());
        process.getCurrentState().handle(process, storage, blueprint, report);
    }
}

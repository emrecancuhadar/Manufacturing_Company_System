import java.util.Map;

public class WaitingForStockState implements ManufacturingState {
    @Override
    public void handle(ManufacturingProcess process,
                       Storage storage, Blueprint blueprint,
                       Report report) throws ManufacturingProcessNotValid,
                                              InvalidStorageException,
                                              InvalidComponentNodeException {
        System.out.println("============================================================");
        System.out.println("-> State: WaitingForStockState");
        System.out.println("Manufacturing product: " + blueprint.getName());
        System.out.println("Blueprint Components:");

        for (Map.Entry<String, Integer> entry : blueprint.getComponentScheme().entrySet()) {
            String componentName = entry.getKey();
            int requiredQuantity = entry.getValue();

            Stock stockEntry = storage.getStock(componentName);
            Component component = stockEntry.getComponent();

            System.out.printf("- %s (%s), Quantity: %d, Unit Cost: %.2f%n",
                    componentName, component.getType().getLabel(),
                    requiredQuantity, component.getUnitCost());

            if (!storage.checkStock(componentName, requiredQuantity)) {
                int availableQuantity = stockEntry.getQuantity();
                System.out.printf("-> Missing component(s): %s (%s), Required: %d, Available: %d%n",
                        componentName, component.getType().getLabel(), requiredQuantity, availableQuantity);

                process.setCurrentState(new FailedState(FailureReason.STOCK_SHORTAGE));
                process.getCurrentState().handle(process, storage, blueprint, report);
                return; // Stop processing this product
            }
        }

        System.out.println("-> All required stock is available.");
        process.setCurrentState(new InManufacturingState());
        process.getCurrentState().handle(process, storage, blueprint, report);
    }
}

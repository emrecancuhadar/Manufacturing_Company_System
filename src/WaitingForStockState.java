import java.util.Map;

public class WaitingForStockState implements ManufacturingState {
    @Override
    public void handle(ManufacturingProcess process) throws ManufacturingProcessNotValid {
        Blueprint blueprint = process.getBlueprint();
        Storage storage = process.getStorage();

        for (Map.Entry<String, Integer> componentNode: blueprint.getComponentScheme().entrySet()) {
            if (!storage.checkStock(componentNode.getKey(), componentNode.getValue())) {
                process.setCurrentState(new FailedState(FailureReason.STOCK_SHORTAGE));
                process.getCurrentState().handle(process);
                break;
            }

            process.setCurrentState(new InManufacturingState());
            process.getCurrentState().handle(process);
        }
    }
} 
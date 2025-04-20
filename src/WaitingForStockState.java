import java.util.Map;

public class WaitingForStockState implements ManufacturingState {
    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValid, 
                                                            InvalidStorageException, 
                                                            InvalidComponentNodeException {
        
        for (Map.Entry<String, Integer> componentNode: blueprint.getComponentScheme().entrySet()) {
            if (!storage.checkStock(componentNode.getKey(), componentNode.getValue())) {
                process.setCurrentState(new FailedState(FailureReason.STOCK_SHORTAGE));
                process.getCurrentState().handle(process, storage, blueprint, report);
                break;
            }
        }
        
        process.setCurrentState(new InManufacturingState());
        process.getCurrentState().handle(process, storage, blueprint, report);
    }
} 
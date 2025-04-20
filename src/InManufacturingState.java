public class InManufacturingState implements ManufacturingState {

    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValid, InvalidComponentNodeException, InvalidStorageException {
        ManufacturingState randState = RandomStateGenerator.generateRandomState();
        process.setCurrentState(randState);
        process.getCurrentState().handle(process, storage, blueprint, report);
    }
} 
public class InManufacturingState implements ManufacturingState {

    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValid, InvalidComponentNodeException, InvalidStorageException {
        System.out.println("-> All required stock is available.");
        System.out.println("-> State: InManufacturingState");
        ManufacturingState randState = RandomStateGenerator.generateRandomState();
        process.setCurrentState(randState);
        process.getCurrentState().handle(process, storage, blueprint, report);
    }
} 
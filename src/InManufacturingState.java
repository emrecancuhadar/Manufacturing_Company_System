public class InManufacturingState implements ManufacturingState {

    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) {
        ManufacturingState randState = RandomStateGenerator.generateRandomState();
        process.setCurrentState(randState);
    }
} 
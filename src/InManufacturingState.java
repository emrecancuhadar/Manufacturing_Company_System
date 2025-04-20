public class InManufacturingState implements ManufacturingState {

    @Override
    public void handle(ManufacturingProcess process) {
        ManufacturingState randState = RandomStateGenerator.generateRandomState();
        process.setCurrentState(randState);
    }
} 
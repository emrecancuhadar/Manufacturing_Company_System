public interface ManufacturingState {
    void handle(ManufacturingProcess process) throws ManufacturingProcessNotValid;
} 
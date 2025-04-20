public class ManufacturingProcess {
    private ManufacturingState currentState;
    private boolean isValid;

    public ManufacturingProcess() {
        this.currentState = new WaitingForStockState();
        this.isValid = false;
    }
    
    public void process(Storage storage, Blueprint blueprint, Report report) {
        this.setIsValid(this.validate(storage, blueprint, report));

        try {
            if (!isValid) {
                throw new ManufacturingProcessNotValid();
            }

            this.currentState.handle(this, storage, blueprint, report);
        } catch (ManufacturingProcessNotValid | InvalidComponentNodeException | InvalidStorageException e) {
            e.printStackTrace();
        }
    }
    
    public void setCurrentState(ManufacturingState state) {
        this.currentState = state;
    }

    public ManufacturingState getCurrentState() throws ManufacturingProcessNotValid {
        if (this.isValid) {
            return this.currentState;
        }

        throw new ManufacturingProcessNotValid("Can't get current state because ManufacturingProcess object is invalid.");
    }

    private void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    private boolean validate(Storage storage, Blueprint blueprint, Report report) {
        return storage != null &&
               storage.getStockList() != null &&
               blueprint != null &&
               blueprint.getComponentScheme() != null &&
               report != null;
    }    
} 
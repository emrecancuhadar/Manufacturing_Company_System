public class ManufacturingProcess {
    private ManufacturingState currentState;
    private Storage storage;
    private Blueprint blueprint;
    private boolean isValid;

    private ManufacturingProcess() {
        this.currentState = new WaitingForStockState();
        this.storage = new Storage(); // ??
        this.blueprint = new Blueprint();
        this.isValid = false;
    }

    private ManufacturingProcess(ManufacturingState state, Storage storage, Blueprint blueprint) {
        this.currentState = state;
        this.storage = storage;
        this.blueprint = blueprint;
        this.isValid = true;
    }
    
    private void startManufacture() throws ManufacturingProcessNotValid {
        if (!isValid) {
            throw new ManufacturingProcessNotValid();
        }
        
        this.currentState.handle(this);
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

    public Storage getStorage() throws ManufacturingProcessNotValid {
        if (this.isValid) {
            return this.storage;
        }

        throw new ManufacturingProcessNotValid("Can't get storage because ManufacturingProcess object is invalid.");
    }

    public Blueprint getBlueprint() throws ManufacturingProcessNotValid {
        if (this.isValid) {
            return this.blueprint;
        }

        throw new ManufacturingProcessNotValid("Can't get blueprint because ManufacturingProcess object is invalid.");
    }
} 
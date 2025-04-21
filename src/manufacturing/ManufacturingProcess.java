package manufacturing;

import exception.InvalidComponentNodeException;
import exception.InvalidStockException;
import exception.InvalidStorageException;
import exception.ManufacturingProcessNotValidException;
import storage.Storage;
import manufacturing_state.ManufacturingState;
import manufacturing_state.WaitingForStockState;
import order.Blueprint;

/**
 * Main class responsible for handling the manufacturing process
 */
public class ManufacturingProcess {
    private ManufacturingState currentState;
    private boolean isValid;

    public ManufacturingProcess() {
        this.currentState = new WaitingForStockState();
        this.isValid = false;
    }
    
    /**
     * Process the manufacturing order starting from the WaitingForStock state
     * @param storage - the storage object used to check for stocks
     * @param blueprint - the blueprint object of the order
     * @param report - the report object for saving the failure/success of the order
     */
    public void process(Storage storage, Blueprint blueprint, Report report) {
        this.setIsValid(this.validate(storage, blueprint, report));

        try {
            if (!isValid) {
                throw new ManufacturingProcessNotValidException();
            }

            // set the state to WaitingForStockState and let the states handle
            this.setCurrentState(new WaitingForStockState());
            this.currentState.handle(this, storage, blueprint, report);
        } catch (ManufacturingProcessNotValidException | InvalidComponentNodeException | InvalidStorageException | InvalidStockException e) {
            e.printStackTrace();
        }
    }
    
    public void setCurrentState(ManufacturingState state) {
        this.currentState = state;
    }

    public ManufacturingState getCurrentState() throws ManufacturingProcessNotValidException {
        if (this.isValid) {
            return this.currentState;
        }

        throw new ManufacturingProcessNotValidException("Can't get current state because ManufacturingProcess object is invalid.");
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
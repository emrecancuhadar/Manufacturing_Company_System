package manufacturing_state;
import exception.InvalidComponentNodeException;
import exception.InvalidStockException;
import exception.InvalidStorageException;
import exception.ManufacturingProcessNotValidException;
import storage.Storage;
import manufacturing.ManufacturingProcess;
import manufacturing.Report;
import order.Blueprint;
import utils.RandomStateGenerator;

/**
 * The state class when the manufacturing process is in progress
 */
public class InManufacturingState implements ManufacturingState {

    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValidException, 
                                                InvalidComponentNodeException, 
                                                InvalidStorageException, 
                                                InvalidStockException {
        System.out.println("-> All required stock is available.");
        System.out.println("-> State: InManufacturingState");
        ManufacturingState randState = RandomStateGenerator.generateRandomState();
        process.setCurrentState(randState);
        process.getCurrentState().handle(process, storage, blueprint, report);
    }
} 
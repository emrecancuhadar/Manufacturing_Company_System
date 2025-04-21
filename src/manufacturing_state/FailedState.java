package manufacturing_state;
import enums.FailureReason;
import exception.ManufacturingProcessNotValidException;
import storage.Storage;
import manufacturing.ManufacturingProcess;
import manufacturing.Report;
import order.Blueprint;

/**
 * Failed state for manufacturing process
 */
public class FailedState implements ManufacturingState {
    private FailureReason failureReason;

    public FailedState() {
        this.failureReason = FailureReason.SYSTEM_ERROR;
    }

    public FailedState(FailureReason reason) {
        this.failureReason = reason;
    }
    
    @Override
    public void handle(ManufacturingProcess process, 
                        Storage storage, Blueprint blueprint, 
                        Report report) throws ManufacturingProcessNotValidException {
        System.out.println("-> Transitioned to: FailedState (" + failureReason + ")");
        report.recordFailure(this.failureReason);
    }
}
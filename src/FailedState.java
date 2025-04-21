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
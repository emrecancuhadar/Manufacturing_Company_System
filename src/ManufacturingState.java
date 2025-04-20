public interface ManufacturingState {
    void handle(ManufacturingProcess process, 
                Storage storage, Blueprint blueprint, 
                Report report) throws ManufacturingProcessNotValid, 
                                        InvalidComponentNodeException, 
                                        InvalidStorageException;
} 
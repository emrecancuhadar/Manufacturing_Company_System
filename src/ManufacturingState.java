public interface ManufacturingState {
    /**
     * Interface for the manufacturing states
     * @param process - the current manufacturing process
     * @param storage - the storage used to check/reduce stock
     * @param blueprint - the blueprint of the currently handled product
     * @param report - the report to save the state of the manufacturing process
     * @throws ManufacturingProcessNotValidException
     * @throws InvalidComponentNodeException
     * @throws InvalidStorageException
     * @throws InvalidStockException
     */
    void handle(ManufacturingProcess process, 
                Storage storage, Blueprint blueprint, 
                Report report) throws ManufacturingProcessNotValidException, 
                                      InvalidComponentNodeException, 
                                      InvalidStorageException, InvalidStockException;
} 
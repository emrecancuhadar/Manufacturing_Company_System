import java.util.List;

/**
 * Main controller for manufacturing
 */
public class ManufacturingController {
    private List<Order> orders;
    private Storage storage;
    private boolean isValid;

    public ManufacturingController() {
        this.orders = ProductParser.parseProducts("./products.csv");
        this.storage = new Storage(ComponentParser.parseComponents("./components.csv"));        
        this.isValid = true;
    }
       
    /**
     * While the storage is not empty or the orders are not finished,
     * create a new product
     */
    public void startManufacturing() {
        try {
            if (!isValid) {
                throw new ManufacturingControllerNotValidException();
            }
    
            Report report = new Report();
            int currentIndex = 0;

            while (!isOver()) {
                Order order = orders.get(currentIndex);
                if(order.getQuantity() != 0) {
                    ManufacturingProcess manufacturingProcess = new ManufacturingProcess();
                    manufacturingProcess.process(storage, order.getBlueprint(), report);
                    
                    // mark the order as completed and reduce one
                    order.markOneCompleted();
                }
                
                currentIndex = (currentIndex + 1) % orders.size();
            }
            
            // generate the final report
            report.generateReport();
        } catch (ManufacturingControllerNotValidException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the storage is empty or orders are finished
     * @return true if the manufacturing should end
     */
    private boolean isOver() {
        boolean allOrdersDone = orders.stream().allMatch(Order::isFinished);
        boolean stockDepleted = storage.getStockList().isEmpty();
    
        return allOrdersDone || stockDepleted;
    }    
} 
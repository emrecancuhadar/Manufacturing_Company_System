import java.util.List;

public class ManufacturingController {
    private List<Order> orders;
    private Storage storage;
    private boolean isValid;

    public ManufacturingController() {
        this.orders = CSVReader.parseProducts("./products.csv");
        this.storage = new Storage(CSVReader.parseComponents("./components.csv"));
        this.isValid = true;
    }
       
    public void startManufacturing() {
        try {
            if (!isValid) {
                throw new ManufacturingControllerNotValid();
            }
    
            Report report = new Report();
            int currentIndex = 0;

            while (!isOver()) {
                Order order = orders.get(currentIndex);
                if(order.getQuantity() != 0) {
                    ManufacturingProcess manufacturingProcess = new ManufacturingProcess();
                    manufacturingProcess.process(storage, order.getBlueprint(), report);

                order.markOneCompleted();

                }
                currentIndex = (currentIndex + 1) % orders.size();
            }

            report.generateReport();
        } catch (ManufacturingControllerNotValid e) {
            e.printStackTrace();
        }
    }

    private boolean isOver() {
        boolean allOrdersDone = orders.stream().allMatch(Order::isFinished);
        boolean stockDepleted = storage.getStockList().isEmpty();
    
        return allOrdersDone || stockDepleted;
    }    
} 
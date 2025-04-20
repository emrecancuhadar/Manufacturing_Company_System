import java.util.List;

public class ManufacturingController {
    private List<Order> orders;
    private Storage storage;
    private boolean isValid;

    public ManufacturingController() {
        this.orders = CSVReader.parseProducts("./products.csv");
        this.storage = new Storage();
        this.isValid = true;
    }
        
    public void startManufacturing() {
        try {
            if (!isValid) {
                throw new ManufacturingControllerNotValid();
            }
    
            Report report = new Report();
            
            for (Order order: orders) {
                ManufacturingProcess manufacturingProcess = new ManufacturingProcess();
                manufacturingProcess.process(storage, order.getBlueprint(), report);
            }
        } catch (ManufacturingControllerNotValid e) {
            e.printStackTrace();
        }
    }
} 
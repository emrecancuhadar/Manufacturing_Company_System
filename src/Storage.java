import java.util.List;

public class Storage {
    private List<Stock> stockList;
    private Boolean isStorageValid = false;

    // Default constructor
    public Storage() 
    {
        this.stockList = null;
        this.isStorageValid = false;
    }
    // Full constructor
    public Storage(List<Stock> stockList) 
    {
        this.stockList = stockList;
        this.isStorageValid = true;
    }
    // Copy constructor
    public Storage(Storage storage) 
    {
        this.stockList = storage.stockList;
        this.isStorageValid = storage.isStorageValid;
    }
    // Getters and Setters
    public void setStockList(List<Stock> stockList) 
    {
        this.stockList = stockList;
    }
    public List<Stock> getStockList() {
        return stockList;
    }

    public Boolean getIsStorageValid() 
    {
        return isStorageValid;
    }

    private void ensureStorageValid() throws InvalidStorageException 
    {
        if (Boolean.FALSE.equals(isStorageValid)) {
            throw new InvalidStorageException("Storage is invalid.");
        }
    }

    public Stock getStock(String stockName)
            throws InvalidStorageException, InvalidStockException
    {
        ensureStorageValid();

        if (stockList == null) 
        {
            return null;
        }
        for (Stock stock : stockList) 
        {
            if (stockName.equals(stock.getComponent().getName())) 
            {
                stock.validate();      // <-- throws if stock.isStockValid == false
                return stock;
            }
        }
        return null;
    }

    public Boolean checkStock(String componentName, Double requiredQuantity)
            throws InvalidStorageException, InvalidStockException
    {
        ensureStorageValid();

        if (stockList == null) 
        {
            return false;
        }
        for (Stock stock : stockList) {
            if (componentName.equals(stock.getComponent().getName())) 
            {
                stock.validate();     // <-- fail fast on invalid stock
                return stock.getQuantity() >= requiredQuantity;
            }
        }
        return false;
    }

    public void reduceStockQuantity(String componentName, Double quantity)
            throws InvalidStorageException, InvalidStockException
    {
        ensureStorageValid();

        if (stockList == null) 
        {
            return;
        }
        for (Stock stock : stockList) 
        {
            if (componentName.equals(stock.getComponent().getName())) 
            {
                stock.validate();    // <-- prevent tampering invalid stock
                Double currentQuantity = stock.getQuantity();
                if (currentQuantity >= quantity) 
                {
                    stock.setQuantity(currentQuantity - quantity);
                } else 
                {
                    throw new InvalidStorageException(
                        "Cannot reduce stock: insufficient quantity."
                    );
                }
                return;
            }
        }
    }
}

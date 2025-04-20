import java.util.List;

public class Storage {
    private List<Stock> stockList;
    private Boolean isStorageValid = false;
    
    // Default constructor
    public Storage() 
    {
        this.stockList = null;
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
    public List<Stock> getStockList() 
    {
        return stockList;
    }
    public void setIsStorageValid(Boolean isStorageValid) 
    {
        this.isStorageValid = isStorageValid;
    }

    public Boolean getIsStorageValid() 
    {
        return isStorageValid;
    }


    public Stock getStock(String stockName) throws InvalidStorageException
    {
        if (!getIsStorageValid()) 
        {
            throw new InvalidStorageException(
                "Cannot get stock: storage is invalid."
            );
        }
        if (stockList == null) 
        {
            // Either treat null as empty or throw—here we choose empty
            return null;
        }

        for (Stock stock : stockList) 
        {
            if (stockName.equals(stock.getComponent().getName())) 
            {
                return stock;
            }
        }
        // stock not found
        return null;
    }
    
    /**
     * Checks if the given component is in stock with at least the required quantity.
     *
     * @param componentName    the name of the component to look up
     * @param requiredQuantity the minimum quantity required
     * @return true if in stock with sufficient quantity, false otherwise
     * @throws InvalidStorageException if the storage itself is not valid
     */
    public Boolean checkStock(String componentName, int requiredQuantity) throws InvalidStorageException
    {
        if (!getIsStorageValid()) 
        {
            throw new InvalidStorageException(
                "Cannot check stock: storage is invalid."
            );
        }
        if (stockList == null) 
        {
            // Either treat null as empty or throw—here we choose empty
            return false;
        }

        for (Stock stock : stockList) 
        {
            if (componentName.equals(stock.getComponent().getName())) 
            {
                return stock.getQuantity() >= requiredQuantity;
            }
        }
        // component not found
        return false;
    }

    public void reduceStockQuantity(String componentName, int quantity) throws InvalidStorageException
    {
        if (!getIsStorageValid()) 
        {
            throw new InvalidStorageException(
                "Cannot reduce stock: storage is invalid."
            );
        }
        if (stockList == null) 
        {
            // Either treat null as empty or throw—here we choose empty
            return;
        }

        for (Stock stock : stockList) 
        {
            if (componentName.equals(stock.getComponent().getName())) 
            {
                int currentQuantity = stock.getQuantity();
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
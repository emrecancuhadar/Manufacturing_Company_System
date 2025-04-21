import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads, validates, and parses the csv files (i.e. products.csv, components.csv)
 */
public class CSVReader {
    /**
     * Reads the csv file and creates Stock objects
     * @param filePath - name of the file path
     * @return list of Stock objects
     */
    public static List<Stock> parseComponents(String filePath) {
        List<Stock> stocks = new ArrayList<>();
        List<String[]> rows = readCsvFile(filePath, ";", 5);

        if (rows.isEmpty()) {
            System.err.println("Error: No data found in components file");
            return stocks;
        }
        
        // Skip header row
        for (int i = 1; i < rows.size(); i++) {
            Stock stock = parseComponentRow(rows.get(i), i);
            if (stock != null) {
                stocks.add(stock);
            }
        }
        
        return stocks;
    }

    /**
     * Reads the csv file and creates Stock objects
     * @param filePath - name of the file path
     * @return list of Order objects
     */
    public static List<Order> parseProducts(String filePath) {
        List<Order> orders = new ArrayList<>();
        List<String[]> rows = readCsvFile(filePath, ";", 2);
        
        if (rows.isEmpty()) {
            System.err.println("Error: No data found in products file");
            return orders;
        }
        
        // First row is header
        String[] header = rows.get(0);
        
        // Parse data rows
        for (int i = 1; i < rows.size(); i++) {
            String[] data = rows.get(i);
            
            if (data.length != header.length) {
                System.err.println("Warning: Incorrect fields at line " + i);
                continue;
            }
            
            Order order = parseProductRow(data, header, i);
            if (order != null) {
                orders.add(order);
            }
        }
        
        return orders;
    }

    // Core validation methods
    private static boolean isEmptyLine(String line) {
        return line == null || line.trim().isEmpty();
    }
    
    /**
     * Parses the given string value to double using type check
     * @param value - the double value to be parsed to double
     * @param lineNumber - the line number in the csv file
     * @param fieldName - name of the field the value corresponds to in the csv file
     * @return format checked double value
     */
    private static Double parseDoubleSafely(String value, int lineNumber, String fieldName) {
        if (isEmptyLine(value)) return 0.0; // Default for empty values
        try {
            return Double.parseDouble(value.replace(',', '.'));
        } catch (NumberFormatException e) {
            System.err.println("Warning: Invalid " + fieldName + " format at line " + lineNumber);
            return null;
        }
    }
    
    /**
     * Extract quantity value from a string like "1000 m²"
     * @param stockQuantityStr - quantity text
     * @param lineNumber - the line number in the csv file
     * @return stock value
     */
    private static Double extractQuantityValue(String stockQuantityStr, int lineNumber) {
        if (isEmptyLine(stockQuantityStr)) return null;
        
        // Pattern to extract the number part
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(stockQuantityStr.trim());
        
        if (matcher.find()) {
            return parseDoubleSafely(matcher.group(), lineNumber, stockQuantityStr);
        } else {
            System.err.println("Warning: Could not extract quantity value at line " + lineNumber + ": " + stockQuantityStr);
            return null;
        }
    }
    
    /**
     * Determine the QuantityUnit enum from stock quantity string
     * @param stockQuantityStr - quantity unit from the csv file
     * @return the quantity unit enum
     */
    private static QuantityUnit determineQuantityUnit(String stockQuantityStr) {
        String lowerCaseStr = stockQuantityStr.toLowerCase();
        
        if (lowerCaseStr.contains("m²") || lowerCaseStr.contains("m2") || 
            lowerCaseStr.contains("square meter")) {
            return QuantityUnit.SQUARE_METERS;
        } else if (lowerCaseStr.contains("piece") || lowerCaseStr.contains("pieces")) {
            return QuantityUnit.PIECES;
        } else if (lowerCaseStr.contains("box") || lowerCaseStr.contains("boxes")) {
            return QuantityUnit.BOXES;
        } else {
            // Default to pieces if unknown
            return QuantityUnit.PIECES;
        }
    }
    
    /**
     * Reads the csv file
     * @param filePath - the path fo the file
     * @param delimiter - delimeter used to seperate the values
     * @param minColumns - number of minimum columns
     * @return list of the rows in String array
     */
    private static List<String[]> readCsvFile(String filePath, String delimiter, int minColumns) {
        List<String[]> rows = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (isEmptyLine(line)) continue;
                
                String[] data = line.split(delimiter);
                if (data.length < minColumns) {
                    System.err.println("Warning: Insufficient data at line " + lineNumber);
                    continue;
                }
                
                rows.add(data);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error: IOException while reading file: " + e.getMessage());
        }
        
        return rows;
    }
    
    /**
     * Parses a single component row
     * @param data - row data of the compoennt
     * @param lineNumber - the line of the row
     * @return Stock object created
     */
    private static Stock parseComponentRow(String[] data, int lineNumber) {
        try {
            String componentName = data[0].trim();
            if (isEmptyLine(componentName)) return null;
            
            Double unitCost = parseDoubleSafely(data[1], lineNumber, "unit cost");
            Double unitWeight = parseDoubleSafely(data[2], lineNumber, "unit weight");
            String typeStr = data[3];
            String stockQuantityStr = data[4];
            
            // Extract quantity value and unit type separately
            Double quantity = extractQuantityValue(stockQuantityStr, lineNumber);
            QuantityUnit unit = determineQuantityUnit(stockQuantityStr);
            
            if (unitCost == null || unitWeight == null || quantity == null) return null;

            try {
                Type type = Type.fromLabel(typeStr.toLowerCase());
                Component component = new Component(componentName, unitCost, unitWeight, 0, type, unit);
                return new Stock(component, quantity);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.err.println("Warning: Invalid type at line " + lineNumber + ": " + typeStr);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Warning: Error at line " + lineNumber + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Parse a single product row with header info
     * @param data - row data of the product (e.g., ["Chair", "2", "0", "5", "10"])
     * @param header - headers for the row (e.g., ["Product", "Screw", "Glue", "Wood", "Quantity"])
     * @param lineNumber - the line of the row
     * @return Order object created
     */
    private static Order parseProductRow(String[] data, String[] header, int lineNumber) {
        try {
            String productName = data[0].trim();
            if (isEmptyLine(productName)) return null;
            
            Blueprint blueprint = new Blueprint(productName);
            
            // Last column is the order quantity
            Double orderQuantity = parseDoubleSafely(data[data.length - 1], lineNumber, "order quantity");
            if (orderQuantity == null || orderQuantity <= 0) return null;
            
            // Process components (skip first column and last column)
            processComponentsForProduct(data, header, blueprint, lineNumber);
            
            return new Order(blueprint, orderQuantity);
        } catch (Exception e) {
            System.err.println("Warning: Error at line " + lineNumber + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Reads and processes the component quantities of a product from a CSV row and adding them to a Blueprint object. 
     * @param data - row data of the product (e.g., ["Chair", "2", "0", "5", "10"])
     * @param header - headers for the row (e.g., ["Product", "Screw", "Glue", "Wood", "Quantity"])
     * @param blueprint - the Blueprint object that this method will populate with components
     * @param lineNumber - the line of the row
     */
    private static void processComponentsForProduct(String[] data, String[] header, Blueprint blueprint, int lineNumber) {
        for (int i = 1; i < data.length - 1; i++) {
            if (i >= header.length) break;
            
            String componentValue = data[i];
            if (isEmptyLine(componentValue) || componentValue.equals("0")) continue;
            
            Double doubleQuantity = parseDoubleSafely(componentValue, lineNumber, "component " + header[i]);
            if (doubleQuantity != null && doubleQuantity > 0) {
                String componentName = header[i];
                if (!isEmptyLine(componentName)) {
                    blueprint.addComponent(componentName, doubleQuantity);
                }
            }
        }
    }
} 
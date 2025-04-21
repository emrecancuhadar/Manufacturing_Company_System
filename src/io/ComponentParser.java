package io;
import java.util.ArrayList;
import java.util.List;

import storage.Stock;
import enums.*;
import component_composite.*;

public class ComponentParser {
    public static List<Stock> parseComponents(String filePath) {
        List<Stock> stocks = new ArrayList<>();
        List<String[]> rows = CSVReader.read(filePath, ";", 5);

        if (rows.isEmpty()) {
            System.err.println("Error: No data found in components file");
            return stocks;
        }

        for (int i = 1; i < rows.size(); i++) {
            Stock stock = parseRow(rows.get(i), i);
            if (stock != null) {
                stocks.add(stock);
            }
        }

        return stocks;
    }

    private static Stock parseRow(String[] data, int lineNumber) {
        try {
            String componentName = data[0].trim();
            if (ValidationUtils.isEmptyLine(componentName)) return null;

            Double unitCost = ValidationUtils.parseDoubleSafely(data[1], lineNumber, "unit cost");
            Double unitWeight = ValidationUtils.parseDoubleSafely(data[2], lineNumber, "unit weight");
            String typeStr = data[3];
            String stockQuantityStr = data[4];

            Double quantity = ValidationUtils.extractQuantityValue(stockQuantityStr, lineNumber);
            QuantityUnit unit = ValidationUtils.determineQuantityUnit(stockQuantityStr);

            if (unitCost == null || unitWeight == null || quantity == null) return null;

            Type type = Type.fromLabel(typeStr.toLowerCase());
            Component component = new Component(componentName, unitCost, unitWeight, 0, type, unit);
            return new Stock(component, quantity);

        } catch (Exception e) {
            System.err.println("Warning: Error at line " + lineNumber + ": " + e.getMessage());
            return null;
        }
    }
}

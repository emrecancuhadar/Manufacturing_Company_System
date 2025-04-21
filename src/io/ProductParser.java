package io;
import java.util.ArrayList;
import java.util.List;

import order.Blueprint;
import order.Order;

public class ProductParser {
    public static List<Order> parseProducts(String filePath) {
        List<Order> orders = new ArrayList<>();
        List<String[]> rows = CSVReader.read(filePath, ";", 2);

        if (rows.isEmpty()) {
            System.err.println("Error: No data found in products file");
            return orders;
        }

        String[] header = rows.get(0);
        for (int i = 1; i < rows.size(); i++) {
            String[] data = rows.get(i);
            if (data.length != header.length) {
                System.err.println("Warning: Incorrect fields at line " + i);
                continue;
            }

            Order order = parseRow(data, header, i);
            if (order != null) {
                orders.add(order);
            }
        }

        return orders;
    }

    private static Order parseRow(String[] data, String[] header, int lineNumber) {
        try {
            String productName = data[0].trim();
            if (ValidationUtils.isEmptyLine(productName)) return null;

            Blueprint blueprint = new Blueprint(productName);
            Double orderQuantity = ValidationUtils.parseDoubleSafely(data[data.length - 1], lineNumber, "order quantity");
            if (orderQuantity == null || orderQuantity <= 0) return null;

            for (int i = 1; i < data.length - 1; i++) {
                if (i >= header.length) break;
                String componentValue = data[i];
                if (ValidationUtils.isEmptyLine(componentValue) || componentValue.equals("0")) continue;

                Double doubleQuantity = ValidationUtils.parseDoubleSafely(componentValue, lineNumber, "component " + header[i]);
                if (doubleQuantity != null && doubleQuantity > 0) {
                    String componentName = header[i];
                    blueprint.addComponent(componentName, doubleQuantity);
                }
            }

            return new Order(blueprint, orderQuantity);
        } catch (Exception e) {
            System.err.println("Warning: Error at line " + lineNumber + ": " + e.getMessage());
            return null;
        }
    }
}

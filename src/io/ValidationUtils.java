package io;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import enums.*;

public class ValidationUtils {
    public static boolean isEmptyLine(String line) {
        return line == null || line.trim().isEmpty();
    }

    public static Double parseDoubleSafely(String value, int lineNumber, String fieldName) {
        if (isEmptyLine(value)) return 0.0;
        try {
            return Double.parseDouble(value.replace(',', '.'));
        } catch (NumberFormatException e) {
            System.err.println("Warning: Invalid " + fieldName + " format at line " + lineNumber);
            return null;
        }
    }

    public static Double extractQuantityValue(String stockQuantityStr, int lineNumber) {
        if (isEmptyLine(stockQuantityStr)) return null;

        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(stockQuantityStr.trim());

        if (matcher.find()) {
            return parseDoubleSafely(matcher.group(), lineNumber, stockQuantityStr);
        } else {
            System.err.println("Warning: Could not extract quantity at line " + lineNumber + ": " + stockQuantityStr);
            return null;
        }
    }

    public static QuantityUnit determineQuantityUnit(String stockQuantityStr) {
        String lower = stockQuantityStr.toLowerCase();
        if (lower.contains("m²") || lower.contains("m2") || lower.contains("square meter")) {
            return QuantityUnit.SQUARE_METERS;
        } else if (lower.contains("box")) {
            return QuantityUnit.BOXES;
        } else {
            return QuantityUnit.PIECES;
        }
    }
}

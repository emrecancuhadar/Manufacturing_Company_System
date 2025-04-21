package io;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<String[]> read(String filePath, String delimiter, int minColumns) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (ValidationUtils.isEmptyLine(line)) continue;

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
}
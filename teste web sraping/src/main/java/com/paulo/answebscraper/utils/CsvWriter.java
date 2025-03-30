package com.paulo.answebscraper.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    public void writeCSV(String filePath, List<String[]> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                String formattedRow = "\"" + String.join("\",\"", row) + "\"";
                writer.append(formattedRow).append("\n");
            }
            System.out.println("CSV gerado com sucesso: " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao escrever CSV: " + e.getMessage());
        }
    }
}

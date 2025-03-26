package com.paulo.answebscraper.dataextraction;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFDataExtractor {
    public static List<String[]> extractTableData(File pdfFile) {
        List<String[]> tableData = new ArrayList<>();

        try (PDDocument document = PDDocument.load(pdfFile)) {
            // Extrair texto de todas as páginas usando um stripper personalizado
            CustomPDFTextStripper stripper = new CustomPDFTextStripper();
            String fullText = stripper.getText(document);

            // Padrão de regex mais flexível para encontrar linhas da tabela
            Pattern tableRowPattern = Pattern.compile(
                    "(?m)^(\\d+)\\s*" +       // Código (início da linha)
                            "(.+?)\\s*" +             // Descrição (não greedy)
                            "(Sim|Não)\\s*" +         // Cobertura Obrigatória
                            "(OD|AMB)\\s*" +          // Tipo de Atendimento
                            "(\\d+,\\d{2})$"          // Valor (fim da linha)
            );

            Matcher matcher = tableRowPattern.matcher(fullText);

            // Cabeçalho do CSV
            tableData.add(new String[]{
                    "Código",
                    "Descrição",
                    "Cobertura Obrigatória",
                    "Tipo de Atendimento",
                    "Valor"
            });

            // Coletar todas as correspondências
            while (matcher.find()) {
                String[] row = new String[5];
                row[0] = matcher.group(1);  // Código
                row[1] = matcher.group(2).trim();  // Descrição
                row[2] = matcher.group(3);  // Cobertura Obrigatória

                // Substituir abreviações
                String tipoAtendimento = matcher.group(4);
                row[3] = tipoAtendimento.equals("OD") ? "Odontológico" : "Ambulatorial";

                row[4] = matcher.group(5);  // Valor

                tableData.add(row);
            }

            System.out.println("Dados extraídos: " + (tableData.size() - 1) + " linhas");
        } catch (IOException e) {
            System.err.println("Erro ao extrair dados do PDF: " + e.getMessage());
            e.printStackTrace();
        }

        return tableData;
    }

    // Stripper personalizado para melhorar a extração de texto
    private static class CustomPDFTextStripper extends PDFTextStripper {
        public CustomPDFTextStripper() throws IOException {
            super();
            // Configurações para melhorar a extração de texto
            setSortByPosition(true);
            setAddMoreFormatting(true);
        }

        @Override
        protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
            // Pode ser personalizado se necessário para lidar com layouts específicos
            super.writeString(text, textPositions);
        }
    }
}
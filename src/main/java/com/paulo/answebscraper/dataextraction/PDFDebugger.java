package com.paulo.answebscraper.dataextraction;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFDebugger {
    public static void printFullPdfText(File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();

            // Imprimir o texto de cada página
            int pageCount = document.getNumberOfPages();
            System.out.println("Número total de páginas: " + pageCount);

            for (int page = 1; page <= pageCount; page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);

                System.out.println("\n--- PÁGINA " + page + " ---");
                String pageText = stripper.getText(document);
                System.out.println(pageText);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
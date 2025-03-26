package com.paulo.answebscraper;

import com.paulo.answebscraper.scraper.WebScraper;
import com.paulo.answebscraper.scraper.PDFDownloader;
import com.paulo.answebscraper.utils.ZipFileCompressor;
import com.paulo.answebscraper.dataextraction.PDFDataExtractor;
import com.paulo.answebscraper.dataextraction.PDFDebugger;
import com.paulo.answebscraper.utils.CsvWriter;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    performWebScraping();
                    break;
                case 2:
                    performDataExtraction();
                    break;
                case 3:
                    performPdfDebug();
                    break;
                case 4:
                    System.out.println("Saindo do programa...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- MENU ANS WEB SCRAPER ---");
        System.out.println("1. Realizar Web Scraping e Download de PDFs");
        System.out.println("2. Extrair Dados dos PDFs para CSV");
        System.out.println("3. Depurar Conteúdo do PDF");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // [Métodos anteriores mantidos na íntegra]

    private static void performPdfDebug() {
        System.out.println("🔍 Iniciando Depuração de PDF...");

        // 1. Encontrar o arquivo do Anexo I
        File anexoIFile = findAnexoIFile();
        if (anexoIFile == null) {
            System.out.println("Erro: Arquivo do Anexo I não encontrado.");
            return;
        }

        // 2. Imprimir o conteúdo completo do PDF
        PDFDebugger.printFullPdfText(anexoIFile);
    }
}
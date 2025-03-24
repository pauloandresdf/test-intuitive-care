package com.paulo.answebscraper;

import com.paulo.answebscraper.scraper.WebScraper;
import com.paulo.answebscraper.scraper.PDFDownloader;
import com.paulo.answebscraper.utils.ZipFileCompressor;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Web Scraping...");

        // 1. Buscar links dos PDFs no site
        List<String> pdfLinks = WebScraper.getPdfLinks();
        System.out.println("Links para PDFs encontrados: " + pdfLinks);

        // 2. Baixar os PDFs filtrando apenas Anexo I e II
        List<File> downloadedFiles = PDFDownloader.downloadFiles(pdfLinks, "data/");

        // 3. Compactar os PDFs baixados em um ZIP
        ZipFileCompressor.zipFiles(downloadedFiles.toArray(new File[0]), "data/AnexosCompactados.zip");

        System.out.println("✅ Processo concluído!");
    }
}
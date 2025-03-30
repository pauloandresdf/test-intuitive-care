package com.paulo.answebscraper.scraper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class PDFDownloader {
    public static List<File> downloadFiles(List<String> pdfLinks, String saveDirectory) {
        List<File> downloadedFiles = new ArrayList<>();

        // Garantir que a pasta de destino exista
        File directory = new File(saveDirectory);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();  // Cria a pasta de destino caso não exista
            if (!created) {
                System.err.println("Erro ao criar a pasta de destino.");
                return downloadedFiles;
            }
        }

        for (String link : pdfLinks) {
            // Verificar se o link contém "Anexo_I" ou "Anexo_II" para baixar somente os anexos desejados
            if (link.contains("Anexo_I") || link.contains("Anexo_II")) {
                try {
                    // Baixar o arquivo
                    URL url = new URL(link);
                    String fileName = link.substring(link.lastIndexOf("/") + 1);
                    File destinationFile = new File(saveDirectory + fileName);

                    // Evitar sobrescrever arquivos com o mesmo nome
                    int counter = 1;
                    while (destinationFile.exists()) {
                        String baseName = fileName.substring(0, fileName.lastIndexOf("."));
                        String extension = fileName.substring(fileName.lastIndexOf("."));
                        fileName = baseName + "-" + counter + extension;
                        destinationFile = new File(saveDirectory + fileName);
                        counter++;
                    }

                    // Baixar o arquivo
                    try (InputStream in = url.openStream()) {
                        java.nio.file.Files.copy(in, destinationFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        downloadedFiles.add(destinationFile);
                        System.out.println("Arquivo baixado: " + fileName);
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao baixar o arquivo " + link + ": " + e.getMessage());
                }
            }
        }

        return downloadedFiles;
    }
}

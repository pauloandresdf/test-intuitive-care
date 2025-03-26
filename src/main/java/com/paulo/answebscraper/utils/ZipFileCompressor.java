package com.paulo.answebscraper.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.HashSet;
import java.util.Set;

public class ZipFileCompressor {
    public static void zipFiles(File[] files, String zipFileName) {
        Set<String> addedFileNames = new HashSet<>();  // Para controlar arquivos adicionados

        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (File file : files) {
                if (file.exists()) {
                    String fileName = file.getName();

                    // Garantir que o nome do arquivo seja único dentro do ZIP
                    int counter = 1;
                    String uniqueFileName = fileName;
                    while (addedFileNames.contains(uniqueFileName)) {
                        uniqueFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "-" + counter + fileName.substring(fileName.lastIndexOf("."));
                        counter++;
                    }

                    // Adiciona o nome único ao conjunto e ao arquivo ZIP
                    addedFileNames.add(uniqueFileName);

                    try (FileInputStream fis = new FileInputStream(file)) {
                        ZipEntry zipEntry = new ZipEntry(uniqueFileName);
                        zos.putNextEntry(zipEntry);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) >= 0) {
                            zos.write(buffer, 0, length);
                        }
                        zos.closeEntry();
                    }
                }
            }

            System.out.println("Arquivos compactados com sucesso em: " + zipFileName);
        } catch (IOException e) {
            System.err.println("Erro ao compactar arquivos: " + e.getMessage());
        }
    }
}
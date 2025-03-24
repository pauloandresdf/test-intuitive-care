package com.paulo.answebscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    public static List<String> getPdfLinks() {
        List<String> pdfLinks = new ArrayList<>();

        try {
            // 1. Acessar o site
            String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
            Document doc = Jsoup.connect(url).get();

            // 2. Encontrar todos os links para PDFs na página
            Elements links = doc.select("a[href$=.pdf]"); // Seleciona todos os links que terminam com .pdf

            for (Element link : links) {
                String linkHref = link.attr("href");
                if (linkHref.startsWith("/")) {
                    linkHref = "https://www.gov.br" + linkHref; // Corrigir links relativos
                }
                pdfLinks.add(linkHref);
            }
            System.out.println("Links para PDFs encontrados: " + pdfLinks);
        } catch (IOException e) {
            System.err.println("Erro ao acessar o site: " + e.getMessage());
        }

        return pdfLinks;
    }
}
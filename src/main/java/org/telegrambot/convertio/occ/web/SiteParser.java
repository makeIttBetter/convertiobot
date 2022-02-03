package org.telegrambot.convertio.occ.web;

import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SiteParser {
    @Value("${updateCheckmarks}")
    @Setter
    private String userAgent;

    public Document parse(String myURL) throws IOException {
        return read(myURL);
    }

    /**
     * Метод для получения html кода практически любой страницы не содержащей JS
     *
     * @param url ссылка на страницу для парсинга
     * @return Документ с html кодом
     * @throws IOException
     */
    private Document read(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent(userAgent)
                .referrer("http://www.google.com").get();
    }
}
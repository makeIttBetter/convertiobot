package org.telegrambot.convertio;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegrambot.convertio.occ.web.SiteParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;

public class TestDB {
    private static final String my_file_name = "src/main/resources/spring/convertio_custom.properties";
    private static final String convertio_filetypes_en_link = "https://convertio.co/en/formats/";
    private static final String convertio_filetypes_ru_link = "https://convertio.co/ru/formats/";

    private final SiteParser siteParser = new SiteParser();
//    private final FileCategoryFacade fileCategoryFacade;
//
//    private static void init() {
//        siteParser.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:77.0) Gecko/20190101 Firefox/76.0");
//    }

    public static void main(String[] args) throws IOException {
//        init();
//
//        Document doc = siteParser.parse(convertio_filetypes_en_link);
//
//        Elements categories = doc.getElementsByClass("mb-2");
////        categories.forEach((el) -> System.out.println(el.text()));
//
//        Elements typeTables = doc.getElementsByClass("data-table");
////        System.out.println(typeTables.size());
////        typeTables;
//        int i = 0;
//        for (Element table : typeTables) {
////            System.out.println(categories.get(i++).text());
//            try {
//                Elements rows = table.getElementsByClass("row");
//                rows.remove(0);
////                System.out.println("rows.size: " + rows.size());
//                for (Element row : rows) {
//                    System.out.println(System.lineSeparator());
//                    System.out.println
//                            ("  - " + row.getElementsByClass("text-uppercase").get(0).text());
//                    System.out.println
//                            ("    - " + row.getElementsByClass("col-12").get(0).text());
//                    try {
//                        Elements elms = row.getElementsByClass("text-blue");
//                        Element writable = elms.get(0);
//                        System.out.println
//                                ("      - " + writable.text());
//                    } catch (Exception e) {
//                        System.out.println("      - " + "Type is not writable :(");
//                    }
//                    try{
//                    System.out.println
//                            ("      - " + row.getElementsByClass("text-green").get(0).text());
//                    } catch (Exception e) {
//                        System.out.println("      - " + "Type is not readable :(");
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("|||||||" + e.getMessage() + "; " + e.getCause() + "||||||||");
//            }
//        }


//        addNewProperty("name", "value");

    }

//    <div class="row">
//                                    <div class="col-auto col-sm-3 font-weight-sbold">
//                                        <a href="https://convertio.co/formats/7z/" class="link-unstyled">
//                                            <span class="format-icon f-archive f-7z"></span>
//                                            <span class="link-gray text-uppercase"><u>7Z</u></span>
//                                        </a>
//                                    </div>
//                                    <div class="col-12 col-sm order-1 order-sm-0">
//                                        7-Zip Compressed File
//                                    </div>
//                                    <div class="col col-sm-4 text-right formats-badges">
//                                                                                    <span class="ui-badge text-blue">
//                                                Write                                            </span>
//                                                                                                                            <span class="ui-badge text-green">
//                                                Read                                            </span>
//                                                                            </div>
//                                </div>

    private static void addNewProperty(String name, String value) throws IOException {
        String newLine = name + "=" + value + System.lineSeparator();

        Writer output;
        output = new BufferedWriter(new FileWriter(my_file_name, true));
        output.append(newLine);
        output.close();
    }

    public void findDomain2(Document doc) throws MalformedURLException {
        Elements tracks = doc.getElementsByAttributeValue("itemprop", "track");
        for (Element element : tracks) {
            Elements durations = element.getElementsByClass("duration");
            StringBuilder sb = new StringBuilder();
            for (Element duration : durations) {
                String text = duration.text();

                String[] time = text.split(":");
                if (Integer.parseInt(time[0]) <= 20) {
                    if (Integer.parseInt(time[1]) > 60) {
                        time[0] = String.valueOf(Integer.parseInt(time[0]) + 1);
                        time[1] = String.valueOf(Integer.parseInt(time[1]) - 60);
                    } else if (Integer.parseInt(time[1]) == 60) {
                        time[0] = String.valueOf(Integer.parseInt(time[0]) + 1);
                        time[1] = "00";
                    }
                    text = time[0] + ":" + time[1];

                    if (text.length() < 5) {
                        sb.append("0").append(text);
                    } else {
                        sb.append(duration.ownText());
                    }
                }
            }
            Elements names = element.getElementsByClass("track");
            for (Element name : names) {
                sb.append(" ").append(name.ownText());
            }
            Elements links = element.getElementsByAttributeValue("itemprop", "url");
            String url = null;
            for (Element link : links) {
                url = link.attributes().get("href");
            }
        }

    }
}

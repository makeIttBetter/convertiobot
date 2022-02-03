package org.telegrambot.convertio.occ.bot.commands.test;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.exceptions.facades.FacadeException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.FileCategoryData;
import org.telegrambot.convertio.facades.data.files.FileTypeData;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.facades.facades.FileCategoryFacade;
import org.telegrambot.convertio.facades.facades.FileTypeFacade;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.commands.AbstractCommand;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.web.SiteParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.function.Predicate;


@MainPoint
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestCommand extends AbstractCommand {
    private static final String propertiesFilePath = "src/main/resources/convertio.properties";
    private static final String propertiesENFilePath = "src/main/resources/convertio_en.properties";
    private static final String propertiesRUFilePath = "src/main/resources/convertio_ru.properties";

    private static final String convertio_filetypes_en_link = "https://convertio.co/en/formats/";
    private static final String convertio_filetypes_ru_link = "https://convertio.co/ru/formats/";

    private final SiteParser siteParser;
    private final FileCategoryFacade fileCategoryFacade;
    private final FileTypeFacade fileTypeFacade;
    @Qualifier("fileTypeReverseConverter")
    private final Converter<FileTypeData, FileType> fileTypeConverter;
//    private final FileFacade fileFacade;

    private static void addNewProperty(String filePath, String name, String value) throws IOException {
        String newLine = name + "=" + value + System.lineSeparator();

        Writer output;
        output = new BufferedWriter(new FileWriter(filePath, true));
        output.append(newLine);
        output.close();
    }

    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {

        Document doc;
        try {
            doc = siteParser.parse(convertio_filetypes_en_link);

            Elements categories = doc.getElementsByClass("mb-2");

            Elements typeTables = doc.getElementsByClass("data-table");
            int i = 0;
            for (Element table : typeTables) {
                FileCategoryData categoryData = createNewCategoryDataObj(categories.get(i++).text());

                System.out.println(categoryData.getName());
                try {
                    Elements rows = table.getElementsByClass("row");
                    rows.remove(0);
                    for (Element row : rows) {
                        System.out.println(System.lineSeparator());

                        String typeCode = row.getElementsByClass("text-uppercase").get(0).text();
                        String typeDescription = row.getElementsByClass("col-12").get(0).text();
                        FileTypeData fileTypeData = createNewFileTypeDataObj(typeDescription, typeCode);
                        fileTypeData.setCode(row.getElementsByClass("text-uppercase").get(0).text());

                        System.out.println("  - " + fileTypeData.getCode());
                        System.out.println("    - " + fileTypeData.getDescription());
                        try {
                            Elements elms = row.getElementsByClass("text-blue");
                            Element writable = elms.get(0);
                            System.out.println("      - " + writable.text());
                            fileTypeData.setWriteable(true);
                        } catch (Exception e) {
                            System.out.println("      - " + "Type is not writable :(");
                            fileTypeData.setWriteable(false);
                        }
                        try {
                            System.out.println("      - " + row.getElementsByClass("text-green").get(0).text());
                            fileTypeData.setReadable(true);
                        } catch (Exception e) {
                            System.out.println("      - " + "Type is not readable :(");
                            fileTypeData.setReadable(false);
                        }
                        System.out.println(fileTypeData);
                        fileTypeData.setCategory(categoryData);
                        fileTypeFacade.create(fileTypeData);
                    }
                } catch (Exception e) {
                    System.out.println("|||||||" + e.getMessage() + "; " + e.getCause() + "||||||||");
                }
                System.out.println(categoryData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FacadeException e) {
            e.printStackTrace();
        }

        return responses;
    }

    private FileCategoryData createNewCategoryDataObj(String categoryNameEn) throws IOException, FacadeException {
        String categoryPropertyPrefix = "type.category.";
        String categoryPropertySuffix = ".name";

        FileCategoryData categoryData = new FileCategoryData();
        String categoryPropertyName = categoryPropertyPrefix + categoryNameEn.toLowerCase() + categoryPropertySuffix;
        categoryData.setName(categoryPropertyName);

        addNewProperty(propertiesFilePath, categoryPropertyName, categoryNameEn);
        addNewProperty(propertiesENFilePath, categoryPropertyName, categoryNameEn);

        categoryData = fileCategoryFacade.create(categoryData);
        return categoryData;
    }

    private FileTypeData createNewFileTypeDataObj(String fileTypeDescEn, String typeCode) throws IOException {
        String fileTypePropertyPrefix = "type.filetype.";
        String fileTypePropertySuffix = ".description";

        FileTypeData fileTypeData = new FileTypeData();
        String fileTypePropertyName = fileTypePropertyPrefix + typeCode.toLowerCase() + fileTypePropertySuffix;
        fileTypeData.setDescription(fileTypePropertyName);

        addNewProperty(propertiesFilePath, fileTypePropertyName, fileTypeDescEn);
        addNewProperty(propertiesENFilePath, fileTypePropertyName, fileTypeDescEn);

        return fileTypeData;
    }

//    @Override
//    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {
//
//        String fileId = botContext.getUpdate().getMessage().getDocument().getFileId();
//
//        GetFile getFile = new GetFile();
//        getFile.setFileId(fileId);
//
//        try {
//            File file = bot.execute(getFile);
//            String url = file.getFileUrl(bot.getBotToken());
//
//            System.out.println(url);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
////        responses.get(user).add(sendPoll);
//
//        // forming return value
//        responses.get(user).setStateChanged(false);
//        responses.get(user).setUndo(false);
//
//        return responses;
//    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> bc.getUpdate().getMessage().getText() != null && bc.getUpdate().getMessage().getText().equals("/test");
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return State.ALL;
    }

}




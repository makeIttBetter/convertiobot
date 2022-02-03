package org.telegrambot.convertio.occ.bot.commands.test;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegrambot.convertio.core.model.files.FileType;
import org.telegrambot.convertio.exceptions.facades.FacadeException;
import org.telegrambot.convertio.facades.converters.Converter;
import org.telegrambot.convertio.facades.data.files.File2FileData;
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
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;
import org.telegrambot.convertio.occ.web.SiteParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@MainPoint
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Test1Command extends AbstractCommand {
    private static final String propertiesFilePath = "src/main/resources/convertio.properties";
    private static final String propertiesENFilePath = "src/main/resources/convertio_en.properties";
    private static final String propertiesRUFilePath = "src/main/resources/convertio_ru.properties";

    private static final String convertio_filetypes_en_link = "https://convertio.co/en/formats/";
    private static final String convertio_filetypes_ru_link = "https://convertio.co/ru/formats/";

    private final MessageBuilder messageBuilder;
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

        long before = System.currentTimeMillis();

        // fill the DB
        createEssentialData();
        createEssentialDataForLocaleRu();
        createRelationsForWritableTypes();

        long after = System.currentTimeMillis();

        // send message "done✅"
        long executionTime = after - before;
        return sendMessage(executionTime);
    }

    private Map<UserData, BotResponseWsDTO> sendMessage(long millis) {
        long seconds = millis / 1000;
        SendMessage infoMessage = messageBuilder.getMsgHTML(chatId, "finished in: " + seconds + " sec.");

        responses.get(user).add(infoMessage);

        // forming return value
        responses.get(user).setStateChanged(true);
        responses.get(user).setUndo(true);

        return responses;
    }

    private void executeWritableTypes(String typeLink) throws IOException, FacadeException {
        Document doc = siteParser.parse(typeLink);
        Elements type2typeLinks = doc.getElementsByClass("link-gray");
//        https://convertio.co/ttf-afm/

        String linkPrefix = "https://convertio.co/";
        for (Element type2typeElement : type2typeLinks) {
            String[] type2typeArr = type2typeElement.attributes().get("href").replace(linkPrefix, "").replace("/", "").split("-");
            String currentTypeCode = type2typeArr[0].toUpperCase();
            String targetTypeCode = type2typeArr[1].toUpperCase();

            // get current fileType
            FileTypeData fileTypeData = fileTypeFacade.readByCode(currentTypeCode);

            // add new writableType for current type
            Set<File2FileData> writableTypes = fileTypeData.getWritableTypes();
            File2FileData newFile2File = new File2FileData(currentTypeCode, targetTypeCode);
            writableTypes.add(newFile2File);
            fileTypeData.setWritableTypes(writableTypes);

            fileTypeFacade.update(fileTypeData);
        }
    }

    private void createRelationsForWritableTypes() {
        Document doc;
        try {
            doc = siteParser.parse(convertio_filetypes_en_link);

            Elements categories = doc.getElementsByClass("mb-2");

            Elements typeTables = doc.getElementsByClass("data-table");
            int i = 0;
            for (Element table : typeTables) {
                try {
                    Elements rows = table.getElementsByClass("row");
                    rows.remove(0);
                    for (Element row : rows) {
                        String typeCode = row.getElementsByClass("text-uppercase").get(0).text();
                        String typeDescription = row.getElementsByClass("col-12").get(0).text();

                        // type link execution
                        String typeLink = row.getElementsByClass("link-unstyled").get(0).attributes().get("href");
                        try {
                            executeWritableTypes(typeLink);
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + " | " + e.getCause());
                            e.printStackTrace();
                        }

                        FileTypeData fileTypeData = createNewFileTypeDataObj(typeDescription, typeCode);
                        fileTypeData.setCode(row.getElementsByClass("text-uppercase").get(0).text());

                    }
                } catch (Exception e) {
                    System.out.println("|||||||" + e.getMessage() + "; " + e.getCause() + "||||||||");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createEssentialData() {
        Document doc;
        try {
            doc = siteParser.parse(convertio_filetypes_en_link);

            Elements categories = doc.getElementsByClass("mb-2");

            Elements typeTables = doc.getElementsByClass("data-table");
            int i = 0;
            for (Element table : typeTables) {
                FileCategoryData categoryData = createNewCategoryDataObj(categories.get(i++).text());
                categoryData = fileCategoryFacade.create(categoryData);

                try {
                    Elements rows = table.getElementsByClass("row");
                    rows.remove(0);
                    for (Element row : rows) {
                        String typeCode = row.getElementsByClass("text-uppercase").get(0).text();
                        String typeDescription = row.getElementsByClass("col-12").get(0).text();

                        FileTypeData fileTypeData = createNewFileTypeDataObj(typeDescription, typeCode);
                        fileTypeData.setCode(row.getElementsByClass("text-uppercase").get(0).text());

                        try {
                            row.getElementsByClass("text-blue").get(0).text();
                            fileTypeData.setWriteable(true);
                        } catch (Exception e) {
                            fileTypeData.setWriteable(false);
                        }
                        try {
                            row.getElementsByClass("text-green").get(0).text();
                            fileTypeData.setReadable(true);
                        } catch (Exception e) {
                            fileTypeData.setReadable(false);
                        }
                        fileTypeData.setCategory(categoryData);
                        fileTypeFacade.create(fileTypeData);
                    }
                } catch (Exception e) {
                    System.out.println("|||||||" + e.getMessage() + "; " + e.getCause() + "||||||||");
                }
            }
        } catch (IOException | FacadeException e) {
            e.printStackTrace();
        }
    }

    private FileCategoryData createNewCategoryDataObj(String categoryNameEn) throws IOException, FacadeException {
        String categoryPropertyPrefix = "type.category.";
        String categoryPropertySuffix = ".name";

        FileCategoryData categoryData = new FileCategoryData();
        String categoryPropertyName = categoryPropertyPrefix + categoryNameEn.toLowerCase() + categoryPropertySuffix;
        categoryData.setName(categoryPropertyName);

        addNewProperty(propertiesFilePath, categoryPropertyName, categoryNameEn);
        addNewProperty(propertiesENFilePath, categoryPropertyName, categoryNameEn);

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


//     ----------------------------------------------------


    private void createEssentialDataForLocaleRu() {
        Document doc;
        Document docRu;
        try {
            doc = siteParser.parse(convertio_filetypes_en_link);
            docRu = siteParser.parse(convertio_filetypes_ru_link);

            Elements categories = doc.getElementsByClass("mb-2");
            Elements categoriesRU = docRu.getElementsByClass("mb-2");

            Elements typeTables = doc.getElementsByClass("data-table");
            Elements typeTablesRU = docRu.getElementsByClass("data-table");
            int i = 0;
            for (Element table : typeTables) {
                createNewCategoryDataObjRU(categories.get(i).text(), categoriesRU.get(i).text());

                Element tableRu = typeTablesRU.get(i);

                try {
                    Elements rows = table.getElementsByClass("row");
                    Elements rowsRU = tableRu.getElementsByClass("row");
                    rows.remove(0);
                    rowsRU.remove(0);
                    int j = 0;
                    for (Element row : rows) {
                        Element rowRU = rowsRU.get(j);
                        String typeCode = row.getElementsByClass("text-uppercase").get(0).text();
                        String typeDescription = rowRU.getElementsByClass("col-12").get(0).text();

                        createNewFileTypeDataObjRU(typeDescription, typeCode);

                        j++;
                    }
                } catch (Exception e) {
                    System.out.println("|||||||" + e.getMessage() + "; " + e.getCause() + "||||||||");
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createNewCategoryDataObjRU(String categoryNameEn, String categoryNameRu) throws IOException {
        String categoryPropertyPrefix = "type.category.";
        String categoryPropertySuffix = ".name";

        FileCategoryData categoryData = new FileCategoryData();
        String categoryPropertyName = categoryPropertyPrefix + categoryNameEn.toLowerCase() + categoryPropertySuffix;
        categoryData.setName(categoryPropertyName);

        addNewProperty(propertiesRUFilePath, categoryPropertyName, categoryNameRu);

    }

    private void createNewFileTypeDataObjRU(String fileTypeDescRu, String typeCode) throws IOException {
        String fileTypePropertyPrefix = "type.filetype.";
        String fileTypePropertySuffix = ".description";

        FileTypeData fileTypeData = new FileTypeData();
        String fileTypePropertyName = fileTypePropertyPrefix + typeCode.toLowerCase() + fileTypePropertySuffix;
        fileTypeData.setDescription(fileTypePropertyName);

        addNewProperty(propertiesRUFilePath, fileTypePropertyName, fileTypeDescRu);

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
        return (bc) -> bc.getUpdate().getMessage().getText() != null && bc.getUpdate().getMessage().getText().equals("/updateDataBaseTypes");
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

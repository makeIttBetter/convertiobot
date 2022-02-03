package org.telegrambot.convertio.occ.bot.commands.convert;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.annotations.MainPoint;
import org.telegrambot.convertio.occ.bot.commands.AbstractCommand;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.InputType;
import org.telegrambot.convertio.occ.bot.entities.State;
import org.telegrambot.convertio.occ.bot.view.button.ButtonsHelper;
import org.telegrambot.convertio.occ.bot.view.message.MessageBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@MainPoint
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReceiveFileCommand extends AbstractCommand {
    private final MessageBuilder messageBuilder;
    private final ButtonsHelper buttonsHelper;
    @Value("${convertio.api.new_conversion.link}")
    private String convertionLink;


    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) {

        String fileId = getFileId(botContext);

        if (fileId != null) {
            GetFile getFile = new GetFile();
            getFile.setFileId(fileId);

            try {

                File file = getFile(fileId);

                String url = file.getFileUrl(bot.getBotToken());

                System.out.println(file.getFilePath());
//            System.out.println(file.get());

                System.out.println(url);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
//        responses.get(user).add(sendPoll);

        // forming return value
        responses.get(user).setStateChanged(false);
        responses.get(user).setUndo(false);

        return responses;
    }

    private File getFile(String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        return bot.execute(getFile);
    }

    private String getFileId(BotContextWsDTO botContext) {
        String fileId = null;

        if (InputType.FILE.equals(botContext.getInputType())) {
            System.out.println(InputType.FILE);

            Document document = botContext.getUpdate().getMessage().getDocument();
            fileId = document.getFileId();
        } else if (InputType.PHOTO.equals(botContext.getInputType())) {

            System.out.println(InputType.PHOTO);

            List<PhotoSize> photo = botContext.getUpdate().getMessage().getPhoto();
            PhotoSize photoSize = photo.get(0);
            fileId = photoSize.getFileId();

        } else if (InputType.VIDEO.equals(botContext.getInputType())) {
            System.out.println(InputType.VIDEO);

            Video video = botContext.getUpdate().getMessage().getVideo();
            fileId = video.getFileId();

            System.out.println("fileName: " + video.getFileName());
            System.out.println("fileSize: " + video.getFileSize());

        } else if (InputType.AUDIO.equals(botContext.getInputType())) {
            System.out.println(InputType.AUDIO);

            Audio audio = botContext.getUpdate().getMessage().getAudio();
            fileId = audio.getFileId();

        } else if (InputType.ANIMATION.equals(botContext.getInputType())) {
            System.out.println(InputType.ANIMATION);

            Animation animation = botContext.getUpdate().getMessage().getAnimation();
            fileId = animation.getFileId();

        } else if (InputType.VIDEO_NOTE.equals(botContext.getInputType())) {
            System.out.println(InputType.VIDEO_NOTE);

            VideoNote videoNote = botContext.getUpdate().getMessage().getVideoNote();
            fileId = videoNote.getFileId();

        } else if (InputType.STICKER.equals(botContext.getInputType())) {
            System.out.println(InputType.STICKER);

            Sticker sticker = botContext.getUpdate().getMessage().getSticker();
            fileId = sticker.getFileId();

        }
        return fileId;
    }


    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return (bc) -> InputType.FILE.equals(bc.getInputType())
                || InputType.PHOTO.equals(bc.getInputType())
                || InputType.VIDEO.equals(bc.getInputType())
                || InputType.AUDIO.equals(bc.getInputType())
                || InputType.ANIMATION.equals(bc.getInputType())
                || InputType.VIDEO_NOTE.equals(bc.getInputType())
                || InputType.STICKER.equals(bc.getInputType());
    }

    @Override
    public State operatedState() {
        return State.ALL;
    }

    @Override
    public State returnedState() {
        return State.CHOOSE_FORMAT_TO_CONVERT;
    }

}


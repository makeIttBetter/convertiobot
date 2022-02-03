package org.telegrambot.convertio.occ.bot.view.message;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.games.Animation;

@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageBuilder {

    public SendMessage getMsgWithMarkdown(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        return sendMessage;
    }

    public SendMessage getMsgWithMarkdownV2(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdownV2(true);
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        return sendMessage;
    }

    public SendMessage getMsgHTML(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);

        return sendMessage;
    }


    public SendMessage getMsgWithoutMarkup(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        return sendMessage;
    }


    public SendPhoto getPhoto(String chatId, String caption, PhotoSize photoSize) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(caption);

        GetFile getFile = new GetFile();
        getFile.setFileId(photoSize.getFileId());
        InputFile inputFile = new InputFile();
        inputFile.setMedia(getFile.getFileId());
        sendPhoto.setPhoto(inputFile);

        return sendPhoto;
    }


    public SendAnimation getGif(String chatId, String caption, Animation animation) {
        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setChatId(chatId);
        sendAnimation.setCaption(caption);

        GetFile getFile = new GetFile();
        getFile.setFileId(animation.getFileId());
        InputFile inputFile = new InputFile();
        inputFile.setMedia(getFile.getFileId());
        sendAnimation.setAnimation(inputFile);

        return sendAnimation;
    }


    public SendVideo getVideo(String chatId, String caption, Video video) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        sendVideo.setCaption(caption);

        GetFile getFile = new GetFile();
        getFile.setFileId(video.getFileId());

        InputFile inputFile = new InputFile();
        inputFile.setMedia(getFile.getFileId());
        sendVideo.setVideo(inputFile);

        return sendVideo;
    }

}

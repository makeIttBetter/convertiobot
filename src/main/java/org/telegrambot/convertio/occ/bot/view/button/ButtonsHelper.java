package org.telegrambot.convertio.occ.bot.view.button;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@Component
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ButtonsHelper {
    @Value("${updateCheckmarks}")
    @Setter
    private String updateCheckmarks;


    public void setClientsKeyboard(SendMessage sendMessage, ResourceBundle resourceBundle) {
        setCommonMainKeyboard(sendMessage, resourceBundle);
    }

    private void setCommonMainKeyboard(SendMessage sendMessage, ResourceBundle resourceBundle) {
        addReplyButtonDown(sendMessage, resourceBundle.getString("searchFileType.button"));
        addReplyButtonRight(sendMessage, resourceBundle.getString("listFileType.button"));
        addReplyButtonDown(sendMessage, resourceBundle.getString("infoButton"));
        addReplyButtonRight(sendMessage, resourceBundle.getString("settingsButton"));
    }


    public void setAdminsKeyboardFirst(SendMessage sendMessage, ResourceBundle resourceBundle) {
        setCommonMainKeyboard(sendMessage, resourceBundle);
        addReplyButtonDown(sendMessage, resourceBundle.getString("adminROOTButton"));
    }

    /**
     * Устанавливает кнопки для Админа 2
     *
     * @param sendMessage
     * @param resourceBundle
     * @return
     */
    public void setAdminsKeyboardSecond(SendMessage sendMessage, ResourceBundle resourceBundle) {
        addReplyButtonDown(sendMessage, resourceBundle.getString("statisticsButton"));
        addReplyButtonDown(sendMessage, resourceBundle.getString("advertisingButton"));
        addReplyButtonRight(sendMessage, resourceBundle.getString("adminStatButton"));
        addReplyButtonDown(sendMessage, resourceBundle.getString("adminVillainListButton"));
        addReplyButtonRight(sendMessage, resourceBundle.getString("blockUserButton"));
        addReplyButtonDown(sendMessage, resourceBundle.getString("adminsManageButton"));
        addReplyButtonDown(sendMessage, resourceBundle.getString("adminCLIENTButton"));
    }


    public EditMessageText addInlineButtonRedirectDown(EditMessageText editMessageText, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (editMessageText.getReplyMarkup() != null) {
            markupInline = editMessageText.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectDown(markupInline, text, link);

        editMessageText.setReplyMarkup(markupInline);

        return editMessageText; // Sending our editMessageText to user
    }


    public EditMessageReplyMarkup addInlineButtonRedirectDown
            (EditMessageReplyMarkup editMessageReplyMarkup, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (editMessageReplyMarkup.getReplyMarkup() != null) {
            markupInline = editMessageReplyMarkup.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectDown(markupInline, text, link);

        editMessageReplyMarkup.setReplyMarkup(markupInline);

        return editMessageReplyMarkup; // Sending our editMessageReplyMarkup to user
    }


    public void addInlineButtonRedirectDown(SendMessage sendMessage, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendMessage.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendMessage.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectDown(markupInline, text, link);

        sendMessage.setReplyMarkup(markupInline);

    }


    public SendAudio addInlineButtonRedirectDown(SendAudio sendAudio, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendAudio.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAudio.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectDown(markupInline, text, link);

        sendAudio.setReplyMarkup(markupInline);

        return sendAudio; // Sending our sendMessage to user
    }


    public SendPhoto addInlineButtonRedirectDown(SendPhoto sendPhoto, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendPhoto.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendPhoto.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectDown(markupInline, text, link);

        sendPhoto.setReplyMarkup(markupInline);

        return sendPhoto; // Sending our sendPhoto to user
    }


    public SendAnimation addInlineButtonRedirectDown(SendAnimation sendAnimation, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendAnimation.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAnimation.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectDown(markupInline, text, link);

        sendAnimation.setReplyMarkup(markupInline);

        return sendAnimation; // Sending our sendAnimation to user
    }


    public SendVideo addInlineButtonRedirectDown(SendVideo sendVideo, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendVideo.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendVideo.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }
        addButtonRedirectDown(markupInline, text, link);

        sendVideo.setReplyMarkup(markupInline);

        return sendVideo; // Sending our sendVideo to user
    }


    public void addInlineButtonRedirectRight(SendMessage sendMessage, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendMessage.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendMessage.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectRight(markupInline, text, link);
        sendMessage.setReplyMarkup(markupInline);

    }


    public EditMessageText addInlineButtonRedirectRight(EditMessageText editMessageText, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (editMessageText.getReplyMarkup() != null) {
            markupInline = editMessageText.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectRight(markupInline, text, link);

        editMessageText.setReplyMarkup(markupInline);

        return editMessageText; // Sending our editMessageText to user
    }


    public EditMessageReplyMarkup addInlineButtonRedirectRight
            (EditMessageReplyMarkup editMessageReplyMarkup, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (editMessageReplyMarkup.getReplyMarkup() != null) {
            markupInline = editMessageReplyMarkup.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectRight(markupInline, text, link);

        editMessageReplyMarkup.setReplyMarkup(markupInline);

        return editMessageReplyMarkup; // Sending our editMessageText to user
    }


    public SendAudio addInlineButtonRedirectRight(SendAudio sendAudio, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendAudio.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAudio.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectRight(markupInline, text, link);

        sendAudio.setReplyMarkup(markupInline);

        return sendAudio; // Sending our sendMessage to user
    }


    public SendPhoto addInlineButtonRedirectRight(SendPhoto sendPhoto, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendPhoto.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendPhoto.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectRight(markupInline, text, link);

        sendPhoto.setReplyMarkup(markupInline);

        return sendPhoto; // Sending our sendPhoto to user
    }


    public SendAnimation addInlineButtonRedirectRight(SendAnimation sendAnimation, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendAnimation.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAnimation.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectRight(markupInline, text, link);

        sendAnimation.setReplyMarkup(markupInline);

        return sendAnimation; // Sending our sendAnimation to user
    }


    public SendVideo addInlineButtonRedirectRight(SendVideo sendVideo, String text, String link) {
        InlineKeyboardMarkup markupInline;
        if (sendVideo.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendVideo.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addButtonRedirectRight(markupInline, text, link);

        sendVideo.setReplyMarkup(markupInline);

        return sendVideo; // Sending our sendVideo to user
    }


    public SendMessage addInlineButtonDown(SendMessage sendMessage, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendMessage.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendMessage.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonDown(markupInline, text, callbackData);

        sendMessage.setReplyMarkup(markupInline);

        return sendMessage; // Sending our sendMessage to user
    }


    public SendPoll addInlineButtonDown(SendPoll sendPoll, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendPoll.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendPoll.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonDown(markupInline, text, callbackData);

        sendPoll.setReplyMarkup(markupInline);

        return sendPoll; // Sending our sendMessage to user
    }


    public EditMessageMedia addInlineButtonDown(EditMessageMedia editMessageMedia, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (editMessageMedia.getReplyMarkup() != null) {
            markupInline = editMessageMedia.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }
        addInlineButtonDown(markupInline, text, callbackData);

        editMessageMedia.setReplyMarkup(markupInline);

        return editMessageMedia; // Sending our sendMessage to user
    }


    public EditMessageText addInlineButtonDown(EditMessageText editMessageText, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (editMessageText.getReplyMarkup() != null) {
            markupInline = editMessageText.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonDown(markupInline, text, callbackData);

        editMessageText.setReplyMarkup(markupInline);

        return editMessageText; // Sending our editMessageText to user
    }


    public EditMessageReplyMarkup addInlineButtonDown
            (EditMessageReplyMarkup messageReplyMarkup, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (messageReplyMarkup.getReplyMarkup() != null) {
            markupInline = messageReplyMarkup.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonDown(markupInline, text, callbackData);

        messageReplyMarkup.setReplyMarkup(markupInline);

        return messageReplyMarkup; // Sending our messageReplyMarkup to user
    }


    public SendPhoto addInlineButtonDown(SendPhoto sendPhoto, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendPhoto.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendPhoto.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonDown(markupInline, text, callbackData);

        sendPhoto.setReplyMarkup(markupInline);

        return sendPhoto; // Sending our sendPhoto to user
    }


    public SendVideo addInlineButtonDown(SendVideo sendVideo, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendVideo.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendVideo.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonDown(markupInline, text, callbackData);

        sendVideo.setReplyMarkup(markupInline);

        return sendVideo; // Sending our sendPhoto to user
    }


    public SendAnimation addInlineButtonDown(SendAnimation sendAnimation, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendAnimation.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAnimation.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonDown(markupInline, text, callbackData);

        sendAnimation.setReplyMarkup(markupInline);

        return sendAnimation; // Sending our sendPhoto to user
    }


    public SendAudio addInlineButtonDown(SendAudio sendAudio, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendAudio.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAudio.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }
        addInlineButtonDown(markupInline, text, callbackData);

        sendAudio.setReplyMarkup(markupInline);

        return sendAudio; // Sending our sendAudio to user
    }


    public void addReplyButtonDown(SendMessage sendMessage, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if (sendMessage.getReplyMarkup() != null) {
            replyKeyboardMarkup = (ReplyKeyboardMarkup) sendMessage.getReplyMarkup();
        } else {
            replyKeyboardMarkup = new ReplyKeyboardMarkup();
        }

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        addReplyButtonDown(replyKeyboardMarkup, text);

    }


    public void addReplyButtonDown(SendVideo sendVideo, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if (sendVideo.getReplyMarkup() != null) {
            replyKeyboardMarkup = (ReplyKeyboardMarkup) sendVideo.getReplyMarkup();
        } else {
            replyKeyboardMarkup = new ReplyKeyboardMarkup();
        }

        sendVideo.setReplyMarkup(replyKeyboardMarkup);
        addReplyButtonDown(replyKeyboardMarkup, text);

    }


    public void addReplyButtonDown(SendPhoto sendPhoto, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if (sendPhoto.getReplyMarkup() != null) {
            replyKeyboardMarkup = (ReplyKeyboardMarkup) sendPhoto.getReplyMarkup();
        } else {
            replyKeyboardMarkup = new ReplyKeyboardMarkup();
        }

        sendPhoto.setReplyMarkup(replyKeyboardMarkup);
        addReplyButtonDown(replyKeyboardMarkup, text);

    }


    public void addReplyButtonRight(SendMessage sendMessage, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if (sendMessage.getReplyMarkup() != null) {
            replyKeyboardMarkup = (ReplyKeyboardMarkup) sendMessage.getReplyMarkup();
        } else {
            replyKeyboardMarkup = new ReplyKeyboardMarkup();
        }

        addReplyButtonRight(replyKeyboardMarkup, text);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

    }


    public void addReplyButtonRight(SendPhoto sendPhoto, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if (sendPhoto.getReplyMarkup() != null) {
            replyKeyboardMarkup = (ReplyKeyboardMarkup) sendPhoto.getReplyMarkup();
        } else {
            replyKeyboardMarkup = new ReplyKeyboardMarkup();
        }

        addReplyButtonRight(replyKeyboardMarkup, text);

        sendPhoto.setReplyMarkup(replyKeyboardMarkup);
    }


    public void addReplyButtonRight(SendVideo sendVideo, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if (sendVideo.getReplyMarkup() != null) {
            replyKeyboardMarkup = (ReplyKeyboardMarkup) sendVideo.getReplyMarkup();
        } else {
            replyKeyboardMarkup = new ReplyKeyboardMarkup();
        }

        addReplyButtonRight(replyKeyboardMarkup, text);
        sendVideo.setReplyMarkup(replyKeyboardMarkup);
    }


    public SendMessage addInlineButtonRight(SendMessage sendMessage, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendMessage.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendMessage.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);

        sendMessage.setReplyMarkup(markupInline);

        return sendMessage; // Sending our sendMessage to user
    }


    public SendPoll addInlineButtonRight(SendPoll sendPoll, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendPoll.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendPoll.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);

        sendPoll.setReplyMarkup(markupInline);

        return sendPoll; // Sending our sendMessage to user
    }


    public EditMessageMedia addInlineButtonRight(EditMessageMedia editMessageMedia, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (editMessageMedia.getReplyMarkup() != null) {
            markupInline = editMessageMedia.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);

        editMessageMedia.setReplyMarkup(markupInline);

        return editMessageMedia; // Sending our editMessageMedia to user
    }


    public EditMessageText addInlineButtonRight
            (EditMessageText editMessageText, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (editMessageText.getReplyMarkup() != null) {
            markupInline = editMessageText.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);

        editMessageText.setReplyMarkup(markupInline);

        return editMessageText; // Sending our editMessageText to user
    }


    public SendAudio addInlineButtonRight(SendAudio sendAudio, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendAudio.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAudio.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);

        sendAudio.setReplyMarkup(markupInline);

        return sendAudio; // Sending our sendMessage to user
    }


    public SendPhoto addInlineButtonRight(SendPhoto sendPhoto, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendPhoto.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendPhoto.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);

        sendPhoto.setReplyMarkup(markupInline);

        return sendPhoto; // Sending our sendMessage to user
    }


    public SendVideo addInlineButtonRight(SendVideo sendVideo, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendVideo.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendVideo.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);

        sendVideo.setReplyMarkup(markupInline);

        return sendVideo; // Sending our sendMessage to user
    }


    public SendAnimation addInlineButtonRight
            (SendAnimation sendAnimation, String text, String callbackData) {
        InlineKeyboardMarkup markupInline;
        if (sendAnimation.getReplyMarkup() != null) {
            markupInline = (InlineKeyboardMarkup) sendAnimation.getReplyMarkup();
        } else {
            markupInline = new InlineKeyboardMarkup();
        }

        addInlineButtonRight(markupInline, text, callbackData);
        sendAnimation.setReplyMarkup(markupInline);

        return sendAnimation; // Sending our sendMessage to user
    }


//    --------------------------------------------------------------------------------------------


    public void addButtonRedirectDown(InlineKeyboardMarkup markupInline, String text, String link) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        if (markupInline.getKeyboard() != null) {
            rowsInline = new ArrayList<>(markupInline.getKeyboard());
        }

        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setUrl(link);

        rowInline.add(inlineKeyboardButton);
        rowsInline.add(rowInline);

        // Add it to the sendVideo
        markupInline.setKeyboard(rowsInline);

    }


    public void addButtonRedirectRight(InlineKeyboardMarkup markupInline, String text, String link) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        if (markupInline.getKeyboard() != null) {
            rowsInline = new ArrayList<>(markupInline.getKeyboard());
        }

        List<InlineKeyboardButton> rowInline;
        if (rowsInline.size() > 0) {
            rowInline = rowsInline.remove(rowsInline.size() - 1);
        } else {
            rowInline = new ArrayList<>();
        }

        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setUrl(link);

        rowInline.add(inlineKeyboardButton);
        rowsInline.add(rowInline);

        // Add it to the editMessageText
        markupInline.setKeyboard(rowsInline);

    }

    public void addInlineButtonDown(InlineKeyboardMarkup markupInline, String text, String data) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        if (markupInline.getKeyboard() != null) {
            rowsInline = new ArrayList<>(markupInline.getKeyboard());
        }

        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(data);

        rowInline.add(inlineKeyboardButton);
        rowsInline.add(rowInline);

        // Add it to the sendMessage
        markupInline.setKeyboard(rowsInline);

    }


    public void addInlineButtonRight(InlineKeyboardMarkup markupInline, String text, String data) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        if (markupInline.getKeyboard() != null) {
            rowsInline = new ArrayList<>(markupInline.getKeyboard());
        }

        List<InlineKeyboardButton> rowInline;
        if (rowsInline.size() > 0) {
            rowInline = rowsInline.remove(rowsInline.size() - 1);
        } else {
            rowInline = new ArrayList<>();
        }


        InlineKeyboardButton inlineKeyboardButton =
                new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(data);

        rowInline.add(inlineKeyboardButton);

        rowsInline.add(rowInline);

        // Add it to the sendMessage
        markupInline.setKeyboard(rowsInline);

    }


    public void addReplyButtonRight(ReplyKeyboardMarkup replyKeyboardMarkup, String text) {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        if (replyKeyboardMarkup.getKeyboard() != null) {
            keyboardRowList
                    = new ArrayList<>(replyKeyboardMarkup.getKeyboard());
        }

        KeyboardRow rowInline;
        if (keyboardRowList.size() > 0) {
            rowInline = keyboardRowList.remove(keyboardRowList.size() - 1);
        } else {
            rowInline = new KeyboardRow();
        }

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(text);
        rowInline.add(keyboardButton);
        keyboardRowList.add(rowInline);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void addReplyButtonDown(ReplyKeyboardMarkup replyKeyboardMarkup, String text) {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        if (replyKeyboardMarkup.getKeyboard() != null) {
            keyboardRowList
                    = new ArrayList<>(replyKeyboardMarkup.getKeyboard());
        }

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(text));

        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }


//    --------------------------------------------------------------------------------------------


    public InlineKeyboardMarkup updateCheckMarks
            (String data, InlineKeyboardMarkup keyboardMarkup) {
        log.info(updateCheckmarks);

        // keyboard changing
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        for (List<InlineKeyboardButton> row : keyboardMarkup.getKeyboard()) {
            List<InlineKeyboardButton> rowInLine = new ArrayList<>();
            for (InlineKeyboardButton button : row) {

                String buttonText = button.getText();
                String buttonCallbackData = button.getCallbackData();

                if (buttonCallbackData.equals(data) && !buttonText.contains("✅")) {
                    InlineKeyboardButton inlineKeyboardButton =
                            new InlineKeyboardButton();
                    inlineKeyboardButton.setText("✅" + buttonText);
                    inlineKeyboardButton.setCallbackData(buttonCallbackData);

                    rowInLine.add(inlineKeyboardButton);
                } else if (buttonCallbackData.equals(data) && buttonText.contains("✅")) {
                    InlineKeyboardButton inlineKeyboardButton =
                            new InlineKeyboardButton();
                    inlineKeyboardButton.setText
                            (buttonText.replace("✅", ""));
                    inlineKeyboardButton.setCallbackData(buttonCallbackData);

                    rowInLine.add(inlineKeyboardButton);
                } else {
                    rowInLine.add(button);
                }
            }
            rowsInline.add(rowInLine);
        }

        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }


    public String getChosenResultsToString(InlineKeyboardMarkup keyboardMarkup) {
        StringBuilder result = new StringBuilder();


        for (List<InlineKeyboardButton> row : keyboardMarkup.getKeyboard()) {
            for (InlineKeyboardButton button : row) {
                String buttonText = button.getText();
                if (buttonText.contains("✅")) {
                    String text = buttonText
                            .replace("✅", "");
                    result.append(text).append(", ");
                }
            }
        }

        return result.substring(0, result.length() - 2);
    }


    public <T> byte addSelectButton(InlineKeyboardMarkup markup,
                                    byte currentAmount, byte buttonsInLineLimit,
                                    Collection<T> selectedValues, T value,
                                    String text, String data) {

        if (selectedValues.contains(value)) {
            text = "✅" + text;
        }
        if (currentAmount < buttonsInLineLimit) {
            addInlineButtonRight(markup, text, data);
            currentAmount++;
        } else {
            currentAmount = 1;
            addInlineButtonDown(markup, text, data);

        }

        return currentAmount;
    }

}

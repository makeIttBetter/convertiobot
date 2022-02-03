package org.telegrambot.convertio.occ.bot.entities;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.io.Serializable;
import java.util.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BotResponseWsDTO {
    @EqualsAndHashCode.Include
    private final Deque<PartialBotApiMethod<? extends Serializable>> methods = new LinkedList<>();
    @Getter
    @Setter
    private boolean stateChanged;
    @Getter
    @Setter
    @EqualsAndHashCode.Include
    private State state;
    @Getter
    @Setter
    private boolean undo;
    @Getter
    @Setter
    private boolean deleteKeyboard;
    @Getter
    @Setter
    private boolean answerCallbackQueryPresent;

    public void add(PartialBotApiMethod<? extends Serializable> telegramMethod) {
        if (telegramMethod instanceof EditMessageReplyMarkup
                || telegramMethod instanceof EditMessageText
                || telegramMethod instanceof DeleteMessage) {
            methods.addFirst(telegramMethod);
        } else {
            methods.addLast(telegramMethod);
        }
    }

    public boolean containsSuchType(Class<? extends PartialBotApiMethod<? extends Serializable>> clazz) {
        return methods.stream().anyMatch((m) -> clazz.equals(m.getClass()));
    }

    public boolean containsSuchTypes(Set<Class<? extends PartialBotApiMethod<? extends Serializable>>> classes) {
        return methods.stream().anyMatch((m) -> classes.contains(m.getClass()));
    }

    public void addAll(List<PartialBotApiMethod<? extends Serializable>> telegramMethods) {
        telegramMethods.forEach(this::add);
    }

    public void addAll(Set<PartialBotApiMethod<? extends Serializable>> telegramMethods) {
        telegramMethods.forEach(this::add);
    }

    public void remove(PartialBotApiMethod<? extends Serializable> telegramMethod) {
        methods.remove(telegramMethod);
    }

    public void clear() {
        methods.clear();
    }

    public Set<PartialBotApiMethod<? extends Serializable>> getApiMethods() {
        return new LinkedHashSet<>(methods);
    }
}

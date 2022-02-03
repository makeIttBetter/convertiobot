package org.telegrambot.convertio.occ.bot.commands;

import org.telegrambot.convertio.exceptions.commands.CommandException;
import org.telegrambot.convertio.exceptions.facades.FacadeException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Processes main inline and keyboard commands.
 * Doesn't depends on user-bot state.
 */
public interface Command {

    /**
     * Main method that will execute user action
     *
     * @param botContext class that contains parameters
     *                   to use in this method
     * @return list of telegram api methods to execute
     */
    Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) throws CommandException, FacadeException;

    /**
     * Method that lets us know what Requests this command can process
     *
     * @return State that current handler can process
     */
    Predicate<BotContextWsDTO> predicate();

    /**
     * Method that lets us know what State this handler can process
     *
     * @return State that current handler can process
     */
    State operatedState();

    /**
     * Method that lets us know what State this handler will return
     *
     * @return State that current handler can process
     */
    State returnedState();

    /**
     * Method where parameters are initialised
     *
     * @param botContext class that contains parameters
     *                   to use in this method
     */
    void init(BotContextWsDTO botContext);


}

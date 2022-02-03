package org.telegrambot.convertio.occ.bot.commands.test;

import org.telegrambot.convertio.exceptions.commands.CommandException;
import org.telegrambot.convertio.facades.data.user.UserData;
import org.telegrambot.convertio.occ.bot.commands.AbstractCommand;
import org.telegrambot.convertio.occ.bot.entities.BotContextWsDTO;
import org.telegrambot.convertio.occ.bot.entities.BotResponseWsDTO;
import org.telegrambot.convertio.occ.bot.entities.State;

import java.util.Map;
import java.util.function.Predicate;

public class ConvertFileCommand extends AbstractCommand {

//    apikey		String	Your API Key
//input		String	Method of providing the input file.
//Default Value:url
//Allowed Values: url,raw,base64,upload
//file		String	URL of the input file (if input=url), or file content (if input = raw/base64)
//filename	optional	String	Input filename including extension (file.ext). Required if input = raw/base64
//outputformat		String	Output format, to which the file should be converted to.
//options	optional	Object	Conversion options. Now used to define callback URL, enable OCR and setting up its options. You may find available OCR conversion options here and callback example here.


    @Override
    public Map<UserData, BotResponseWsDTO> execute(BotContextWsDTO botContext) throws CommandException {
        return null;
    }

    @Override
    public Predicate<BotContextWsDTO> predicate() {
        return null;
    }

    @Override
    public State operatedState() {
        return null;
    }

    @Override
    public State returnedState() {
        return null;
    }
}

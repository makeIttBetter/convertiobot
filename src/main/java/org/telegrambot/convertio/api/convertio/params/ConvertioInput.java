package org.telegrambot.convertio.api.convertio.params;

import org.telegrambot.convertio.api.annotations.JsonParam;
import org.telegrambot.convertio.api.convertio.consts.ConvertioRequestParams;

import javax.ws.rs.DefaultValue;


public interface ConvertioInput {
    @JsonParam(ConvertioRequestParams.INPUT)
    @DefaultValue(value = "url")
    String getInput();
}

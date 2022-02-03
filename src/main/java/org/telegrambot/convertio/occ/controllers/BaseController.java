package org.telegrambot.convertio.occ.controllers;

import org.json.JSONObject;

public abstract class BaseController {
    /**
     * Method that processes notification's info
     * from different APIs which uses current App.
     *
     * @param input Input content from external service
     */
    abstract void execute(JSONObject input);
}

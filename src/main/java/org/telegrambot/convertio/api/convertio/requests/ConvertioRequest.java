package org.telegrambot.convertio.api.convertio.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.api.ApiRequest;
import org.telegrambot.convertio.exceptions.apis.ApiException;

@Getter
@NoArgsConstructor
@Component
public abstract class ConvertioRequest extends ApiRequest {
    private String apiPath;

    @Override
    public JSONObject toJson() throws ApiException {
        return null;
    }
}

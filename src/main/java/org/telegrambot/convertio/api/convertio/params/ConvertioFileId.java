package org.telegrambot.convertio.api.convertio.params;

import org.telegrambot.convertio.api.annotations.JsonParam;
import org.telegrambot.convertio.api.convertio.consts.ConvertioRequestParams;

public interface ConvertioFileId {
    @JsonParam(ConvertioRequestParams.FILE_ID)
    String getFileId();
}

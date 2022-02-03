package org.telegrambot.convertio.api.convertio.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.telegrambot.convertio.api.convertio.params.ConvertioFileId;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class GetConvertionStatus extends ConvertioRequest implements ConvertioFileId {

    private String fileId;

}

package org.telegrambot.convertio.api.convertio.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.telegrambot.convertio.api.convertio.params.*;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class ConvertFileRequest extends ConvertioRequest implements ConvertioInput, ConvertioFile, ConvertioFileName, ConvertioOutputFormat, ConvertioOptions {

    private String input;
    private String file;
    private String fileName;
    private String outputFormat;
    private String options;

}

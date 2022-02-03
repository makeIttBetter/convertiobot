package org.telegrambot.convertio.api.convertio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum InputFileType {
//    url,raw,base64,upload
    URL("url"),
    RAW("raw"),
    BASE64("base64"),
    UPLOAD("upload"),
    ;

    private final String name;

}

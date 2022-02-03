package org.telegrambot.convertio.facades.data.channel;

import lombok.*;
import org.telegrambot.convertio.core.model.channel.ChannelType;


@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChannelData {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String chatId;

    @Getter
    @Setter
    private String channelTitle;

    @Getter
    @Setter
    private String channelLink;

    @Getter
    @Setter
    private ChannelType channelType;


}
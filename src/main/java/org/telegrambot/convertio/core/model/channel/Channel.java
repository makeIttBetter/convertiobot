package org.telegrambot.convertio.core.model.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "channels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Channel extends Model {

    private static final long serialVersionUID = -8791115098086415433L;

    @Column(name = "chatId", nullable = false)
    private String chatId;

    @Column(name = "channelTitle", nullable = false)
    private String channelTitle;

    @Column(name = "channelLink")
    private String channelLink;

    @Column(name = "channelType", nullable = false)
    private ChannelType channelType;


}
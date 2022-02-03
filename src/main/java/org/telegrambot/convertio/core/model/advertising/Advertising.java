package org.telegrambot.convertio.core.model.advertising;

import lombok.*;
import org.telegrambot.convertio.core.model.Model;
import org.telegrambot.convertio.core.model.advertising.enums.FileType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "advertising")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Advertising extends Model {

    private static final long serialVersionUID = -6475919989663740801L;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private FileType fileType;

    @Column(length = 1024)
    @EqualsAndHashCode.Include
    private String text;

    @Column(length = 300)
    private String fileId;

    @OneToMany(targetEntity = Button.class,
            mappedBy = "advertising", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @Builder.Default
    private List<Button> buttons = Collections.synchronizedList(new ArrayList<>());


}

package org.telegrambot.convertio.core.model.files;

import lombok.*;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "files")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class File extends Model {

    private static final long serialVersionUID = -5274157094729267060L;

    @Column(nullable = false, unique = true, length = 200)
    @EqualsAndHashCode.Include
    private String fileId;

    @Column(name = "link", length = 500)
    private String downloadLink;


}

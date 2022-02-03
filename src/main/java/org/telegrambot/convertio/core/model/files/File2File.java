package org.telegrambot.convertio.core.model.files;

import lombok.*;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.*;

@Entity
@Table(name = "file2file", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"type", "writeable_type"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class File2File extends Model {

    private static final long serialVersionUID = 7126455457317020908L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type")
    private FileType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "writeable_type")
    private FileType writeableType;

}

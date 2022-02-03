package org.telegrambot.convertio.core.model.files;

import lombok.*;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.*;

@Entity
@Table(name = "file_types")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class FileType extends Model {

    private static final long serialVersionUID = -3971978814728944989L;

    @Column(nullable = false, unique = true, length = 50)
    @EqualsAndHashCode.Include
    private String code;

    @Column(nullable = false)
    private boolean readable;

    @Column(nullable = false)
    private boolean writeable;

    @Column(nullable = false, unique = true, length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", nullable = false)
    private FileCategory category;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "file2file",
//            joinColumns = @JoinColumn(name = "type", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "writeable_type",
//                    referencedColumnName = "id", table = "file2file"))
//    private Set<FileType> writableTypes = Collections.synchronizedSet(new HashSet<>());
}

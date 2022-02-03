package org.telegrambot.convertio.core.model.files;

import lombok.*;
import org.telegrambot.convertio.core.model.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "file_categories")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class FileCategory extends Model {

    private static final long serialVersionUID = -2672059578354435461L;

    @Column(nullable = false, unique = true, length = 200)
    @EqualsAndHashCode.Include
    private String name;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
//    private Set<FileType> fileTypes = Collections.synchronizedSet(new HashSet<>());

}

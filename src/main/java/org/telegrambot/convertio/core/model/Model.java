package org.telegrambot.convertio.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public abstract class Model implements Serializable {
    private static final long serialVersionUID = 7945147474269998569L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Model() {
    }

    public Model(int id) {
        this.id = id;
    }
}

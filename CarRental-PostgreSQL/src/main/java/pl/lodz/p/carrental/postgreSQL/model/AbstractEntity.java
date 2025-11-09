package pl.lodz.p.carrental.postgreSQL.model;

import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractEntity implements Serializable {

    @Version
    @Column(name = "version")
    private long version;
}
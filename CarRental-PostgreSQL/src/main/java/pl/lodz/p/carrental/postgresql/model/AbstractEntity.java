package pl.lodz.p.carrental.postgresql.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Version
    @Column(name = "version")
    private long version;

    public UUID getId() {
        return id;
    }
}
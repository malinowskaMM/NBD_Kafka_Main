package pl.nbd.hotel.abstractEntity;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

//    @Embedded
//    @NotNull
//    private UUID id;

    @Version
    private long version;
}

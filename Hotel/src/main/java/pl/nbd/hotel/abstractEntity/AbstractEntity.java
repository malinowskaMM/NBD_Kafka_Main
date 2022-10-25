package pl.nbd.hotel.abstractEntity;

import jakarta.persistence.Version;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractEntity implements Serializable {

    @BsonProperty("_id")
    private final UUID uuid;

    public AbstractEntity(UUID uuid) {
        this.uuid = uuid;
    }
}

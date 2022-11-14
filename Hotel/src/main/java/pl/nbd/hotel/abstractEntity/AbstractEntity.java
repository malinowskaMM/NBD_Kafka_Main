package pl.nbd.hotel.abstractEntity;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.hotel.db.UniqueId;

import java.io.Serializable;


public abstract class AbstractEntity implements Serializable {

    @BsonProperty("_id")
    private final UniqueId uniqueEntityId;

    public UniqueId getUniqueId() {
        return uniqueEntityId;
    }

    public AbstractEntity(UniqueId uuid) {
        this.uniqueEntityId = uuid;
    }
}

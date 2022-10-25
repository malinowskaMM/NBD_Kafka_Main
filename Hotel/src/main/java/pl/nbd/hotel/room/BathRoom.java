package pl.nbd.hotel.room;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
public class BathRoom extends Room {

    @BsonCreator
    public BathRoom( @BsonProperty("_id") UUID uuid,
                       @BsonProperty("roomNumber") String roomNumber,
                       @BsonProperty("price") Double price,
                       @BsonProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("bathType") bathType bathType) {
        super(uuid, roomNumber, price, roomCapacity);
        this.bathType = bathType;
    }

    @BsonProperty("bathType")
    bathType bathType;

}

package pl.nbd.hotel.room;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@BsonDiscriminator(key="_clazz", value="bath")
public class BathRoom extends Room {

    @BsonCreator
    public BathRoom(
                       @BsonProperty("roomNumber") String roomNumber,
                       @BsonProperty("price") Double price,
                       @BsonProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("bathType") bathType bathType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomCapacity = roomCapacity;
        this.bathType = bathType;
    }

    @BsonProperty("bathType")
    bathType bathType;


}

package pl.nbd.hotel.room;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.hotel.abstractEntity.AbstractEntity;

import java.util.UUID;

@Getter
public abstract class Room extends AbstractEntity {

    @BsonCreator
    public Room(@BsonProperty("_id") UUID uuid,
                @BsonProperty("roomNumber") String roomNumber,
                @BsonProperty("price") Double price,
                @BsonProperty("roomCapacity") Integer roomCapacity
                ) {
        super(uuid);
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomCapacity = roomCapacity;
    }

    @BsonProperty("roomNumber")
    String roomNumber;

    @BsonProperty("price")
    Double price;

    @BsonProperty("roomCapacity")
    Integer roomCapacity;

    public String getRoomInfo() {
        return roomNumber.concat(" ").concat(price.toString()).concat(" ").concat(roomCapacity.toString());
    }
}

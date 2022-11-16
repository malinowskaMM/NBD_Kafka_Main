package pl.nbd.hotel.room;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Getter
@BsonDiscriminator(key="_cls", value = "abstract")
public abstract class Room implements Serializable {

    @BsonCreator
    public Room(@BsonProperty("roomNumber") String roomNumber,
                @BsonProperty("price") Double price,
                @BsonProperty("roomCapacity") Integer roomCapacity) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomCapacity = roomCapacity;
    }

    @BsonId
    String roomNumber;

    @BsonProperty("price")
    Double price;

    @BsonProperty("roomCapacity")
    Integer roomCapacity;



    public String getRoomInfo() {
        return roomNumber.concat(" ").concat(price.toString()).concat(" ").concat(roomCapacity.toString());
    }
}

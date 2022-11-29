package pl.nbd.hotel.room;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Getter
@Setter
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
    @Size(max = 12)
    String roomNumber;

    @NotNull
    @PositiveOrZero
    @BsonProperty("price")
    Double price;

    @NotNull
    @Positive
    @BsonProperty("roomCapacity")
    Integer roomCapacity;

    public String roomInfoGet() {
        return roomNumber.concat(" ").concat(price.toString()).concat(" ").concat(roomCapacity.toString());
    }
}

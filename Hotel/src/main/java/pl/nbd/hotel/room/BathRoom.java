package pl.nbd.hotel.room;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.*;

@Getter
@BsonDiscriminator(key="_cls", value="bathroom")
public class BathRoom extends Room {

    @BsonCreator
    public BathRoom(
                       @BsonId String roomNumber,
                       @BsonProperty("price") Double price,
                       @BsonProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("bathType") bathType bathType) {
        super(roomNumber, price, roomCapacity);
        this.bathType = bathType;
    }

    @BsonProperty(value = "bathType")
    bathType bathType;

    public String getRoomInfo() {
        return super.getRoomInfo().concat(" ").concat(bathType.name());
    }
}

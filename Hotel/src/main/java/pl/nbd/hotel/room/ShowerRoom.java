package pl.nbd.hotel.room;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@BsonDiscriminator(key="_clazz", value="shower")
public class ShowerRoom extends Room {

    @BsonCreator
    public ShowerRoom(
                       @BsonProperty("roomNumber") String roomNumber,
                       @BsonProperty("price") Double price,
                       @BsonProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("withShelf") Boolean withShelf) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomCapacity = roomCapacity;
        this.withShelf = withShelf;
    }

    @BsonProperty("withShelf")
    Boolean withShelf;

    public String getRoomInfo() {
        return super.getRoomInfo().concat("with shelf ").concat(String.valueOf(withShelf));
    }
}

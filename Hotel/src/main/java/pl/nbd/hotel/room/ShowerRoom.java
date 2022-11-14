package pl.nbd.hotel.room;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
public class ShowerRoom extends Room {

    @BsonCreator
    public ShowerRoom(
                       @BsonProperty("roomNumber") String roomNumber,
                       @BsonProperty("price") Double price,
                       @BsonProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("withShelf") boolean withShelf) {
        super(roomNumber, price, roomCapacity);
        this.withShelf = withShelf;
    }

    @BsonProperty("withShelf")
    boolean withShelf;

    public String getRoomInfo() {
        return super.getRoomInfo().concat("with shelf ").concat(String.valueOf(withShelf));
    }
}

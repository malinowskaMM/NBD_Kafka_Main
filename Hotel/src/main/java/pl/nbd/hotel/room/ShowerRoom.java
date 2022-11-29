package pl.nbd.hotel.room;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@BsonDiscriminator(key="_cls", value="showerroom")
public class ShowerRoom extends Room {

    @BsonCreator
    public ShowerRoom(
                       @BsonId String roomNumber,
                       @BsonProperty("price") Double price,
                       @BsonProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("withShelf") Boolean withShelf) {
        super(roomNumber, price, roomCapacity);
        this.withShelf = withShelf;
    }

    @BsonProperty(value = "withShelf")
    Boolean withShelf;

    public String roomInfoGet() {
        return super.roomInfoGet().concat("with shelf ").concat(String.valueOf(withShelf));
    }
}

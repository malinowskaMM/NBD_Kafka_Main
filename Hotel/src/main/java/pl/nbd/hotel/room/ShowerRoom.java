package pl.nbd.hotel.room;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@BsonDiscriminator(key="_cls", value="showerroom")
public class ShowerRoom extends Room {

    @BsonCreator
    @JsonbCreator
    public ShowerRoom(
                       @BsonId @JsonbProperty("roomNumber") String roomNumber,
                       @BsonProperty("price") @JsonbProperty("price") Double price,
                       @BsonProperty("roomCapacity") @JsonbProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("withShelf") @JsonbProperty("withShelf") Boolean withShelf) {
        super(roomNumber, price, roomCapacity);
        this.withShelf = withShelf;
    }

    @BsonProperty(value = "withShelf")
    @JsonbProperty(value = "withShelf")
    Boolean withShelf;

    public String roomInfoGet() {
        return super.roomInfoGet().concat("with shelf ").concat(String.valueOf(withShelf));
    }
}

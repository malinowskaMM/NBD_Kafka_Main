package pl.nbd.hotel.room;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.*;

@Getter
@BsonDiscriminator(key = "_cls", value = "bathroom")
public class BathRoom extends Room {

    @BsonCreator
    @JsonbCreator
    public BathRoom(
            @BsonId @JsonbProperty("roomNumber")
                    String roomNumber,
            @BsonProperty("price") @JsonbProperty("price")
                    Double price,
            @BsonProperty("roomCapacity") @JsonbProperty("roomCapacity")
                    Integer roomCapacity,
            @BsonProperty("bathType") @JsonbProperty("bathType")
                    bathType bathType) {
        super(roomNumber, price, roomCapacity);
        this.bathType = bathType;
    }

    @BsonProperty(value = "bathType")
    bathType bathType;

    public String roomInfoGet() {
        return super.roomInfoGet().concat(" ").concat(bathType.name());
    }
}

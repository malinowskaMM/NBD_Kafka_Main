package pl.nbd.hotel.room;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
public class ShowerRoom extends Room {

    @BsonCreator
    public ShowerRoom( @BsonProperty("_id") UUID uuid,
                       @BsonProperty("roomNumber") String roomNumber,
                       @BsonProperty("price") Double price,
                       @BsonProperty("roomCapacity") Integer roomCapacity,
                       @BsonProperty("withShelf") boolean withShelf) {
        super(uuid, roomNumber, price, roomCapacity);
        this.withShelf = withShelf;
    }

    @BsonProperty("withShelf")
    boolean withShelf;

    public String getRoomInfo() {
        return super.getRoomInfo().concat("with shelf ").concat(String.valueOf(withShelf));
    }
}

package pl.nbd.hotel.rent;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.room.Room;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Rent implements Serializable {

    @BsonId
    @JsonbProperty("_id")
    UUID id;

    @NotNull
    @BsonProperty("beginTime")
    @JsonbProperty("beginTime")
    LocalDateTime beginTime;

    @NotNull
    @BsonProperty("endTime")
    @JsonbProperty("endTime")
    LocalDateTime endTime;

    @PositiveOrZero
    @NotNull
    @BsonProperty("rentCost")
    @JsonbProperty("rentCost")
    Double rentCost;

    @BsonProperty("client")
    @JsonbProperty("client")
    Client client;

    @BsonProperty(value = "room", useDiscriminator = true)
    @JsonbProperty("room")
    Room room;

    public String rentInfoGet() {
        return id.toString().concat(" ").concat(beginTime.toString()).concat(" ").concat(endTime.toString()).concat(" ").concat(rentCost.toString()).concat(" ").concat(client.clientInfoGet()).concat(" ").concat(room.roomInfoGet());
    }

    @BsonCreator
    @JsonbCreator
    public Rent(
            @BsonId @JsonbProperty("_id")
                    UUID id,
            @BsonProperty("beginTime") @JsonbProperty("beginTime")
                    LocalDateTime beginTime,
            @BsonProperty("endTime") @JsonbProperty("endTime")
                    LocalDateTime endTime,
            @BsonProperty("client") @JsonbProperty("client")
                    Client client,
            @BsonProperty("room") @JsonbProperty("room")
                    Room room,
            @BsonProperty("rentCost") @JsonbProperty("rentCost")
                    Double rentCost) {
        this.id = id;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.client = client;
        this.room = room;
        this.rentCost = rentCost;
    }

    public void changeEndTime(LocalDateTime newEndTime) {
        endTime = newEndTime;
    }
}

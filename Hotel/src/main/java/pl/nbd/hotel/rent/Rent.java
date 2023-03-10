package pl.nbd.hotel.rent;

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
    UUID id;

    @NotNull
    @BsonProperty("beginTime")
    LocalDateTime beginTime;

    @NotNull
    @BsonProperty("endTime")
    LocalDateTime endTime;

    @PositiveOrZero
    @NotNull
    @BsonProperty("rentCost")
    Double rentCost;

    @BsonProperty("client")
    Client client;

    @BsonProperty(value = "room", useDiscriminator = true)
    Room room;

    public String rentInfoGet() {
        return id.toString().concat(" ").concat(beginTime.toString()).concat(" ").concat(endTime.toString()).concat(" ").concat(rentCost.toString()).concat(" ").concat(client.clientInfoGet()).concat(" ").concat(room.roomInfoGet());
    }

    @BsonCreator
    public Rent(
            @BsonId UUID id,
                @BsonProperty("beginTime") LocalDateTime beginTime,
                @BsonProperty("endTime") LocalDateTime endTime,
                @BsonProperty("client") Client client,
                @BsonProperty("room") Room room,
                @BsonProperty("rentCost") Double rentCost) {
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

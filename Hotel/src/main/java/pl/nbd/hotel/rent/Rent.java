package pl.nbd.hotel.rent;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.hotel.abstractEntity.AbstractEntity;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.room.Room;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Rent extends AbstractEntity {

    @BsonProperty("id")
    UUID id;

    @BsonProperty("beginTime")
    LocalDateTime beginTime;

    @BsonProperty("endTime")
    LocalDateTime endTime;

    @BsonProperty("rentCost")
    Double rentCost;

    @BsonProperty("client")
    Client client;

    @BsonProperty("room")
    Room room;

    public String getRentInfo() {
        return id.toString().concat(" ").concat(beginTime.toString()).concat(" ").concat(endTime.toString()).concat(" ").concat(rentCost.toString()).concat(" ").concat(client.getClientInfo()).concat(" ").concat(room.getRoomInfo());
    }

    @BsonCreator
    public Rent(@BsonProperty("_id") UUID uuid,
                @BsonProperty("id") UUID id,
                @BsonProperty("beginTime") LocalDateTime beginTime,
                @BsonProperty("endTime") LocalDateTime endTime,
                @BsonProperty("client") Client client,
                @BsonProperty("room") Room room,
                @BsonProperty("rentCost") Double rentCost) {
        super(uuid);
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

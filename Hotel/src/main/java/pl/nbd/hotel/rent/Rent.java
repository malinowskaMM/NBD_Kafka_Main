package pl.nbd.hotel.rent;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import pl.nbd.hotel.abstractEntity.AbstractEntity;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.room.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rent extends AbstractEntity {

    @Id
    @NotNull
    @Column(name = "ID")
    UUID id;

    @NotNull
    @Column(name = "BEGIN_TIME", nullable = false, columnDefinition = "TIMESTAMP CHECK (END_TIME > BEGIN_TIME)")
    LocalDateTime beginTime;

    @NotNull
    @Column(name = "END_TIME", nullable = false, columnDefinition = "TIMESTAMP CHECK (END_TIME > BEGIN_TIME)")
    LocalDateTime endTime;

    @Column(name = "RENT_COST", nullable = false, columnDefinition = "FLOAT CHECK (RENT_COST >= 0)")
    @PositiveOrZero
    @NotNull
    Double rentCost;

    @ManyToOne
    @JoinColumn(name = "PERSONAL_ID", nullable = false)
    Client client;

    @ManyToOne
    @JoinColumn(name = "ROOM_NUMBER", nullable = false)
    Room room;

    public String getRentInfo() {
        return id.toString().concat(" ").concat(beginTime.toString()).concat(" ").concat(endTime.toString()).concat(" ").concat(rentCost.toString()).concat(" ").concat(client.getClientInfo()).concat(" ").concat(room.getRoomInfo());
    }

    public Rent(UUID id, LocalDateTime beginTime, LocalDateTime endTime, Client client, Room room, Double rentCost) {
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

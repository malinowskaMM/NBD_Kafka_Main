package pl.nbd.hotel.rent;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.room.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Rent {

    @Id
    @NotNull
    @Column(name = "ID")
    UUID id;

    @NotNull
    @Column(name = "BEGIN_TIME", nullable = false, columnDefinition = "TIMESTAMP CHECK (END_TIME > BEGIN_TIME)")
    LocalDateTime beginTime;

    @NotNull
    @Column(name = "END_TIME", nullable = false, columnDefinition = "TIMESTAMP CHECK (END_TIME > BEGIN_TIME)")
    LocalDate endTime;

    @Column(name = "RENT_COST", nullable = false, columnDefinition = "INTEGER CHECK (RENT_COST >= 0)")
    @PositiveOrZero
    @NotNull
    Double rentCost;

    @ManyToOne
    @JoinColumn(name = "PERSONAL_ID", nullable = false)
    Client client;

    @ManyToOne
    @JoinColumn(name = "ROOM_NUMBER", nullable = false)
    Room room;
}

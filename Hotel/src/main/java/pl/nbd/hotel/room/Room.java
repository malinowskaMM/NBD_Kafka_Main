package pl.nbd.hotel.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @Column(name = "ROOM_NUMBER")
    String roomNumber;

    @NotNull
    @Column(name = "BASE_PRICE")
    Integer basePrice;

    @NotNull
    @Column(name = "ROOM_CAPACITY")
    Integer roomCapacity;
}

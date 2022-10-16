package pl.nbd.hotel.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="BATHROOM_TYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class Room {

    @Id
    @Column(name = "ROOM_NUMBER")
    String roomNumber;

    @NotNull
    @Column(name = "PRICE")
    Double PRICE;

    @NotNull
    @Column(name = "ROOM_CAPACITY")
    Integer roomCapacity;

}

package pl.nbd.hotel.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="BATHROOM_TYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class Room {

    @Id
    @Size(max = 12)
    @Column(name = "ROOM_NUMBER", columnDefinition = "VARCHAR(12)")
    String roomNumber;

    @NotNull
    @PositiveOrZero
    @Column(name = "PRICE", nullable = false, columnDefinition = "DOUBLE PRECISION CHECK (PRICE >= 0)")
    Double price;

    @NotNull
    @Positive
    @Column(name = "ROOM_CAPACITY", nullable = false, columnDefinition = "INTEGER CHECK (ROOM_CAPACITY > 0)")
    Integer roomCapacity;

    public String getRoomInfo() {
        return roomNumber.concat(" ").concat(price.toString()).concat(" ").concat(roomCapacity.toString());
    }
}

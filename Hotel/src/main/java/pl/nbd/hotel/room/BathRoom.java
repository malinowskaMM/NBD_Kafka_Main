package pl.nbd.hotel.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("BATH")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BathRoom extends Room {

    @Enumerated(EnumType.STRING)
    //@Size(max = 20)
    @Column(name = "BATH_TYPE", length = 20)
    bathType bathType;

    public BathRoom(@Size(max = 12) String roomNumber, @NotNull @PositiveOrZero Double price, @NotNull @Positive Integer roomCapacity, pl.nbd.hotel.room.bathType bathType) {
        super(roomNumber, price, roomCapacity);
        this.bathType = bathType;
    }

    //    public float getActualRentalPrice() {
//
//    }

}

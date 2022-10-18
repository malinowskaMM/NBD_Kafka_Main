package pl.nbd.hotel.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("BATH")
public class BathRoom extends Room {

    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(name = "BATH_TYPE", length = 20)
    bathType bathType;

//    public float getActualRentalPrice() {
//
//    }

}

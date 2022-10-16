package pl.nbd.hotel.room;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BATH")
public class BathRoom extends Room {

    @Column(name = "BATH_TYPE")
    String type;

}

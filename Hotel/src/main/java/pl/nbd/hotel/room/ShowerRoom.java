package pl.nbd.hotel.room;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SHOWER")
public class ShowerRoom extends Room {

    @Column(name = "WITH_SHELF")
    boolean withShelf;

}

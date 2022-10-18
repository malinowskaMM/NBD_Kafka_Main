package pl.nbd.hotel.room;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SHOWER")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowerRoom extends Room {

    @Column(name = "WITH_SHELF")
    boolean withShelf;

    public String getRoomInfo() {
        return super.getRoomInfo().concat("with shelf ").concat(String.valueOf(withShelf));
    }

//    public float getActualRentalPrice() {
//
//    }

}

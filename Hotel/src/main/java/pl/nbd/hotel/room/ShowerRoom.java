package pl.nbd.hotel.room;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SHOWER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowerRoom extends Room {

    @Column(name = "WITH_SHELF")
    boolean withShelf;

    public ShowerRoom(@Size(max = 12) String roomNumber, @NotNull @PositiveOrZero Double price, @NotNull @Positive Integer roomCapacity, boolean withShelf) {
        super(roomNumber, price, roomCapacity);
        this.withShelf = withShelf;
    }

    public String getRoomInfo() {
        return super.getRoomInfo().concat("with shelf ").concat(String.valueOf(withShelf));
    }
}

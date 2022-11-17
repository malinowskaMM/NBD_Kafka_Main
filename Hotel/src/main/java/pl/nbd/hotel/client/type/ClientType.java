package pl.nbd.hotel.client.type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientType {

    @Enumerated(EnumType.STRING)
    @BsonProperty
    public ClientTypeName clientTypeName;

    @NotNull
    @PositiveOrZero
    @BsonProperty
    public Integer discount;

    public String clientTypeInfoGet() {
        return clientTypeName.name().concat(" ").concat(discount.toString());
    }

    public double applyDiscount(double price) {
        return price - (price * discount * 0.01);
    }
}

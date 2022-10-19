package pl.nbd.hotel.client.type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientType {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "CLIENT_TYPE_NAME", nullable = false, length = 20)
    public ClientTypeName clientTypeName;

    @NotNull
    @PositiveOrZero
    @Column(name = "DISCOUNT", nullable = false, columnDefinition = "INTEGER CHECK (DISCOUNT >= 0)")
    public Integer discount;

    public String getClientTypeInfo() {
        return clientTypeName.name().concat(" ").concat(discount.toString());
    }

    public double applyDiscount(double price) {
        return price - (price * discount * 0.01);
    }
}

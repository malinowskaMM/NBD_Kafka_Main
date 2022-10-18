package pl.nbd.hotel.client.type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientType {

    @Id
    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(name = "CLIENT_TYPE_NAME", nullable = false, length = 20)
    private ClientTypeName clientTypeName;

    @NotNull
    @PositiveOrZero
    @Column(name = "DISCOUNT", nullable = false, columnDefinition = "INTEGER CHECK (DISCOUNT >= 0)")
    public Integer discount;

    public String getClientTypeInfo() {
        return clientTypeName.name().concat(" ").concat(discount.toString());
    }

    public float applyDiscount(int price) {
        return discount * price;
    }
}

package pl.nbd.hotel.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.nbd.hotel.abstractEntity.AbstractEntity;
import pl.nbd.hotel.client.type.ClientType;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends AbstractEntity {

    @Id
    @Size(min = 11, max = 11)
    @Column(name = "PERSONAL_ID", length = 11)
    String personalId;

    @NotNull
    @Size(max = 35)
    @Column(name = "FIRST_NAME", nullable = false, length = 35)
    String firstName;

    @NotNull
    @Size(max = 35)
    @Column(name = "LAST_NAME", nullable = false, length = 35)
    String lastName;

    Address address;

    @Setter
    @NotNull
    @PositiveOrZero
    @Column(name = "MONEY_SPENT", nullable = false, columnDefinition = "FLOAT CHECK (MONEY_SPENT >= 0)")
    Double moneySpent;

    @ManyToOne
    @JoinColumn(name = "CLIENT_TYPE_NAME", nullable = false)
    ClientType clientType;

    public String getClientInfo() {
        return personalId.concat(" ").concat(firstName).concat(" ").concat(lastName).concat(" ").concat(address.getAddressInfo()).concat(" ").concat(clientType.getClientTypeInfo());
    }

    public double applyDiscount(double price) {
        return clientType.applyDiscount(price);
    }

}

package pl.nbd.hotel.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.nbd.hotel.abstractEntity.AbstractEntity;
import pl.nbd.hotel.client.type.ClientType;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends AbstractEntity {

    @Id
    @Size(max = 11)
    @Column(name = "PERSONAL_ID", length = 11)
    String personalId;

    @NotNull
    @Size(max = 35)
    @Column(name = "FIRST_NAME", nullable = false, length = 35)
    String firstName;

    @Size(max = 35)
    @Column(name = "LAST_NAME", length = 35)
    String lastName;

    Address address;

    @ManyToOne
    @JoinColumn(name = "CLIENT_TYPE_NAME", nullable = false)
    ClientType clientType;

    public String getClientInfo() {
        return personalId.toString().concat(" ").concat(firstName).concat(" ").concat(lastName).concat(" ").concat(address.getAddressInfo()).concat(" ").concat(clientType.getClientTypeInfo());
    }

    public float applyDiscount(int price) {
        return clientType.applyDiscount(price);
    }

}

package pl.nbd.hotel.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.nbd.hotel.client.type.ClientType;

@Entity
@Getter
@Setter
public class Client {

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

}

package pl.nbd.hotel.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.nbd.hotel.client.type.ClientType;

@Entity
@Getter
@Setter
public class Client {

    @Id
    @Column(name = "PERSONAL_ID")
    String personalId;

    @NotNull
    @Column(name = "FIRST_NAME")
    String firstName;

    @Column(name = "LAST_NAME")
    String lastName;

    @NotNull
    Address address;

    @ManyToOne
    @JoinColumn(name = "CLIENT_TYPE_NAME")
    ClientType clientType;

}

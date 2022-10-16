package pl.nbd.hotel.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @Enumerated
    @NotNull
    @Column(name = "CLIENT_TYPE")
    ClientType clientType;
}

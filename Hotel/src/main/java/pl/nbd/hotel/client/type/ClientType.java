package pl.nbd.hotel.client.type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class ClientType {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "CLIENT_TYPE_NAME")
    private ClientTypeName clientTypeName;

    @NotNull
    @Column(name = "DISCOUNT")
    private int discount;
}

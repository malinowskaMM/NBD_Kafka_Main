package pl.nbd.hotel.client.type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class ClientType {

    @Id
    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(name = "CLIENT_TYPE_NAME", nullable = false, length = 20)
    private ClientTypeName clientTypeName;

    @NotNull
    @PositiveOrZero
    @Column(name = "DISCOUNT", nullable = false, columnDefinition = "INTEGER CHECK (DISCOUNT >= 0)")
    private Integer discount;
}

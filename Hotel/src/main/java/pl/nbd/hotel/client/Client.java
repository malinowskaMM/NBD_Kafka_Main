package pl.nbd.hotel.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.hotel.client.type.ClientType;

import java.io.Serializable;

@Getter
@Setter
public class Client implements Serializable {

    @BsonCreator
    public Client(@BsonProperty("personalId") String personalId,
                  @BsonProperty("firstName") String firstName,
                  @BsonProperty("lastName") String lastName,
                  @BsonProperty("address") Address address,
                  @BsonProperty("moneySpent") Double moneySpent,
                  @BsonProperty("clientType") ClientType clientType
                  ) {
        super();
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.moneySpent = moneySpent;
        this.clientType = clientType;
    }

    @BsonProperty("personalId")
    @Size(min = 11, max = 11)
    @NotNull
    String personalId;

    @BsonProperty("firstName")
    @Size(max = 35)
    @NotNull
    String firstName;

    @NotNull
    @Size(max = 35)
    @BsonProperty("lastName")
    String lastName;

    @NotNull
    @BsonProperty("address")
    Address address;

    @NotNull
    @PositiveOrZero
    @BsonProperty("moneySpent")
    Double moneySpent;

    @BsonProperty("clientType")
    ClientType clientType;

    public String clientInfoGet() {
        return personalId.concat(" ").concat(firstName).concat(" ").concat(lastName).concat(" ").concat(address.addressInfoGet()).concat(" ").concat(clientType.clientTypeInfoGet());
    }

    public double applyDiscount(double price) {
        return clientType.applyDiscount(price);
    }

    public void setMoneySpent(double v) {
        this.moneySpent = v;
    }
}

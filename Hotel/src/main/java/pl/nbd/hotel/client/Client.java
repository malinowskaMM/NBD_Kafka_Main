package pl.nbd.hotel.client;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
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
    @JsonbCreator
    public Client(@BsonProperty("personalId") @JsonbProperty("personalId")
                          String personalId,
                  @BsonProperty("firstName") @JsonbProperty("firstName")
                          String firstName,
                  @BsonProperty("lastName") @JsonbProperty("lastName")
                          String lastName,
                  @BsonProperty("address") @JsonbProperty("address")
                          Address address,
                  @BsonProperty("moneySpent") @JsonbProperty("moneySpent")
                          Double moneySpent,
                  @BsonProperty("clientType") @JsonbProperty("clientType")
                          ClientType clientType
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
    @JsonbProperty("personalId")
    @Size(min = 11, max = 11)
    @NotNull
    String personalId;

    @BsonProperty("firstName")
    @JsonbProperty("firstName")
    @Size(max = 35)
    @NotNull
    String firstName;

    @NotNull
    @Size(max = 35)
    @BsonProperty("lastName")
    @JsonbProperty("lastName")
    String lastName;

    @NotNull
    @BsonProperty("address")
    @JsonbProperty("address")
    Address address;

    @NotNull
    @PositiveOrZero
    @BsonProperty("moneySpent")
    @JsonbProperty("moneySpent")
    Double moneySpent;

    @BsonProperty("clientType")
    @JsonbProperty("clientType")
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

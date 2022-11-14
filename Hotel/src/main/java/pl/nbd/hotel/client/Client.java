package pl.nbd.hotel.client;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.hotel.abstractEntity.AbstractEntity;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.db.UniqueId;

@Getter
public class Client extends AbstractEntity {

    @BsonCreator
    public Client(@BsonProperty("_id") UniqueId uuid,
                  @BsonProperty("personalId") String personalId,
                  @BsonProperty("firstName") String firstName,
                  @BsonProperty("lastName") String lastName,
                  @BsonProperty("address") Address address,
                  @BsonProperty("moneySpent") Double moneySpent,
                  @BsonProperty("clientType") ClientType clientType
                  ) {
        super(uuid);
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.moneySpent = moneySpent;
        this.clientType = clientType;
    }
    @BsonProperty("personalId")
    String personalId;

    @BsonProperty("firstName")
    String firstName;

    @BsonProperty("lastName")
    String lastName;

    @BsonProperty("address")
    Address address;

    @BsonProperty("moneySpent")
    Double moneySpent;

    @BsonProperty("clientType")
    ClientType clientType;

    public String getClientInfo() {
        return personalId.concat(" ").concat(firstName).concat(" ").concat(lastName).concat(" ").concat(address.getAddressInfo()).concat(" ").concat(clientType.getClientTypeInfo());
    }

    public double applyDiscount(double price) {
        return clientType.applyDiscount(price);
    }

    public void setMoneySpent(double v) {
        this.moneySpent = v;
    }
}

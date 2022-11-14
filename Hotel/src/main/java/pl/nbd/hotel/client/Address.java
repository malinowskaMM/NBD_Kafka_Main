package pl.nbd.hotel.client;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.hotel.abstractEntity.AbstractEntity;
import pl.nbd.hotel.db.UniqueId;

@Getter
public class Address extends AbstractEntity {

    @BsonCreator
    public Address(@BsonProperty("_id") UniqueId uuid,
                   @BsonProperty("street") String street,
                   @BsonProperty("streetNumber") String streetNumber,
                   @BsonProperty("cityName") String cityName,
                   @BsonProperty("postalCode") String postalCode
    ) {
        super(uuid);
        this.street = street;
        this.streetNumber = streetNumber;
        this.cityName = cityName;
        this.postalCode = postalCode;
    }

    @BsonProperty("street")
    private String street;

    @BsonProperty("streetNumber")
    private String streetNumber;

    @BsonProperty("cityName")
    private String cityName;

    @BsonProperty("postalCode")
    private String postalCode;

    public String getAddressInfo() {
        return street.concat(" ").concat(streetNumber).concat(" ").concat(cityName).concat(" ").concat(postalCode);    }
}

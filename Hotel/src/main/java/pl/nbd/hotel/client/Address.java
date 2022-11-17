package pl.nbd.hotel.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Getter
public class Address implements Serializable {

    @BsonCreator
    public Address(@BsonProperty("street") String street,
                   @BsonProperty("streetNumber") String streetNumber,
                   @BsonProperty("cityName") String cityName,
                   @BsonProperty("postalCode") String postalCode
    ) {
        super();
        this.street = street;
        this.streetNumber = streetNumber;
        this.cityName = cityName;
        this.postalCode = postalCode;
    }

    @NotNull
    @Size(max = 96)
    @BsonProperty("street")
    private String street;

    @NotNull
    @Size(max = 30)
    @BsonProperty("streetNumber")
    private String streetNumber;

    @NotNull
    @Size(max = 40)
    @BsonProperty("cityName")
    private String cityName;

    @NotNull
    @Pattern(regexp = "^\\d{2}-\\d{3}$")
    @BsonProperty("postalCode")
    private String postalCode;

    public String addressInfoGet() {
        return street.concat(" ").concat(streetNumber).concat(" ").concat(cityName).concat(" ").concat(postalCode);    }
}

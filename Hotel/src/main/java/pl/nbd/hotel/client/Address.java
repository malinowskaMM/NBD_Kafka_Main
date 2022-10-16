package pl.nbd.hotel.client;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotNull
    @Column(name = "STREET")
    private String street;

    @NotNull
    @Column(name = "STREET_NUMBER")
    private String streetNumber;

    @NotNull
    @Column(name = "CITY_NAME")
    private String cityName;

    @NotNull
    @Column(name = "POSTAL_CODE")
    private String postalCode;
}

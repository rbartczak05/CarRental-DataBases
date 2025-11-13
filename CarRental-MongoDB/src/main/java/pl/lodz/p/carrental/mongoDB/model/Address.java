package pl.lodz.p.carrental.mongoDB.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

public class Address {

    @BsonProperty("_id")
    private UUID id;

    @BsonProperty("street")
    private String street;

    @BsonProperty("house_number")
    private String houseNumber;

    @BsonProperty("city")
    private String city;

    @BsonProperty("postal_code")
    private String postalCode;

    public Address(String street, String houseNumber, String city, String postalCode) {
        this.id = UUID.randomUUID();
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + getId() +
                ", postalCode='" + getPostalCode() + '\'' +
                ", city='" + getCity() + '\'' +
                ", houseNumber='" + getHouseNumber() + '\'' +
                ", street='" + getStreet() + '\'' +
                "}";
    }
}

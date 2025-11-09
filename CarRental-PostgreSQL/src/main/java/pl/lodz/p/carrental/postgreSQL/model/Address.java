package pl.lodz.p.carrental.postgreSQL.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "address")
@Access(AccessType.FIELD)
public class Address extends AbstractEntity {

    @Id
    @Column(name = "addressId")
    private UUID addressId;
    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private String houseNumber;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_code")
    private String postalCode;

    public Address(String street, String houseNumber, String city, String postalCode) {
        this.addressId = UUID.randomUUID();
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {

    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID id) {
        this.addressId = id;
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
                "street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}

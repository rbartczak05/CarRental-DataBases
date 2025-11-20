package pl.lodz.p.carrental.postgresql.model;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
@Access(AccessType.FIELD)
public class Address extends AbstractEntity {

    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private String houseNumber;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_code")
    private String postalCode;

    public Address(String street, String houseNumber, String city, String postalCode) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {

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

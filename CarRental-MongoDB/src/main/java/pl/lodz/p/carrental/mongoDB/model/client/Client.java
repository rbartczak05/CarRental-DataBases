package pl.lodz.p.carrental.mongoDB.model.client;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz")
public abstract class Client {

    @BsonProperty("_id")
    private UUID id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("email")
    private String email;

    @BsonProperty("balance")
    private double balance;

    @BsonProperty("addressId")
    private UUID addressId;

    public Client(String name, String email, double balance, UUID addressId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.addressId = addressId;
    }

    public Client() {

    }

    @BsonIgnore
    public abstract ClientType getClientType();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void substractBalance(double amount) {
        this.balance -= amount;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "id=" + getId() +
                ", name=" + getName() +
                ", email=" + getEmail() +
                ", balance=" + getBalance() +
                ", addressId=" + getAddressId() +
                ", clientType=" + getClientType().getMaxVehicles() +
                ", discount=" + getClientType().getDiscount();
    }
}

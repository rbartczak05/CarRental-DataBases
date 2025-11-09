package pl.lodz.p.carrental.redis.model.client;

import pl.lodz.p.carrental.redis.model.Address;
import pl.lodz.p.carrental.redis.model.client.ClientType;

import java.util.UUID;

public abstract class Client {

    private final UUID clientId;
    private String name;
    private String email;
    private ClientType clientType;
    private double balance;
    private Address address;

    public Client(String name, String email, double balance, Address address) {
        this.clientId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.address = address;
    }

    public UUID getClientId() {
        return clientId;
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

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", clientType=" + clientType +
                ", balance=" + balance +
                ", address=" + address +
                '}';
    }
}

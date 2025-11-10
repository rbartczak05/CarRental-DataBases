package pl.lodz.p.carrental.postgreSQL.model.client;

import jakarta.persistence.*;
import pl.lodz.p.carrental.postgreSQL.model.AbstractEntity;
import pl.lodz.p.carrental.postgreSQL.model.Address;
import pl.lodz.p.carrental.postgreSQL.model.Rent;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "client_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "client")
@Access(AccessType.FIELD)
public abstract class Client extends AbstractEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "balance")
    private double balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "client_type", insertable = false, updatable = false)
    private ClientType clientType;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "client", cascade = CascadeType.MERGE)
    private List<Rent> rents = new ArrayList<>();

    public Client(String name, String email, double balance, Address address) {
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.address = address;
    }

    public Client() {

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

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void addRent(Rent rent) {
        rents.add(rent);
    }

    @Override
    public String toString() {
        return "id=" + getId() +
                ", name=" + getName() +
                ", email=" + getEmail() +
                ", balance=" + getBalance() +
                ", address=" + getAddress() +
                ", clientType=" + getClientType().getMaxVehicles() +
                ", discount=" + getClientType().getDiscount();
    }
}

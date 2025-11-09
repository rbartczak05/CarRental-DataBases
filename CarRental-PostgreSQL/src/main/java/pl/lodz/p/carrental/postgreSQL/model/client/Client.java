package pl.lodz.p.carrental.postgreSQL.model.client;

import jakarta.persistence.*;
import pl.lodz.p.carrental.postgreSQL.model.AbstractEntity;
import pl.lodz.p.carrental.postgreSQL.model.Address;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "client_type")
@Table(name = "client")
@Access(AccessType.FIELD)
public abstract class Client extends AbstractEntity {

    @Id
    @Column(name = "clientId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID clientId;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "clientType")
    private ClientType clientType;
    @Column(name = "balance")
    private double balance;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "addressId")
    private Address address;

    public Client(String name, String email, double balance, Address address) {
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.address = address;
    }

    public Client() {

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

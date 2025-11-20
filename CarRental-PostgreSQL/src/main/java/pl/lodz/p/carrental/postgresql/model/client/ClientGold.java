package pl.lodz.p.carrental.postgresql.model.client;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.carrental.postgresql.model.Address;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("GOLD")
public class ClientGold extends Client {

    public ClientGold(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.GOLD);
    }

    public ClientGold() {

    }

    @Override
    public String toString() {
        return "ClientGold{" + super.toString() + '}';
    }
}

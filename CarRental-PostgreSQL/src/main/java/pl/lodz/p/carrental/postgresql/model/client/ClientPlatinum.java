package pl.lodz.p.carrental.postgresql.model.client;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.carrental.postgresql.model.Address;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("PLATINUM")
public class ClientPlatinum extends Client {

    public ClientPlatinum(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.PLATINUM);
    }

    public ClientPlatinum() {

    }

    @Override
    public String toString() {
        return "ClientPlatinum{" + super.toString() + '}';
    }
}

package pl.lodz.p.carrental.postgreSQL.model.client;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.carrental.postgreSQL.model.Address;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("client_platinum")
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

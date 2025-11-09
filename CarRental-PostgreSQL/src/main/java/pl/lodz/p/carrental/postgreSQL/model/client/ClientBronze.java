package pl.lodz.p.carrental.postgreSQL.model.client;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.carrental.postgreSQL.model.Address;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("client_bronze")
public class ClientBronze extends Client {

    public ClientBronze(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.BRONZE);
    }

    public ClientBronze() {

    }

    @Override
    public String toString() {
        return "ClientBronze{" + super.toString() + '}';
    }
}

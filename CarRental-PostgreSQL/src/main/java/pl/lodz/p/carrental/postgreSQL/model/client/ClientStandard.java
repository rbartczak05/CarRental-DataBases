package pl.lodz.p.carrental.postgreSQL.model.client;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.carrental.postgreSQL.model.Address;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("STANDARD")
public class ClientStandard extends Client {

    public ClientStandard(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.STANDARD);
    }

    public ClientStandard() {

    }

    @Override
    public String toString() {
        return "ClientStandard{" + super.toString() + '}';
    }
}

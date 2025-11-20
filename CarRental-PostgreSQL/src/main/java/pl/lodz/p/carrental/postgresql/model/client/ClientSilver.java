package pl.lodz.p.carrental.postgresql.model.client;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.carrental.postgresql.model.Address;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("SILVER")
public class ClientSilver extends Client {

    public ClientSilver(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.SILVER);
    }

    public ClientSilver() {

    }

    @Override
    public String toString() {
        return "ClientSilver{" + super.toString() + '}';
    }
}

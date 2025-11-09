package pl.lodz.p.carrental.postgreSQL.model.client;

import pl.lodz.p.carrental.postgreSQL.model.Address;
import pl.lodz.p.carrental.postgreSQL.model.client.Client;
import pl.lodz.p.carrental.postgreSQL.model.client.ClientType;

public class ClientSilver extends Client {

    public ClientSilver(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.SILVER);
    }

    @Override
    public String toString() {
        return "ClientSilver{" + super.toString() + '}';
    }
}

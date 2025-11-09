package pl.lodz.p.carrental.cassandra.model.client;

import pl.lodz.p.carrental.cassandra.model.Address;

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

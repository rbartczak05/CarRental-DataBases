package pl.lodz.p.carrental.cassandra.model.client;

import pl.lodz.p.carrental.cassandra.model.Address;

public class ClientGold extends Client {

    public ClientGold(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.GOLD);
    }

    @Override
    public String toString() {
        return "ClientGold{" + super.toString() + '}';
    }
}

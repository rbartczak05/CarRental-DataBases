package pl.lodz.p.carrental.postgreSQL.model.client;

import pl.lodz.p.carrental.postgreSQL.model.Address;
import pl.lodz.p.carrental.postgreSQL.model.client.ClientType;

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

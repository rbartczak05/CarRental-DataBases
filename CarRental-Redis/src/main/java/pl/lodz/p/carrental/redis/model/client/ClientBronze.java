package pl.lodz.p.carrental.redis.model.client;

import pl.lodz.p.carrental.redis.model.Address;

public class ClientBronze extends Client {

    public ClientBronze(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.BRONZE);
    }

    @Override
    public String toString() {
        return "ClientBronze{" + super.toString() + '}';
    }
}

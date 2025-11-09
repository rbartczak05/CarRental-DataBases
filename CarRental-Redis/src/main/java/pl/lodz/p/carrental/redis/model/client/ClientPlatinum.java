package pl.lodz.p.carrental.redis.model.client;

import pl.lodz.p.carrental.redis.model.Address;

public class ClientPlatinum extends Client {

    public ClientPlatinum(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.PLATINUM);
    }

    @Override
    public String toString() {
        return "ClientPlatinum{" + super.toString() + '}';
    }
}

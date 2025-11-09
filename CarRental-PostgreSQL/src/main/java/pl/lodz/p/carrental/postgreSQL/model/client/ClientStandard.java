package pl.lodz.p.carrental.postgreSQL.model.client;

import pl.lodz.p.carrental.postgreSQL.model.Address;

public class ClientStandard extends Client {

    public ClientStandard(String name, String email, double balance, Address address) {
        super(name, email, balance, address);
        setClientType(ClientType.STANDARD);
    }

    @Override
    public String toString() {
        return "ClientStandard{" + super.toString() + '}';
    }
}

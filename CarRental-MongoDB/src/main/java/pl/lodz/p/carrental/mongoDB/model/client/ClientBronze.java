package pl.lodz.p.carrental.mongoDB.model.client;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "ClientBronze")
public class ClientBronze extends Client {

    public ClientBronze(String name, String email, double balance, UUID addressId) {
        super(name, email, balance, addressId);
    }

    public ClientBronze() {

    }

    @BsonIgnore
    @Override
    public ClientType getClientType() {
        return ClientType.BRONZE;
    }

    @Override
    public String toString() {
        return "ClientBronze{" + super.toString() + '}';
    }
}

package pl.lodz.p.carrental.mongoDB.model.client;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "ClientPlatinum")
public class ClientPlatinum extends Client {

    public ClientPlatinum(String name, String email, double balance, UUID addressId) {
        super(name, email, balance, addressId);
    }

    public ClientPlatinum() {

    }

    @BsonIgnore
    @Override
    public ClientType getClientType() {
        return ClientType.PLATINUM;
    }

    @Override
    public String toString() {
        return "ClientPlatinum{" + super.toString() + '}';
    }
}

package pl.lodz.p.carrental.redis.model.client;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "ClientStandard")
public class ClientStandard extends Client {

    public ClientStandard(String name, String email, double balance, UUID addressId) {
        super(name, email, balance, addressId);
    }

    public ClientStandard() {

    }

    @BsonIgnore
    @Override
    public ClientType getClientType() {
        return ClientType.STANDARD;
    }

    @Override
    public String toString() {
        return "ClientStandard{" + super.toString() + '}';
    }
}

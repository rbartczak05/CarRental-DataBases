package pl.lodz.p.carrental.mongoDB.model.client;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "ClientSilver")
public class ClientSilver extends Client {

    public ClientSilver(String name, String email, double balance, UUID addressId) {
        super(name, email, balance, addressId);
    }

    public ClientSilver() {

    }

    @BsonIgnore
    @Override
    public ClientType getClientType() {
        return ClientType.SILVER;
    }

    @Override
    public String toString() {
        return "ClientSilver{" + super.toString() + '}';
    }
}

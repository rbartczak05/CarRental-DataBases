package pl.lodz.p.carrental.mongoDB.model.client;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "ClientGold")
public class ClientGold extends Client {

    public ClientGold(String name, String email, double balance, UUID addressId) {
        super(name, email, balance, addressId);
    }

    public ClientGold() {

    }

    @BsonIgnore
    @Override
    public ClientType getClientType() {
        return ClientType.GOLD;
    }

    @Override
    public String toString() {
        return "ClientGold{" + super.toString() + '}';
    }
}

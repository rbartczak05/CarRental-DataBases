package pl.lodz.p.carrental.mongoDB.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import pl.lodz.p.carrental.mongoDB.manager.DatabaseManager;
import pl.lodz.p.carrental.mongoDB.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddressRepository {

    private final MongoCollection<Address> collection;

    public AddressRepository(DatabaseManager dbManager) {
        this.collection = dbManager.getDatabase().getCollection("addresses", Address.class);
    }

    public Address save(Address address) {
        collection.replaceOne(Filters.eq("_id", address.getId()), address, new ReplaceOptions().upsert(true));
        return address;
    }

    public void deleteById(UUID id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    public Optional<Address> findById(UUID id) {
        return Optional.ofNullable(collection.find(Filters.eq("_id", id)).first());
    }

    public List<Address> findAll() {
        return collection.find().into(new ArrayList<>());
    }
}
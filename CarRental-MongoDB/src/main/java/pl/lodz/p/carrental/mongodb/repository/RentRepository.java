package pl.lodz.p.carrental.mongodb.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import pl.lodz.p.carrental.mongodb.manager.DatabaseManager;
import pl.lodz.p.carrental.mongodb.model.Rent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RentRepository {

    private final MongoCollection<Rent> collection;

    public RentRepository(DatabaseManager dbManager) {
        this.collection = dbManager.getDatabase().getCollection("rents", Rent.class);
    }

    public Rent save(Rent rent) {
        collection.replaceOne(Filters.eq("_id", rent.getId()), rent, new ReplaceOptions().upsert(true));
        return rent;
    }

    public Optional<Rent> findById(UUID id) {
        return Optional.ofNullable(collection.find(Filters.eq("_id", id)).first());
    }

    public Optional<Rent> findByVehicleId(UUID vehicleId) {
        return Optional.ofNullable(collection.find(Filters.eq("vehicleId", vehicleId)).first());
    }

    public Optional<Rent> findByClientId(UUID clientId) {
        return Optional.ofNullable(collection.find(Filters.eq("clientId", clientId)).first());
    }

    public List<Rent> findAll() {
        return collection.find().into(new ArrayList<>());
    }
}
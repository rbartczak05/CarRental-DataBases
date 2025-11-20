package pl.lodz.p.carrental.mongodb.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import pl.lodz.p.carrental.mongodb.manager.DatabaseManager;
import pl.lodz.p.carrental.mongodb.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VehicleRepository {

    private final MongoCollection<Vehicle> collection;

    public VehicleRepository(DatabaseManager dbManager) {
        this.collection = dbManager.getDatabase().getCollection("vehicles", Vehicle.class);
    }

    public Vehicle save(Vehicle vehicle) {
        collection.replaceOne(Filters.eq("_id", vehicle.getId()), vehicle, new ReplaceOptions().upsert(true));
        return vehicle;
    }

    public void deleteById(UUID id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    public Optional<Vehicle> findById(UUID id) {
        return Optional.ofNullable(collection.find(Filters.eq("_id", id)).first());
    }

    public Optional<Vehicle> findByPlateNumber(String plateNumber) {
        return Optional.ofNullable(collection.find(Filters.eq("plateNumber", plateNumber)).first());
    }

    public List<Vehicle> findAll() {
        return collection.find().into(new ArrayList<>());
    }
}
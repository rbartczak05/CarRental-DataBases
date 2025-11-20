package pl.lodz.p.carrental.redis.decorator;

import pl.lodz.p.carrental.redis.manager.DatabaseManager;
import pl.lodz.p.carrental.redis.manager.RedisManager;
import pl.lodz.p.carrental.redis.model.vehicle.Vehicle;
import pl.lodz.p.carrental.redis.repository.VehicleRepository;

import java.util.Optional;
import java.util.UUID;

public class VehicleRepositoryDecorator extends VehicleRepository {

    public VehicleRepositoryDecorator(DatabaseManager dbManager) {
        super(dbManager);
    }

    private String getKey(UUID id) {
        return RedisManager.CACHE_PREFIX + "vehicle:" + id.toString();
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        String key = getKey(id);

        String json = RedisManager.getRaw(key);

        if (json != null) {
            Vehicle cachedVehicle = null;

            if (json.contains("\"jsonType\":\"Car\"")) {
                cachedVehicle = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.vehicle.Car.class);
            } else if (json.contains("\"jsonType\":\"Bicycle\"")) {
                cachedVehicle = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.vehicle.Bicycle.class);
            } else if (json.contains("\"jsonType\":\"Moped\"")) {
                cachedVehicle = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.vehicle.Moped.class);
            }

            if (cachedVehicle != null) {
                System.out.println("Vehicle found in Redis cache: " + id + " Type: " + cachedVehicle.getClass().getSimpleName());
                return Optional.of(cachedVehicle);
            }
        }

        Optional<Vehicle> vehicle = super.findById(id);
        vehicle.ifPresent(value -> RedisManager.set(key, value, RedisManager.TTL_SECONDS));
        return vehicle;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        Vehicle savedVehicle = super.save(vehicle);
        RedisManager.set(getKey(savedVehicle.getId()), savedVehicle, RedisManager.TTL_SECONDS);
        return savedVehicle;
    }

    @Override
    public void deleteById(UUID id) {
        super.deleteById(id);
        RedisManager.delete(getKey(id));
    }
}
package pl.lodz.p.carrental.mongodb.service;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import pl.lodz.p.carrental.mongodb.manager.DatabaseManager;
import pl.lodz.p.carrental.mongodb.model.vehicle.Vehicle;
import pl.lodz.p.carrental.mongodb.repository.VehicleRepository;

import java.util.List;
import java.util.UUID;

public class VehicleService {
    private final DatabaseManager databaseManager;
    private final VehicleRepository vehicleRepository;

    public VehicleService(DatabaseManager databaseManager, VehicleRepository vehicleRepository) {
        this.databaseManager = databaseManager;
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();
                if (vehicleRepository.findById(vehicle.getId()).isPresent()) {
                    throw new IllegalArgumentException("Vehicle with id: " + vehicle.getId() + " already exists");
                }
                Vehicle savedVehicle = vehicleRepository.save(vehicle);
                session.commitTransaction();
                return savedVehicle;
            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();
                if (vehicleRepository.findById(vehicle.getId()).isEmpty()) {
                    throw new IllegalArgumentException("Vehicle with id: " + vehicle.getId() + " does not exist");
                }
                Vehicle updatedVehicle = vehicleRepository.save(vehicle);
                session.commitTransaction();
                return updatedVehicle;
            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public void removeVehicle(UUID vehicleId) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();
                if (vehicleRepository.findById(vehicleId).isEmpty()) {
                    throw new IllegalArgumentException("Vehicle with id: " + vehicleId + " does not exist");
                }
                vehicleRepository.deleteById(vehicleId);
                session.commitTransaction();
            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public Vehicle searchVehicleById(UUID id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle searchVehicleByPlateNumber(String plateNumber) {
        return vehicleRepository.findByPlateNumber(plateNumber).orElse(null);
    }

    public List<Vehicle> searchAll() {
        return vehicleRepository.findAll();
    }
}
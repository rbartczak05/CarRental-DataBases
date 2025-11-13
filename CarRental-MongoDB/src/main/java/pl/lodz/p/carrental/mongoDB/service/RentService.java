package pl.lodz.p.carrental.mongoDB.service;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import pl.lodz.p.carrental.mongoDB.manager.DatabaseManager;
import pl.lodz.p.carrental.mongoDB.model.Rent;
import pl.lodz.p.carrental.mongoDB.model.client.Client;
import pl.lodz.p.carrental.mongoDB.model.vehicle.Vehicle;
import pl.lodz.p.carrental.mongoDB.repository.ClientRepository;
import pl.lodz.p.carrental.mongoDB.repository.RentRepository;
import pl.lodz.p.carrental.mongoDB.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RentService {

    private final ClientRepository clientRepository;
    private final RentRepository rentRepository;
    private final VehicleRepository vehicleRepository;
    private final DatabaseManager dbManager;

    public RentService(DatabaseManager dbManager, ClientRepository clientRepository, RentRepository rentRepository, VehicleRepository vehicleRepository) {
        this.dbManager = dbManager;
        this.clientRepository = clientRepository;
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Rent createRent(UUID clientId, UUID vehicleId, int days) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (days <= 0) {
            throw new IllegalArgumentException("Days cannot be less than or equal to zero");
        }

        try (ClientSession session = dbManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();

                Client client = clientRepository.findById(clientId)
                        .orElseThrow(() -> new IllegalArgumentException("Client with id " + clientId + " not found"));
                Vehicle vehicle = vehicleRepository.findById(vehicleId)
                        .orElseThrow(() -> new IllegalArgumentException("Vehicle with id " + vehicleId + " not found"));

                if (vehicle.isRented()) {
                    throw new IllegalArgumentException("Vehicle with id " + vehicle.getId() + " is already rented");
                }

                Rent rent = new Rent(clientId, vehicleId, days);

                double price = vehicle.getPricePerDay() * days * (1.0 - client.getClientType().getDiscount());
                rent.setPrice(price);

                if (client.getBalance() < rent.getPrice()) {
                    throw new IllegalArgumentException("Client with id " + client.getId() + " has not enough balance");
                }

                client.substractBalance(rent.getPrice());
                vehicle.setRented(true);

                clientRepository.save(client);
                vehicleRepository.save(vehicle);
                rentRepository.save(rent);

                session.commitTransaction();
                return rent;

            } catch (MongoException | IllegalArgumentException e) {
                session.abortTransaction();
                e.printStackTrace();
                throw e;
            }
        }
    }

    public Rent endRent(UUID rentId) {
        try (ClientSession session = dbManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();

                Rent rent = rentRepository.findById(rentId)
                        .orElseThrow(() -> new IllegalArgumentException("Rent with id " + rentId + " not found"));

                if (!rent.isActive()) {
                    throw new IllegalStateException("Rent is already ended");
                }

                Vehicle vehicle = vehicleRepository.findById(rent.getVehicleId())
                        .orElseThrow(() -> new IllegalStateException("Associated vehicle not found")); // Błąd spójności danych

                rent.setActive(false);
                rent.setReturnDate(LocalDate.now());
                vehicle.setRented(false);

                rentRepository.save(rent);
                vehicleRepository.save(vehicle);

                session.commitTransaction();
                return rent;
            } catch (MongoException | IllegalArgumentException e) {
                session.abortTransaction();
                e.printStackTrace();
                throw e;
            }
        }
    }

    public Rent searchRentById(UUID id) {
        return rentRepository.findById(id).orElse(null);
    }

    public Rent searchRentByVehicleId(UUID vehicleId) {
        return rentRepository.findByVehicleId(vehicleId).orElse(null);
    }

    public Rent searchRentByClientId(UUID clientId) {
        return rentRepository.findByClientId(clientId).orElse(null);
    }

    public List<Rent> searchAll() {
        return rentRepository.findAll();
    }
}
package pl.lodz.p.carrental.mongoDB.service;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import pl.lodz.p.carrental.mongoDB.manager.DatabaseManager;
import pl.lodz.p.carrental.mongoDB.model.Address;
import pl.lodz.p.carrental.mongoDB.repository.AddressRepository;

import java.util.List;
import java.util.UUID;

public class AddressService {
    private final DatabaseManager databaseManager;
    private final AddressRepository addressRepository;

    public AddressService(DatabaseManager databaseManager, AddressRepository addressRepository) {
        this.databaseManager = databaseManager;
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address address) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();

                Address savedAddress = addressRepository.save(address);

                session.commitTransaction();
                return savedAddress;
            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public Address updateAddress(Address address) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();

                if (address.getId() == null) {
                    throw new IllegalArgumentException("Address id cannot be null");
                }
                if (addressRepository.findById(address.getId()).isEmpty()) {
                    throw new IllegalArgumentException("Address with id: " + address.getId() + " does not exist");
                }
                Address updatedAddress = addressRepository.save(address);

                session.commitTransaction();

                return updatedAddress;
            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public void removeAddress(UUID addressId) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {
                session.startTransaction();
                if (addressRepository.findById(addressId).isEmpty()) {
                    throw new IllegalArgumentException("Address with id: " + addressId + " does not exist");
                }
                addressRepository.deleteById(addressId);
                session.commitTransaction();
            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public Address searchById(UUID id) {
        return addressRepository.findById(id).orElse(null);
    }

    public List<Address> searchAll() {
        return addressRepository.findAll();
    }
}
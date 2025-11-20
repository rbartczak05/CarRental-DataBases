package pl.lodz.p.carrental.redis.service;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import pl.lodz.p.carrental.redis.manager.DatabaseManager;
import pl.lodz.p.carrental.redis.model.client.Client;
import pl.lodz.p.carrental.redis.repository.ClientRepository;

import java.util.List;
import java.util.UUID;

public class ClientService {
    private final DatabaseManager databaseManager;
    private final ClientRepository clientRepository;

    public ClientService(DatabaseManager databaseManager, ClientRepository clientRepository) {
        this.databaseManager = databaseManager;
        this.clientRepository = clientRepository;
    }

    public Client addUser(Client user) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {

                session.startTransaction();

                if (clientRepository.findById(user.getId()).isPresent()) {
                    throw new IllegalArgumentException("User with login: " + user.getId() + " already exists");
                }

                Client savedUser = clientRepository.save(user);

                session.commitTransaction();

                return savedUser;

            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public Client updateUser(Client client) {
        try (ClientSession session = databaseManager.getMongoClient().startSession()) {
            try {

                session.startTransaction();

                if (clientRepository.findById(client.getId()).isEmpty()) {
                    throw new IllegalArgumentException("User with id: " + client.getId() + " does not exist");
                }

                Client updatedUser = clientRepository.save(client);

                session.commitTransaction();

                return updatedUser;

            } catch (MongoException | IllegalStateException e) {
                System.err.println("Transaction aborted: " + e.getMessage());
                session.abortTransaction();
                throw e;
            }
        }
    }

    public Client searchClientById(UUID id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client searchClientByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    public List<Client> searchClientByName(String name) {
        return clientRepository.findByName(name);
    }

    public List<Client> searchAll() {
        return clientRepository.findAll();
    }
}
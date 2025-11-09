package pl.lodz.p.carrental.postgreSQL.service;

import jakarta.persistence.EntityManagerFactory;
import pl.lodz.p.carrental.postgreSQL.model.client.Client;
import pl.lodz.p.carrental.postgreSQL.repository.ClientRepository;

import java.util.UUID;

public class ClientService {
    private final ClientRepository clientRepository;
    private final EntityManagerFactory emf;

    public ClientService(ClientRepository clientRepository, EntityManagerFactory emf) {
        this.clientRepository = clientRepository;
        this.emf = emf;
    }

    public void persist(Client client) {
        clientRepository.persist(emf.createEntityManager(), client);
    }

    public Client update(Client client) {
        return clientRepository.update(emf.createEntityManager(), client);
    }

    public Client remove(Client client) {
        return clientRepository.remove(emf.createEntityManager(), client);
    }

    public Client searchClientById(UUID id) {
        return clientRepository.searchById(emf.createEntityManager(), id);
    }
}

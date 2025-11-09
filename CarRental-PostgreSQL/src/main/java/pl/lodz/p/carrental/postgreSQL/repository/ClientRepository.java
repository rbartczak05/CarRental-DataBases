package pl.lodz.p.carrental.postgreSQL.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgreSQL.model.client.Client;

import java.util.UUID;

public class ClientRepository {

    public void persist(EntityManager em, Client client) {
        em.persist(client);
    }

    public Client update(EntityManager em, Client client) {
        return em.merge(client);
    }

    public Client remove(EntityManager em, Client client) {
        em.remove(client);
        return client;
    }

    public Client searchById(EntityManager em, UUID id) {
        return em.find(Client.class, id);
    }


}
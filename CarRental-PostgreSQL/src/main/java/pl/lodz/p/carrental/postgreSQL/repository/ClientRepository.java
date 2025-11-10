package pl.lodz.p.carrental.postgreSQL.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgreSQL.model.client.Client;

import java.util.UUID;

public class ClientRepository {

    public void persist(EntityManager em, Client client) {
        em.persist(client);
    }

    public void update(EntityManager em, Client client) {
        em.merge(client);
    }

    public void remove(EntityManager em, UUID clientId) {
        Client client = searchById(em, clientId);
        if (client != null) {
            em.remove(client);
        }
    }
    public Client searchById(EntityManager em, UUID id) {
        return em.find(Client.class, id);
    }


}
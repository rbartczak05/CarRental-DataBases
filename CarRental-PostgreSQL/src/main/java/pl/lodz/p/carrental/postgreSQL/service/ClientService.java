package pl.lodz.p.carrental.postgreSQL.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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

    public Client persist(Client client) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            clientRepository.persist(em, client);
            tx.commit();
            return client;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void update(Client client) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            clientRepository.update(em, client);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void remove(UUID clientId) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            clientRepository.remove(em, clientId);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Client searchClientById(UUID id) {
        return clientRepository.searchById(emf.createEntityManager(), id);
    }
}

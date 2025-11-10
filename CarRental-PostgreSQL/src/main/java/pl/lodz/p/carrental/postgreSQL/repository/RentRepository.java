package pl.lodz.p.carrental.postgreSQL.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgreSQL.model.Rent;

import java.util.UUID;

public class RentRepository {

    public void persist(EntityManager em, Rent rent) {
        em.persist(rent);
    }

    public void update(EntityManager em, Rent rent) {
        em.merge(rent);
    }

    public void remove(EntityManager em, UUID rentId) {
        Rent rent = searchById(em, rentId);
        if (rent != null) {
            em.remove(rent);
        }
    }

    public Rent searchById(EntityManager em, UUID id) {
        return em.find(Rent.class, id);
    }
}
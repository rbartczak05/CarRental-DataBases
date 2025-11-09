package pl.lodz.p.carrental.postgreSQL.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgreSQL.model.Rent;

import java.util.UUID;

public class RentRepository {

    public void persist(EntityManager em, Rent rent) {
        em.persist(rent);
    }

    public Rent update(EntityManager em, Rent rent) {
        return em.merge(rent);
    }

    public Rent remove(EntityManager em, Rent rent) {
        em.remove(rent);
        return rent;
    }

    public Rent searchById(EntityManager em, UUID id) {
        return em.find(Rent.class, id);
    }
}
package pl.lodz.p.carrental.postgreSQL.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgreSQL.model.Address;

import java.util.UUID;

public class AddressRepository {

    public void persist(EntityManager em, Address address) {
        em.persist(address);
    }

    public Address update(EntityManager em, Address address) {
        return em.merge(address);
    }

    public Address remove(EntityManager em, Address address) {
        em.remove(address);
        return address;
    }

    public Address searchById(EntityManager em, UUID id) {
        return em.find(Address.class, id);
    }
}

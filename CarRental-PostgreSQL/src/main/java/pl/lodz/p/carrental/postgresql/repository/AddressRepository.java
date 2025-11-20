package pl.lodz.p.carrental.postgresql.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgresql.model.Address;

import java.util.UUID;

public class AddressRepository {

    public void persist(EntityManager em, Address address) {
        em.persist(address);
    }

    public void update(EntityManager em, Address address) {
        em.merge(address);
    }

    public void remove(EntityManager em, UUID addressId) {
        Address address = searchById(em, addressId);
        if (address != null) {
            em.remove(address);
        }
    }

    public Address searchById(EntityManager em, UUID id) {
        return em.find(Address.class, id);
    }
}

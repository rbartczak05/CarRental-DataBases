package pl.lodz.p.carrental.postgresql.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import pl.lodz.p.carrental.postgresql.model.Address;
import pl.lodz.p.carrental.postgresql.repository.AddressRepository;

import java.util.UUID;

public class AddressService {
    private final AddressRepository addressRepository;
    private final EntityManagerFactory emf;

    public AddressService(AddressRepository addressRepository, EntityManagerFactory emf) {
        this.addressRepository = addressRepository;
        this.emf = emf;
    }

    public Address persist(Address address) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            addressRepository.persist(em, address);
            tx.commit();
            return address;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void update(Address address) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            addressRepository.update(em, address);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void remove(UUID addressId) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            addressRepository.remove(em, addressId);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Address searchById(UUID id) {
        return addressRepository.searchById(emf.createEntityManager(), id);
    }
}

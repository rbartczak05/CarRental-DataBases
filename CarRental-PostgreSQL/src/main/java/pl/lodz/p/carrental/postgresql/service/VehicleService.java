package pl.lodz.p.carrental.postgresql.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import pl.lodz.p.carrental.postgresql.model.vehicle.Vehicle;
import pl.lodz.p.carrental.postgresql.repository.VehicleRepository;

import java.util.UUID;

public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final EntityManagerFactory emf;

    public VehicleService(VehicleRepository vehicleRepository, EntityManagerFactory emf) {
        this.vehicleRepository = vehicleRepository;
        this.emf = emf;
    }

    public Vehicle persist(Vehicle vehicle) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            vehicleRepository.persist(em, vehicle);
            tx.commit();
            return vehicle;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void update(Vehicle vehicle) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            vehicleRepository.update(em, vehicle);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public void remove(UUID vehicleId) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            vehicleRepository.remove(em, vehicleId);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Vehicle searchVehicleById(UUID id) {
        return vehicleRepository.searchById(emf.createEntityManager(), id);
    }
}

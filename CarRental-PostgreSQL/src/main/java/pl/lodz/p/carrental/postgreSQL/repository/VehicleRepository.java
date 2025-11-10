package pl.lodz.p.carrental.postgreSQL.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;

import java.util.UUID;

public class VehicleRepository {

    public void persist(EntityManager em, Vehicle vehicle) {
        em.persist(vehicle);
    }

    public void update(EntityManager em, Vehicle vehicle) {
        em.merge(vehicle);
    }

    public void remove(EntityManager em, UUID vehicleId) {
        Vehicle vehicle = searchById(em, vehicleId);
        if (vehicle != null) {
            em.remove(vehicle);
        }
    }

    public Vehicle searchById(EntityManager em, UUID id) {
        return em.find(Vehicle.class, id);
    }
}
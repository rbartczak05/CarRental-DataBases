package pl.lodz.p.carrental.postgreSQL.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;

import java.util.UUID;

public class VehicleRepository {

    public void persist(EntityManager em, Vehicle vehicle) {
        em.persist(vehicle);
    }

    public Vehicle update(EntityManager em, Vehicle vehicle) {
        return em.merge(vehicle);
    }

    public Vehicle remove(EntityManager em, Vehicle vehicle) {
        em.remove(vehicle);
        return vehicle;
    }

    public Vehicle searchById(EntityManager em, UUID id) {
        return em.find(Vehicle.class, id);
    }
}
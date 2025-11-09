package pl.lodz.p.carrental.postgreSQL.service;

import jakarta.persistence.EntityManagerFactory;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;
import pl.lodz.p.carrental.postgreSQL.repository.VehicleRepository;

import java.util.UUID;

public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final EntityManagerFactory emf;

    public VehicleService(VehicleRepository vehicleRepository, EntityManagerFactory emf) {
        this.vehicleRepository = vehicleRepository;
        this.emf = emf;
    }

    public void persist(Vehicle vehicle) {
        vehicleRepository.persist(emf.createEntityManager(), vehicle);
    }

    public Vehicle update(Vehicle vehicle) {
        return vehicleRepository.update(emf.createEntityManager(), vehicle);
    }

    public Vehicle remove(Vehicle vehicle) {
        return vehicleRepository.remove(emf.createEntityManager(), vehicle);
    }

    public Vehicle searchClientById(UUID id) {
        return vehicleRepository.searchById(emf.createEntityManager(), id);
    }
}

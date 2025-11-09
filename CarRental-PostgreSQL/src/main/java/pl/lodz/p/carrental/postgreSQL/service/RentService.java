package pl.lodz.p.carrental.postgreSQL.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import pl.lodz.p.carrental.postgreSQL.model.Rent;
import pl.lodz.p.carrental.postgreSQL.model.client.Client;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;
import pl.lodz.p.carrental.postgreSQL.repository.AddressRepository;
import pl.lodz.p.carrental.postgreSQL.repository.ClientRepository;
import pl.lodz.p.carrental.postgreSQL.repository.RentRepository;
import pl.lodz.p.carrental.postgreSQL.repository.VehicleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class RentService {

    private final ClientRepository clientRepository;
    private final RentRepository rentRepository;
    private final VehicleRepository vehicleRepository;
    private EntityManagerFactory emf;

    public RentService(EntityManagerFactory emf, ClientRepository clientRepository, RentRepository rentRepository, VehicleRepository vehicleRepository) {
        this.emf = emf;
        this.clientRepository = clientRepository;
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Rent createRent(UUID clientId, UUID vehicleId, int days){
        if (clientId == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (days <= 0) {
            throw new IllegalArgumentException("Days cannot be less than or equal to zero");
        }
        EntityManager em = emf.createEntityManager();

        Client client = clientRepository.searchById(em, clientId);
        Vehicle vehicle = vehicleRepository.searchById(em, vehicleId);

        Rent rent = new Rent(client, vehicle, days);
        rent.getVehicle().setRented(true);


        rentRepository.persist(em, rent);
        return rent;
    }

    public Rent endRent(Rent rent) {
        rent.setActive(false);
        rent.setReturnDate(LocalDate.now());
        rent.getVehicle().setRented(false);
        return rent;
    }

    public Rent updateRent(Rent rent){
        if (rent == null) {
            throw new IllegalArgumentException("Rent cannot be null");
        }
        EntityManager em = emf.createEntityManager();
        return rentRepository.update(em, rent);
    }

    public Rent removeRent(UUID rentId){
        if (rentId == null) {
            throw new IllegalArgumentException("Rent cannot be null");
        }
        EntityManager em = emf.createEntityManager();
        return rentRepository.remove(em, rentRepository.searchById(em, rentId));
    }
}

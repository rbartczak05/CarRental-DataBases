package pl.lodz.p.carrental.postgreSQL.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import pl.lodz.p.carrental.postgreSQL.model.Rent;
import pl.lodz.p.carrental.postgreSQL.model.client.Client;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;
import pl.lodz.p.carrental.postgreSQL.repository.ClientRepository;
import pl.lodz.p.carrental.postgreSQL.repository.RentRepository;
import pl.lodz.p.carrental.postgreSQL.repository.VehicleRepository;

import java.time.LocalDate;
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
        EntityTransaction tx = null;

        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();

            Client client = clientRepository.searchById(em, clientId);
            Vehicle vehicle = vehicleRepository.searchById(em, vehicleId);

            if (vehicle.isRented()) {
                throw new IllegalArgumentException("Vehicle with id " + vehicle.getId() + " is already rented");
            }

            Rent rent = new Rent(client, vehicle, days);
            if (client.getBalance() < rent.getPrice()) {
                throw new IllegalArgumentException("Client with id " + client.getId() + " has not enough balance");
            }
            client.substractBalance(rent.getPrice());
            rent.getVehicle().setRented(true);

            rentRepository.persist(em, rent);

            tx.commit();
            return rent;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    public Rent endRent(Rent rent) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            rent.setActive(false);
            rent.setReturnDate(LocalDate.now());
            rent.getVehicle().setRented(false);
            rentRepository.update(em, rent);
            tx.commit();
            return rent;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    public void update(Rent rent) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            rentRepository.update(em, rent);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    public void remove(UUID rentId) {
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            rentRepository.remove(em, rentId);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    public Rent searchRentById(UUID id) {
        return rentRepository.searchById(emf.createEntityManager(), id);
    }
}

package pl.lodz.p.carrental.postgreSQL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import pl.lodz.p.carrental.postgreSQL.model.Address;
import pl.lodz.p.carrental.postgreSQL.model.Rent;
import pl.lodz.p.carrental.postgreSQL.model.client.*;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Bicycle;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Car;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Moped;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;
import pl.lodz.p.carrental.postgreSQL.repository.AddressRepository;
import pl.lodz.p.carrental.postgreSQL.repository.ClientRepository;
import pl.lodz.p.carrental.postgreSQL.repository.RentRepository;
import pl.lodz.p.carrental.postgreSQL.repository.VehicleRepository;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            emf = Persistence.createEntityManagerFactory("postgres");
            em = emf.createEntityManager();

            ClientRepository clientRepository = new ClientRepository();
            VehicleRepository vehicleRepository = new VehicleRepository();
            RentRepository rentRepository = new RentRepository();
            AddressRepository addressRepository = new AddressRepository();

            tx = em.getTransaction();
            tx.begin();


            System.out.println("--- Tworzenie 5 obiektów Address ---");
            Address addr1 = new Address("Piotrkowska", "1", "Łódź", "90-001");
            Address addr2 = new Address("Marszałkowska", "10", "Warszawa", "00-001");
            Address addr3 = new Address("Grunwaldzka", "20", "Gdańsk", "80-001");
            Address addr4 = new Address("Floriańska", "30", "Kraków", "30-001");
            Address addr5 = new Address("Wrocławska", "40", "Poznań", "60-001");

            addressRepository.persist(em, addr1);
            addressRepository.persist(em, addr2);
            addressRepository.persist(em, addr3);
            addressRepository.persist(em, addr4);
            addressRepository.persist(em, addr5);
            System.out.println("Zapisano adresy.");

            System.out.println("\n--- Tworzenie 5 obiektów Client ---");
            Client client1 = new ClientStandard("Jan Kowalski", "jan@example.com", 1000.0, addr1);
            Client client2 = new ClientBronze("Anna Nowak", "anna@example.com", 2000.0, addr2);
            Client client3 = new ClientSilver("Piotr Wiśniewski", "piotr@example.com", 3000.0, addr3);
            Client client4 = new ClientGold("Maria Dąbrowska", "maria@example.com", 4000.0, addr4);
            Client client5 = new ClientPlatinum("Krzysztof Lewandowski", "kris@example.com", 5000.0, addr5);

            clientRepository.persist(em, client1);
            clientRepository.persist(em, client2);
            clientRepository.persist(em, client3);
            clientRepository.persist(em, client4);
            clientRepository.persist(em, client5);
            System.out.println("Zapisano klientów.");

            System.out.println("\n--- Tworzenie 5 obiektów Vehicle ---");
            Vehicle car1 = new Car("EL 12345", 150.0, 1.6, "C");
            Vehicle car2 = new Car("WPR 54321", 200.0, 2.0, "D (SUV)");
            Vehicle moped1 = new Moped("SK 987A", 80.0, 50.0);
            Vehicle moped2 = new Moped("GD 456B", 90.0, 125.0);
            Vehicle bicycle1 = new Bicycle(30.0);

            vehicleRepository.persist(em, car1);
            vehicleRepository.persist(em, car2);
            vehicleRepository.persist(em, moped1);
            vehicleRepository.persist(em, moped2);
            vehicleRepository.persist(em, bicycle1);
            System.out.println("Zapisano pojazdy.");

            em.flush();

            System.out.println("\n--- Tworzenie 5 obiektów Rent ---");
            Rent rent1 = new Rent(client1, car1, 10);
            Rent rent2 = new Rent(client2, car2, 15);
            Rent rent3 = new Rent(client3, moped1, 20);
            Rent rent4 = new Rent(client4, moped2, 25);
            Rent rent5 = new Rent(client5, bicycle1, 30);

            rentRepository.persist(em, rent1);
            rentRepository.persist(em, rent2);
            rentRepository.persist(em, rent3);
            rentRepository.persist(em, rent4);
            rentRepository.persist(em, rent5);
            System.out.println("Zapisano wypożyczenia.");

            tx.commit();
            System.out.println("\nTransakcja zakończona sukcesem! Obiekty zapisane w bazie.");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
package pl.lodz.p.carrental.postgreSQL;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pl.lodz.p.carrental.postgreSQL.model.Address;
import pl.lodz.p.carrental.postgreSQL.model.client.*;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Bicycle;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Car;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Moped;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;
import pl.lodz.p.carrental.postgreSQL.repository.AddressRepository;
import pl.lodz.p.carrental.postgreSQL.repository.ClientRepository;
import pl.lodz.p.carrental.postgreSQL.repository.RentRepository;
import pl.lodz.p.carrental.postgreSQL.repository.VehicleRepository;
import pl.lodz.p.carrental.postgreSQL.service.AddressService;
import pl.lodz.p.carrental.postgreSQL.service.ClientService;
import pl.lodz.p.carrental.postgreSQL.service.RentService;
import pl.lodz.p.carrental.postgreSQL.service.VehicleService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main(String[] args) {

        EntityManagerFactory emf = null;

        try {
            emf = Persistence.createEntityManagerFactory("postgres");

            AddressRepository addressRepository = new AddressRepository();
            ClientRepository clientRepository = new ClientRepository();
            VehicleRepository vehicleRepository = new VehicleRepository();
            RentRepository rentRepository = new RentRepository();

            AddressService addressService = new AddressService(addressRepository, emf);
            ClientService clientService = new ClientService(clientRepository, emf);
            VehicleService vehicleService = new VehicleService(vehicleRepository, emf);
            RentService rentService = new RentService(emf, clientRepository, rentRepository, vehicleRepository);

            System.out.println("--- Tworzenie 10 adresów ---");
            List<Address> addresses = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                new Address("Ulica " + i, String.valueOf(i), "Miasto " + i, "00-00" + i);
            }

            System.out.println("\n--- Tworzenie 10 klientów ---");
            Client client1 = clientService.persist(new ClientStandard("Std Klient 1", "std1@ex.com", 1000.0, addresses.get(0)));
            Client client2 = clientService.persist(new ClientStandard("Std Klient 2", "std2@ex.com", 2000.0, addresses.get(1)));
            Client client3 = clientService.persist(new ClientBronze("Brz Klient 1", "brz1@ex.com", 3000.0, addresses.get(2)));
            Client client4 = clientService.persist(new ClientBronze("Brz Klient 2", "brz2@ex.com", 4000.0, addresses.get(3)));
            Client client5 = clientService.persist(new ClientSilver("Slv Klient 1", "slv1@ex.com", 5000.0, addresses.get(4)));
            Client client6 = clientService.persist(new ClientSilver("Slv Klient 2", "slv2@ex.com", 6000.0, addresses.get(5)));
            Client client7 = clientService.persist(new ClientGold("Gld Klient 1", "gld1@ex.com", 10000.0, addresses.get(6)));
            Client client8 = clientService.persist(new ClientGold("Gld Klient 2", "gld2@ex.com", 15000.0, addresses.get(7)));
            Client client9 = clientService.persist(new ClientPlatinum("Plt Klient 1", "plt1@ex.com", 20000.0, addresses.get(8)));
            Client client10 = clientService.persist(new ClientPlatinum("Klient Bez Kasy", "nomoney@ex.com", 50.0, addresses.get(9)));

            System.out.println("\n--- Tworzenie 10 pojazdów ---");
            Vehicle vehicle1 = vehicleService.persist(new Car("EL 001", 150.0, 1.6, "C"));
            Vehicle vehicle2 = vehicleService.persist(new Car("EL 002", 200.0, 2.0, "D"));
            Vehicle vehicle3 = vehicleService.persist(new Car("EL 003", 300.0, 3.0, "S"));
            Vehicle vehicle4 = vehicleService.persist(new Car("EL 004", 100.0, 1.2, "B"));
            Vehicle vehicle5 = vehicleService.persist(new Moped("EM 001", 80.0, 50.0));
            Vehicle vehicle6 = vehicleService.persist(new Moped("EM 002", 90.0, 125.0));
            Vehicle vehicle7 = vehicleService.persist(new Moped("EM 003", 85.0, 100.0));
            Vehicle vehicle8 = vehicleService.persist(new Bicycle(30.0));
            Vehicle vehicle9 = vehicleService.persist(new Bicycle(35.0));
            Vehicle vehicle10 = vehicleService.persist(new Bicycle(40.0));

            rentService.createRent(client1.getId(), vehicle1.getId(), 5);
            rentService.createRent(client2.getId(), vehicle2.getId(), 6);
            rentService.createRent(client3.getId(), vehicle3.getId(), 7);
            rentService.createRent(client4.getId(), vehicle4.getId(), 8);
            rentService.createRent(client5.getId(), vehicle5.getId(), 9);

            rentService.createRent(client6.getId(), vehicle6.getId(), 9);
            rentService.createRent(client7.getId(), vehicle7.getId(), 8);
            rentService.createRent(client8.getId(), vehicle8.getId(), 7);
            rentService.createRent(client9.getId(), vehicle9.getId(), 6);
            rentService.createRent(client10.getId(), vehicle10.getId(), 5);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close();
                System.out.println("\nEntityManagerFactory zamknięte.");
            }
        }
    }
}
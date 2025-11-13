package pl.lodz.p.carrental.mongoDB;

import pl.lodz.p.carrental.mongoDB.manager.DatabaseManager;
import pl.lodz.p.carrental.mongoDB.model.Rent;
import pl.lodz.p.carrental.mongoDB.model.client.Client;
import pl.lodz.p.carrental.mongoDB.model.vehicle.Vehicle;
import pl.lodz.p.carrental.mongoDB.repository.AddressRepository;
import pl.lodz.p.carrental.mongoDB.repository.ClientRepository;
import pl.lodz.p.carrental.mongoDB.repository.RentRepository;
import pl.lodz.p.carrental.mongoDB.repository.VehicleRepository;
import pl.lodz.p.carrental.mongoDB.service.AddressService;
import pl.lodz.p.carrental.mongoDB.service.ClientService;
import pl.lodz.p.carrental.mongoDB.service.RentService;
import pl.lodz.p.carrental.mongoDB.service.VehicleService;

public class Main {

    private static final String CONNECTION_STRING = "mongodb://admin:adminpassword@localhost:27017,localhost:27018,localhost:27019/" +
            "carrental?replicaSet=library_replica_set&authSource=admin";

    public static void main(String[] args) {
        DatabaseManager dbManager = null;
        try {
            dbManager = new DatabaseManager(CONNECTION_STRING);

            AddressRepository addressRepository = new AddressRepository(dbManager);
            ClientRepository clientRepository = new ClientRepository(dbManager);
            VehicleRepository vehicleRepository = new VehicleRepository(dbManager);
            RentRepository rentRepository = new RentRepository(dbManager);

            AddressService addressService = new AddressService(dbManager, addressRepository);
            ClientService clientService = new ClientService(dbManager, clientRepository);
            VehicleService vehicleService = new VehicleService(dbManager, vehicleRepository);
            RentService rentService = new RentService(dbManager, clientRepository, rentRepository, vehicleRepository);

            dbManager.initDatabaseValidation();
            dbManager.dataLoader();

            Client client1 = clientService.searchClientByEmail("jan@test.com");
            Client client2 = clientService.searchClientByEmail("anna@test.com");
            Client client3 = clientService.searchClientByEmail("piotr@test.com");

            Vehicle v1 = vehicleService.searchVehicleByPlateNumber("EL00001");
            Vehicle v2 = vehicleService.searchVehicleByPlateNumber("EL00002");
            Vehicle v3 = vehicleService.searchVehicleByPlateNumber("EL00003");
            Vehicle v4 = vehicleService.searchVehicleByPlateNumber("EL00004");

            Rent rent1 = rentService.createRent(client1.getId(), v1.getId(), 3);
            Rent rent2 = rentService.createRent(client2.getId(), v2.getId(), 5);
            Rent rent3 = rentService.createRent(client3.getId(), v3.getId(), 1);

            rentService.endRent(rent1.getId());
            rentService.endRent(rent2.getId());
            rentService.endRent(rent3.getId());

            Rent rent4 = rentService.createRent(client2.getId(), v4.getId(), 2);
            Rent rent5 = rentService.createRent(client1.getId(), v1.getId(), 1);

        } catch (Exception e) {
            System.err.println("Wystąpił błąd: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (dbManager != null) {
                dbManager.close();
                System.out.println("\nPołączenie z bazą danych zamknięte.");
            }
        }
    }
}
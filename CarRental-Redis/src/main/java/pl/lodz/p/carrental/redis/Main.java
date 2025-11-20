package pl.lodz.p.carrental.redis;

import pl.lodz.p.carrental.redis.decorator.AddressRepositoryDecorator;
import pl.lodz.p.carrental.redis.decorator.ClientRepositoryDecorator;
import pl.lodz.p.carrental.redis.decorator.VehicleRepositoryDecorator;
import pl.lodz.p.carrental.redis.manager.DatabaseManager;
import pl.lodz.p.carrental.redis.manager.RedisManager;
import pl.lodz.p.carrental.redis.model.Rent;
import pl.lodz.p.carrental.redis.model.client.Client;
import pl.lodz.p.carrental.redis.model.vehicle.Vehicle;
import pl.lodz.p.carrental.redis.repository.AddressRepository;
import pl.lodz.p.carrental.redis.repository.ClientRepository;
import pl.lodz.p.carrental.redis.repository.RentRepository;
import pl.lodz.p.carrental.redis.repository.VehicleRepository;
import pl.lodz.p.carrental.redis.service.AddressService;
import pl.lodz.p.carrental.redis.service.ClientService;
import pl.lodz.p.carrental.redis.service.RentService;
import pl.lodz.p.carrental.redis.service.VehicleService;

public class Main {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017,localhost:27018,localhost:27019/carrental?replicaSet=library_replica_set";

    public static void main(String[] args) {
        DatabaseManager dbManager = null;
        try {
            dbManager = new DatabaseManager(CONNECTION_STRING);
            RedisManager.init();

            AddressRepository addressRepository = new AddressRepositoryDecorator(dbManager);
            ClientRepository clientRepository = new ClientRepositoryDecorator(dbManager);
            VehicleRepository vehicleRepository = new VehicleRepositoryDecorator(dbManager);
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

            rentService.createRent(client2.getId(), v4.getId(), 2);
            rentService.createRent(client1.getId(), v1.getId(), 1);

        } catch (Exception e) {
            System.err.println("Wystąpił błąd: " + e.getMessage());
            e.printStackTrace();
        } finally {
            RedisManager.close();
            System.out.println("\nPołączenie z bazą danych Redis zostało zamknięte.");
            if (dbManager != null) {
                dbManager.close();
                System.out.println("\nPołączenie z bazą danych MongoDB zostało zamknięte.");
            }
        }
    }
}
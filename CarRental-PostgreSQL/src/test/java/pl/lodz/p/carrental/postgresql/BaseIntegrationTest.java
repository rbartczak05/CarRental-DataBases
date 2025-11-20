package pl.lodz.p.carrental.postgresql;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.lodz.p.carrental.postgresql.repository.AddressRepository;
import pl.lodz.p.carrental.postgresql.repository.ClientRepository;
import pl.lodz.p.carrental.postgresql.repository.RentRepository;
import pl.lodz.p.carrental.postgresql.repository.VehicleRepository;
import pl.lodz.p.carrental.postgresql.service.AddressService;
import pl.lodz.p.carrental.postgresql.service.ClientService;
import pl.lodz.p.carrental.postgresql.service.RentService;
import pl.lodz.p.carrental.postgresql.service.VehicleService;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public abstract class BaseIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("postgres")
            .withUsername("dbuser")
            .withPassword("dbpassword");

    protected static EntityManagerFactory emf;

    protected static AddressRepository addressRepository;
    protected static ClientRepository clientRepository;
    protected static VehicleRepository vehicleRepository;
    protected static RentRepository rentRepository;

    protected static AddressService addressService;
    protected static ClientService clientService;
    protected static VehicleService vehicleService;
    protected static RentService rentService;

    @BeforeAll
    static void setup() {
        postgres.start();

        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", postgres.getJdbcUrl());
        properties.put("jakarta.persistence.jdbc.user", postgres.getUsername());
        properties.put("jakarta.persistence.jdbc.password", postgres.getPassword());
        properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");
        properties.put("hibernate.show_sql", "false");

        emf = Persistence.createEntityManagerFactory("postgres", properties);

        addressRepository = new AddressRepository();
        clientRepository = new ClientRepository();
        vehicleRepository = new VehicleRepository();
        rentRepository = new RentRepository();

        addressService = new AddressService(addressRepository, emf);
        clientService = new ClientService(clientRepository, emf);
        vehicleService = new VehicleService(vehicleRepository, emf);
        rentService = new RentService(emf, clientRepository, rentRepository, vehicleRepository);
    }

    @AfterAll
    static void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        postgres.stop();
    }
}
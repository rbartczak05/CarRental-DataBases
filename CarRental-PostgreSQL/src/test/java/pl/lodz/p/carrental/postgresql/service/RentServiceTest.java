package pl.lodz.p.carrental.postgresql.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.carrental.postgresql.BaseIntegrationTest;
import pl.lodz.p.carrental.postgresql.model.Address;
import pl.lodz.p.carrental.postgresql.model.Rent;
import pl.lodz.p.carrental.postgresql.model.client.Client;
import pl.lodz.p.carrental.postgresql.model.client.ClientGold;
import pl.lodz.p.carrental.postgresql.model.client.ClientStandard;
import pl.lodz.p.carrental.postgresql.model.vehicle.Car;
import pl.lodz.p.carrental.postgresql.model.vehicle.Vehicle;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RentServiceTest extends BaseIntegrationTest {

    private Client testClient;
    private Client testClientGold;
    private Client clientNoMoney;
    private Vehicle testVehicle;
    private Vehicle rentedVehicle;

    @BeforeEach
    void setupRentTest() {
        Address address = addressService.persist(new Address("RentAddress", "1", "City", "99-100"));

        testClient = clientService.persist(new ClientStandard("Client OK", "ok@test.com", 10000.0, address));

        testClientGold = clientService.persist(new ClientGold("Client Gold", "gold@test.com", 10000.0, address));

        clientNoMoney = clientService.persist(new ClientStandard("Client NoMoney", "nomoney@test.com", 10.0, address));

        testVehicle = vehicleService.persist(new Car("CAR 001", 100.0, 1.0, "A"));

        Vehicle v = new Car("CAR 002", 100.0, 1.0, "A");
        v.setRented(true);
        rentedVehicle = vehicleService.persist(v);
    }

    @Test
    void testCreateRent_Success_StandardClient() {
        int days = 10;
        double expectedPrice = 100.0 * days;
        double initialBalance = testClient.getBalance();

        Rent rent = rentService.createRent(testClient.getId(), testVehicle.getId(), days);

        assertNotNull(rent);
        assertNotNull(rent.getId());
        assertTrue(rent.isActive());
        assertEquals(expectedPrice, rent.getPrice());
        assertNull(rent.getReturnDate());

        Client updatedClient = clientService.searchClientById(testClient.getId());
        assertEquals(initialBalance - expectedPrice, updatedClient.getBalance());

        Vehicle updatedVehicle = vehicleService.searchVehicleById(testVehicle.getId());
        assertTrue(updatedVehicle.isRented());
    }

    @Test
    void testCreateRent_Success_GoldClient_CheckDiscount() {
        int days = 10;
        double expectedPrice = (100.0 * days) * (1.00 - 0.15); // 850.0
        double initialBalance = testClientGold.getBalance();

        Rent rent = rentService.createRent(testClientGold.getId(), testVehicle.getId(), days);

        assertNotNull(rent);
        assertEquals(expectedPrice, rent.getPrice());

        Client updatedClient = clientService.searchClientById(testClientGold.getId());
        assertEquals(initialBalance - expectedPrice, updatedClient.getBalance());
    }

    @Test
    void testCreateRent_Fail_VehicleAlreadyRented() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentService.createRent(testClient.getId(), rentedVehicle.getId(), 5);
        });
        assertEquals("Vehicle with id " + rentedVehicle.getId() + " is already rented", exception.getMessage());
    }

    @Test
    void testCreateRent_Fail_NotEnoughBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentService.createRent(clientNoMoney.getId(), testVehicle.getId(), 5);
        });
        assertEquals("Client with id " + clientNoMoney.getId() + " has not enough balance", exception.getMessage());
    }

    @Test
    void testCreateRent_Fail_InvalidDays() {
        assertThrows(IllegalArgumentException.class, () -> {
            rentService.createRent(testClient.getId(), testVehicle.getId(), 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rentService.createRent(testClient.getId(), testVehicle.getId(), -5);
        });
    }

    @Test
    void testEndRent() {
        Rent rent = rentService.createRent(testClient.getId(), testVehicle.getId(), 5);
        UUID rentId = rent.getId();
        UUID vehicleId = testVehicle.getId();

        assertNotNull(rent);
        assertTrue(vehicleService.searchVehicleById(vehicleId).isRented());

        Rent endedRent = rentService.endRent(rent);

        assertNotNull(endedRent);
        assertEquals(rentId, endedRent.getId());
        assertFalse(endedRent.isActive());
        assertNotNull(endedRent.getReturnDate());

        Vehicle freedVehicle = vehicleService.searchVehicleById(vehicleId);
        assertFalse(freedVehicle.isRented());
    }

    @Test
    void testSearchRentById() {
        Rent rent = rentService.createRent(testClient.getId(), testVehicle.getId(), 1);
        Rent found = rentService.searchRentById(rent.getId());
        assertNotNull(found);
        assertEquals(rent.getId(), found.getId());
    }

    @Test
    void testRemove() {
        Rent rent = rentService.createRent(testClient.getId(), testVehicle.getId(), 1);
        UUID id = rent.getId();
        assertNotNull(rentService.searchRentById(id), "Wypożyczenie powinno istnieć przed usunięciem");
        rentService.remove(id);
        assertNull(rentService.searchRentById(id), "Wypożyczenie powinno zostać usunięte");
    }

    @Test
    void testRemove_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertNull(rentService.searchRentById(nonExistentId));
        assertDoesNotThrow(() -> rentService.remove(nonExistentId));
    }
}
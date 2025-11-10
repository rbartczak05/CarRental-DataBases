package pl.lodz.p.carrental.postgreSQL.service;

import org.junit.jupiter.api.Test;
import pl.lodz.p.carrental.postgreSQL.BaseIntegrationTest;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Bicycle;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Car;
import pl.lodz.p.carrental.postgreSQL.model.vehicle.Vehicle;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VehicleServiceTest extends BaseIntegrationTest {

    @Test
    void testPersistAndSearchById_Car() {
        Vehicle car = new Car("EL 12345", 200.0, 1.8, "C");
        Vehicle persistedVehicle = vehicleService.persist(car);

        assertNotNull(persistedVehicle);
        assertNotNull(persistedVehicle.getId());
        assertFalse(persistedVehicle.isRented());

        Vehicle foundVehicle = vehicleService.searchVehicleById(persistedVehicle.getId());
        assertNotNull(foundVehicle);
        assertEquals(200.0, foundVehicle.getPricePerDay());
        assertTrue(foundVehicle instanceof Car);
        assertEquals("EL 12345", ((Car) foundVehicle).getPlateNumber());
    }

    @Test
    void testPersistAndSearchById_Bicycle() {
        Vehicle bicycle = new Bicycle(50.0);
        Vehicle persistedVehicle = vehicleService.persist(bicycle);

        assertNotNull(persistedVehicle);
        assertNotNull(persistedVehicle.getId());

        Vehicle foundVehicle = vehicleService.searchVehicleById(persistedVehicle.getId());
        assertNotNull(foundVehicle);
        assertEquals(50.0, foundVehicle.getPricePerDay());
        assertTrue(foundVehicle instanceof Bicycle);
    }

    @Test
    void testUpdate() {
        Vehicle car = new Car("WWW 111", 100.0, 1.0, "A");
        Vehicle persistedVehicle = vehicleService.persist(car);
        UUID id = persistedVehicle.getId();

        persistedVehicle.setPricePerDay(120.0);
        persistedVehicle.setRented(true);
        vehicleService.update(persistedVehicle);

        Vehicle updatedVehicle = vehicleService.searchVehicleById(id);
        assertNotNull(updatedVehicle);
        assertEquals(120.0, updatedVehicle.getPricePerDay());
        assertTrue(updatedVehicle.isRented());
    }

    @Test
    void testRemove() {
        Vehicle car = new Car("RRR 222", 100.0, 1.0, "B");
        Vehicle persistedVehicle = vehicleService.persist(car);
        UUID id = persistedVehicle.getId();

        assertNotNull(vehicleService.searchVehicleById(id), "Pojazd powinien istnieć przed usunięciem");

        vehicleService.remove(id);

        assertNull(vehicleService.searchVehicleById(id), "Pojazd powinien zostać usunięty");
    }

    @Test
    void testRemove_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertNull(vehicleService.searchVehicleById(nonExistentId));
        assertDoesNotThrow(() -> vehicleService.remove(nonExistentId));
    }
}
package pl.lodz.p.carrental.postgreSQL.service;

import org.junit.jupiter.api.Test;
import pl.lodz.p.carrental.postgreSQL.BaseIntegrationTest;
import pl.lodz.p.carrental.postgreSQL.model.Address;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceTest extends BaseIntegrationTest {

    @Test
    void testPersistAndSearchById() {
        Address address = new Address("Testowa", "1", "Testowo", "00-001");
        Address persistedAddress = addressService.persist(address);

        assertNotNull(persistedAddress);
        assertNotNull(persistedAddress.getId());

        Address foundAddress = addressService.searchById(persistedAddress.getId());
        assertNotNull(foundAddress);
        assertEquals("Testowa", foundAddress.getStreet());
    }

    @Test
    void testUpdate() {
        Address address = new Address("Stara Ulica", "10", "Miasto", "11-111");
        Address persistedAddress = addressService.persist(address);
        UUID id = persistedAddress.getId();

        persistedAddress.setStreet("Nowa Ulica");
        addressService.update(persistedAddress);

        Address updatedAddress = addressService.searchById(id);
        assertNotNull(updatedAddress);
        assertEquals("Nowa Ulica", updatedAddress.getStreet());
        assertEquals("10", updatedAddress.getHouseNumber());
    }

    @Test
    void testRemove() {
        Address address = new Address("Do Usunięcia", "1", "Miasto", "22-222");
        Address persistedAddress = addressService.persist(address);
        UUID id = persistedAddress.getId();

        assertNotNull(addressService.searchById(id), "Adres powinien istnieć przed usunięciem");

        addressService.remove(id);

        Address removedAddress = addressService.searchById(id);
        assertNull(removedAddress, "Adres powinien zostać usunięty");
    }

    @Test
    void testRemove_NotFound() {
        UUID nonExistentId = UUID.randomUUID();

        assertNull(addressService.searchById(nonExistentId));

        assertDoesNotThrow(() -> addressService.remove(nonExistentId));
    }

    @Test
    void testSearchById_NotFound() {
        Address found = addressService.searchById(UUID.randomUUID());
        assertNull(found);
    }
}
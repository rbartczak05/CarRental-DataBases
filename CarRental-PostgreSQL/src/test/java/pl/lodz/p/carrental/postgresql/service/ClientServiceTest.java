package pl.lodz.p.carrental.postgresql.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.carrental.postgresql.BaseIntegrationTest;
import pl.lodz.p.carrental.postgresql.model.Address;
import pl.lodz.p.carrental.postgresql.model.client.Client;
import pl.lodz.p.carrental.postgresql.model.client.ClientStandard;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest extends BaseIntegrationTest {

    private Address testAddress;

    @BeforeEach
    void setupClientTest() {
        testAddress = addressService.persist(new Address("Adresowa", "1", "Test", "12-345"));
    }

    @Test
    void testPersistAndSearchById() {
        Client client = new ClientStandard("Jan Testowy", "jan@test.com", 1000.0, testAddress);
        Client persistedClient = clientService.persist(client);

        assertNotNull(persistedClient);
        assertNotNull(persistedClient.getId());

        Client foundClient = clientService.searchClientById(persistedClient.getId());
        assertNotNull(foundClient);
        assertEquals("Jan Testowy", foundClient.getName());
        assertEquals("jan@test.com", foundClient.getEmail());
        assertEquals(1000.0, foundClient.getBalance());
        assertNotNull(foundClient.getAddress());
        assertEquals(testAddress.getId(), foundClient.getAddress().getId());
    }

    @Test
    void testUpdate() {
        Client client = new ClientStandard("Anna", "anna@test.com", 500.0, testAddress);
        Client persistedClient = clientService.persist(client);
        UUID id = persistedClient.getId();

        // Aktualizacja
        persistedClient.setBalance(1500.0);
        persistedClient.setName("Anna Po Podwyżce");
        clientService.update(persistedClient);

        Client updatedClient = clientService.searchClientById(id);
        assertNotNull(updatedClient);
        assertEquals("Anna Po Podwyżce", updatedClient.getName());
        assertEquals(1500.0, updatedClient.getBalance());
    }

    @Test
    void testRemove() {
        Client client = new ClientStandard("Do Usunięcia", "del@test.com", 0.0, testAddress);
        Client persistedClient = clientService.persist(client);
        UUID id = persistedClient.getId();

        assertNotNull(clientService.searchClientById(id), "Klient powinien istnieć przed usunięciem");

        clientService.remove(id);

        assertNull(clientService.searchClientById(id), "Klient powinien zostać usunięty");

        assertNotNull(addressService.searchById(testAddress.getId()), "Adres klienta nie powinien zostać usunięty");
    }

    @Test
    void testRemove_NotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertNull(clientService.searchClientById(nonExistentId));
        assertDoesNotThrow(() -> clientService.remove(nonExistentId));
    }
}
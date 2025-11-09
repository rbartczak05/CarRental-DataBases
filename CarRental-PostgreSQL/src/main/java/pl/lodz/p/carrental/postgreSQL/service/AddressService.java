package pl.lodz.p.carrental.postgreSQL.service;

import jakarta.persistence.EntityManagerFactory;
import pl.lodz.p.carrental.postgreSQL.model.Address;
import pl.lodz.p.carrental.postgreSQL.repository.AddressRepository;

import java.util.UUID;

public class AddressService {
    private final AddressRepository addressRepository;
    private final EntityManagerFactory emf;

    public AddressService(AddressRepository addressRepository, EntityManagerFactory emf) {
        this.addressRepository = addressRepository;
        this.emf = emf;
    }

    public void persist(Address address) {
        addressRepository.persist(emf.createEntityManager(), address);
    }

    public Address update(Address address) {
        return addressRepository.update(emf.createEntityManager(), address);
    }

    public Address remove(Address address) {
        return addressRepository.remove(emf.createEntityManager(), address);
    }

    public Address searchClientById(UUID id) {
        return addressRepository.searchById(emf.createEntityManager(), id);
    }
}

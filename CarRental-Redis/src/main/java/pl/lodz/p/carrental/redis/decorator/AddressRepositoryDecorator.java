package pl.lodz.p.carrental.redis.decorator;

import pl.lodz.p.carrental.redis.manager.DatabaseManager;
import pl.lodz.p.carrental.redis.manager.RedisManager;
import pl.lodz.p.carrental.redis.model.Address;
import pl.lodz.p.carrental.redis.repository.AddressRepository;

import java.util.Optional;
import java.util.UUID;

public class AddressRepositoryDecorator extends AddressRepository {

    public AddressRepositoryDecorator(DatabaseManager dbManager) {
        super(dbManager);
    }

    private String getKey(UUID id) {
        return RedisManager.CACHE_PREFIX + "address:" + id.toString();
    }

    @Override
    public Optional<Address> findById(UUID id) {
        String key = getKey(id);
        Address cachedAddress = RedisManager.get(key, Address.class);
        if (cachedAddress != null) {
            return Optional.of(cachedAddress);
        }

        Optional<Address> address = super.findById(id);

        address.ifPresent(value -> RedisManager.set(key, value, RedisManager.TTL_SECONDS));

        return address;
    }

    @Override
    public Address save(Address address) {
        Address savedAddress = super.save(address);

        RedisManager.set(getKey(savedAddress.getId()), savedAddress, RedisManager.TTL_SECONDS);

        return savedAddress;
    }

    @Override
    public void deleteById(UUID id) {
        super.deleteById(id);

        RedisManager.delete(getKey(id));
    }
}
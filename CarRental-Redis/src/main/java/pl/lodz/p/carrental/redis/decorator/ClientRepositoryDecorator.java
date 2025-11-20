package pl.lodz.p.carrental.redis.decorator;

import pl.lodz.p.carrental.redis.manager.DatabaseManager;
import pl.lodz.p.carrental.redis.manager.RedisManager;
import pl.lodz.p.carrental.redis.model.client.Client;
import pl.lodz.p.carrental.redis.repository.ClientRepository;

import java.util.Optional;
import java.util.UUID;

public class ClientRepositoryDecorator extends ClientRepository {

    public ClientRepositoryDecorator(DatabaseManager dbManager) {
        super(dbManager);
    }

    private String getKey(UUID id) {
        return RedisManager.CACHE_PREFIX + "client:" + id.toString();
    }

    @Override
    public Optional<Client> findById(UUID id) {
        String key = getKey(id);
        String json = RedisManager.getRaw(key);

        if (json != null) {
            Client cachedClient = null;

            if (json.contains("\"jsonType\":\"ClientStandard\"")) {
                cachedClient = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.client.ClientStandard.class);
            } else if (json.contains("\"jsonType\":\"ClientGold\"")) {
                cachedClient = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.client.ClientGold.class);
            } else if (json.contains("\"jsonType\":\"ClientSilver\"")) {
                cachedClient = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.client.ClientSilver.class);
            } else if (json.contains("\"jsonType\":\"ClientPlatinum\"")) {
                cachedClient = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.client.ClientPlatinum.class);
            } else if (json.contains("\"jsonType\":\"ClientBronze\"")) {
                cachedClient = RedisManager.deserialize(json, pl.lodz.p.carrental.redis.model.client.ClientBronze.class);
            }

            if (cachedClient != null) {
                System.out.println("Client found in Redis cache: " + id);
                return Optional.of(cachedClient);
            }
        }

        Optional<Client> client = super.findById(id);
        client.ifPresent(value -> RedisManager.set(key, value, RedisManager.TTL_SECONDS));
        return client;
    }

    @Override
    public Client save(Client client) {
        Client savedClient = super.save(client);
        RedisManager.set(getKey(savedClient.getId()), savedClient, RedisManager.TTL_SECONDS);
        return savedClient;
    }

}
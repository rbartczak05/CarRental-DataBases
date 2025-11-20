package pl.lodz.p.carrental.redis.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import pl.lodz.p.carrental.redis.manager.DatabaseManager;
import pl.lodz.p.carrental.redis.model.client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository {

    private final MongoCollection<Client> collection;

    public ClientRepository(DatabaseManager dbManager) {
        this.collection = dbManager.getDatabase().getCollection("clients", Client.class);
    }

    public Client save(Client client) {
        collection.replaceOne(Filters.eq("_id", client.getId()), client, new ReplaceOptions().upsert(true));
        return client;
    }

    public Optional<Client> findById(UUID id) {
        return Optional.ofNullable(collection.find(Filters.eq("_id", id)).first());
    }

    public Optional<Client> findByEmail(String email) {
        return Optional.ofNullable(collection.find(Filters.eq("email", email)).first());
    }

    public List<Client> findByName(String name) {
        return collection.find(Filters.eq("name", name)).into(new ArrayList<>());
    }

    public List<Client> findAll() {
        return collection.find().into(new ArrayList<>());
    }
}
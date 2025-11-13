package pl.lodz.p.carrental.mongoDB.manager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pl.lodz.p.carrental.mongoDB.model.Address;
import pl.lodz.p.carrental.mongoDB.model.Rent;
import pl.lodz.p.carrental.mongoDB.model.client.*;
import pl.lodz.p.carrental.mongoDB.model.vehicle.Bicycle;
import pl.lodz.p.carrental.mongoDB.model.vehicle.Car;
import pl.lodz.p.carrental.mongoDB.model.vehicle.Moped;
import pl.lodz.p.carrental.mongoDB.model.vehicle.Vehicle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DatabaseManager {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public DatabaseManager(String connectionString) {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder()
                .automatic(true)
                .register("pl.lodz.p.carrental.mongoDB.model")
                .register("pl.lodz.p.carrental.mongoDB.model.client")
                .register("pl.lodz.p.carrental.mongoDB.model.vehicle")
                .build());

        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry
        );

        ConnectionString connString = new ConnectionString(connectionString);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .codecRegistry(codecRegistry)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("carrental");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void close() {
        mongoClient.close();
    }

    public void initDatabaseValidation() {
        Set<String> collectionNames = database.listCollectionNames().into(new HashSet<>());

        Document addressValidator = new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", List.of("street", "city", "postal_code"))
                        .append("properties", new Document()
                                .append("street", new Document().append("bsonType", "string"))
                                .append("city", new Document().append("bsonType", "string"))
                                .append("postal_code", new Document().append("bsonType", "string"))
                        )
        );

        Document clientValidator = new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", List.of("name", "email", "balance", "addressId"))
                        .append("properties", new Document()
                                .append("name", new Document().append("bsonType", "string"))
                                .append("email", new Document().append("bsonType", "string"))
                                .append("balance", new Document().append("bsonType", "double"))
                                .append("addressId", new Document().append("bsonType", "binData"))
                        )
        );

        Document rentValidator = new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", List.of("clientId", "vehicleId", "startDate", "endDate", "active"))
                        .append("properties", new Document()
                                .append("clientId", new Document().append("bsonType", "binData"))
                                .append("vehicleId", new Document().append("bsonType", "binData"))
                                .append("startDate", new Document().append("bsonType", "date"))
                                .append("active", new Document().append("bsonType", "bool"))
                        )
        );

        Document vehicleValidator = new Document("$jsonSchema",
                new Document("bsonType", "object")
                        .append("required", List.of("pricePerDay", "rented"))
                        .append("properties", new Document()
                                .append("pricePerDay", new Document().append("bsonType", "double"))
                                .append("rented", new Document().append("bsonType", "bool"))
                        )
        );

        if (!collectionNames.contains("addresses")) {
            ValidationOptions vo = new ValidationOptions().validator(addressValidator);
            database.createCollection("addresses", new CreateCollectionOptions().validationOptions(vo));
            System.out.println("Utworzono kolekcję 'addresses' z walidacją.");
        }

        if (!collectionNames.contains("clients")) {
            ValidationOptions vo = new ValidationOptions().validator(clientValidator);
            database.createCollection("clients", new CreateCollectionOptions().validationOptions(vo));
            System.out.println("Utworzono kolekcję 'clients' z walidacją.");
        }

        if (!collectionNames.contains("rents")) {
            ValidationOptions vo = new ValidationOptions().validator(rentValidator);
            database.createCollection("rents", new CreateCollectionOptions().validationOptions(vo));
            System.out.println("Utworzono kolekcję 'rents' z walidacją.");
        }

        if (!collectionNames.contains("vehicles")) {
            ValidationOptions vo = new ValidationOptions().validator(vehicleValidator);
            database.createCollection("vehicles", new CreateCollectionOptions().validationOptions(vo));
            System.out.println("Utworzono kolekcję 'vehicles' z walidacją.");
        }
    }

    public void dataLoader() {
        database.getCollection("addresses").drop();
        database.getCollection("clients").drop();
        database.getCollection("rents").drop();
        database.getCollection("vehicles").drop();

        initDatabaseValidation();

        Address adr1 = new Address("Piotrkowska", "1", "Łódź", "90-001");
        Address adr2 = new Address("Mickiewicza", "10", "Warszawa", "00-001");
        Address adr3 = new Address("Floriańska", "5", "Kraków", "30-001");
        Address adr4 = new Address("Długa", "20", "Gdańsk", "80-001");
        Address adr5 = new Address("Półwiejska", "15", "Poznań", "60-001");
        Address adr6 = new Address("Rynek", "3", "Wrocław", "50-001");

        Address adr7 = new Address("Aleja Wyzwolenia", "12", "Szczecin", "70-001");
        Address adr8 = new Address("Gdańska", "50", "Bydgoszcz", "85-001");
        Address adr9 = new Address("Krakowskie Przedmieście", "33", "Lublin", "20-001");
        Address adr10 = new Address("3 Maja", "21", "Katowice", "40-001");
        Address adr11 = new Address("Lipowa", "8A", "Białystok", "15-001");
        Address adr12 = new Address("Świętojańska", "100", "Gdynia", "81-001");

        database.getCollection("addresses", Address.class).insertMany(List.of(
                adr1, adr2, adr3, adr4, adr5, adr6,
                adr7, adr8, adr9, adr10, adr11, adr12
        ));

        Vehicle v1 = new Car("EL00001", 150.0, 1.8, "C");
        Vehicle v2 = new Car("EL00002", 250.0, 2.5, "D_PREMIUM");
        Vehicle v3 = new Car("EL00003", 100.0, 1.2, "B");
        Vehicle v4 = new Car("EL00004", 80.0, 1.0, "A");
        Vehicle v5 = new Moped("EL00005", 70.0, 50.0);
        Vehicle v6 = new Bicycle(40.0);

        Vehicle v7 = new Car("EL00007", 160.0, 1.9, "C");
        Vehicle v8 = new Car("EL00008", 110.0, 1.4, "B");
        Vehicle v9 = new Car("EL00009", 280.0, 3.0, "D_PREMIUM");
        Vehicle v10 = new Moped("EL00010", 80.0, 125.0);
        Vehicle v11 = new Bicycle(50.0);
        Vehicle v12 = new Car("EL00011", 90.0, 1.0, "A"); // Rower nie ma tablicy, więc kolejny nr to EL00011

        database.getCollection("vehicles", Vehicle.class).insertMany(List.of(
                v1, v2, v3, v4, v5, v6,
                v7, v8, v9, v10, v11, v12
        ));

        Client c1 = new ClientStandard("Jan Kowalski", "jan@test.com", 1000.0, adr1.getId());
        Client c2 = new ClientBronze("Anna Nowak", "anna@test.com", 1500.0, adr2.getId());
        Client c3 = new ClientSilver("Piotr Wiśniewski", "piotr@test.com", 2500.0, adr3.getId());
        Client c4 = new ClientGold("Maria Dąbrowska", "maria@test.com", 5000.0, adr4.getId());
        Client c5 = new ClientPlatinum("Krzysztof Wójcik", "krzysztof@test.com", 10000.0, adr5.getId());
        Client c6 = new ClientStandard("Zofia Kamińska", "zofia@test.com", 800.0, adr6.getId());

        Client c7 = new ClientStandard("Tomasz Zieliński", "tomasz@test.com", 1200.0, adr7.getId());
        Client c8 = new ClientBronze("Barbara Wiśniewska", "barbara@test.com", 1700.0, adr8.getId());
        Client c9 = new ClientSilver("Marek Szymański", "marek@test.com", 3000.0, adr9.getId());
        Client c10 = new ClientGold("Elżbieta Wójcik", "elzbieta@test.com", 6000.0, adr10.getId());
        Client c11 = new ClientPlatinum("Paweł Jankowski", "pawel@test.com", 12000.0, adr11.getId());
        Client c12 = new ClientStandard("Agnieszka Kowalczyk", "agnieszka@test.com", 900.0, adr12.getId());

        database.getCollection("clients", Client.class).insertMany(List.of(
                c1, c2, c3, c4, c5, c6,
                c7, c8, c9, c10, c11, c12
        ));

        System.out.println("Załadowano dane testowe (12 adresów, 12 pojazdów, 12 klientów).");
    }
}
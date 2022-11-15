package pl.nbd.hotel.client;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import pl.nbd.hotel.db.AbstractMongoRepository;
import pl.nbd.hotel.repository.Repository;

import java.util.*;
import java.util.function.Predicate;

public class ClientRepository extends AbstractMongoRepository implements Repository<Client> {

    public ClientRepository() {
        super.initDbConnection();
        this.clientMongoCollection = mongoDatabase.getCollection("clients", Client.class);
    }

    private final MongoCollection<Client> clientMongoCollection;

    @Override
    public Client findById(String id) {
        Bson filter = Filters.eq("personalId", id);
        FindIterable<Client> clients = clientMongoCollection.find(filter);
        return clients.first();
    }

    @Override
    public Client save(Client client) {
        clientMongoCollection.insertOne(client);
        return client;
    }

    @Override
    public List<Client> find(Predicate<Client> predicate) {
        return findAll().stream().filter(predicate).toList();
    }

    @Override
    public List<Client> findAll() {
        return clientMongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public String getReport() {
        final StringBuilder description = new StringBuilder();
        for (Client c: findAll()) {
            description.append(c.getClientInfo());
            description.append(", ");
        }
        return description.toString();
    }

    @Override
    public int getSize() {
        return findAll().size();
    }

    @Override
    public void remove(Client object) {
        Bson filter = Filters.eq("personalId", object.getPersonalId());
        clientMongoCollection.deleteOne(filter);
    }

    @Override
    public void close() throws Exception {

    }
}

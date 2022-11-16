package pl.nbd.hotel.client;

import com.mongodb.MongoCommandException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import pl.nbd.hotel.db.AbstractMongoRepository;
import pl.nbd.hotel.repository.Repository;

import java.util.*;
import java.util.function.Predicate;

public class ClientRepository extends AbstractMongoRepository implements Repository<Client> {

    public ClientRepository() {
        super.initDbConnection();
        try{
            this.mongoDatabase.createCollection("clients", new CreateCollectionOptions().validationOptions(new ValidationOptions().validator(
                    Document.parse(
                            """
                                            {
                                               $jsonSchema: {
                                                  bsonType: "object",
                                                  required: [ "personalId" ],
                                                  properties: {
                                                     lastName: {
                                                        bsonType: "string",
                                                        description: "must be a string"
                                                     },
                                                     personalId: {
                                                        bsonType: "string",
                                                        description: "must be a string"
                                                     }
                                                  }
                                               }
                                            }
                                    """
                    )
            )));
        } catch (MongoCommandException mongoCommandException) {

        }


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
    public void removeById(String id) {
        Bson filter = Filters.eq("personalId", id);
        clientMongoCollection.deleteOne(filter);
    }

    @Override
    public void close() throws Exception {

    }
}

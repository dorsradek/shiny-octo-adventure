package pl.dors.radek.followme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

@Component
public class MyMongoDbFactory {

    private final MongoDbFactory mongoDbFactory;

    @Autowired
    public MyMongoDbFactory(MongoDbFactory mongoDbFactory) {
        this.mongoDbFactory = mongoDbFactory;
    }

    public MongoDbFactory getMongoDbFactory() {
        return mongoDbFactory;
    }
}
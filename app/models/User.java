package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.*;

public class User {

    public static MongoCollection users() {
        return PlayJongo.getCollection("users");
    }

    @JsonProperty("_id")
    public ObjectId id;
    public String username;

    public void insert() {
        users().save(this);
    }

    public void remove() {
        users().remove(this.id);
    }

    public static User findByName(String name) {
        return users().findOne("{name: #}", name).as(User.class);
    }

    @Override
    public String toString() {
        return "Welcome, " + username;
    }

}
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
    public String fullname;
    public String email;
    public String password;

    public void insert() {
        users().save(this);
    }

    public void remove() {
        users().remove(this.id);
    }

    public static User findByUsername(String name) {
        return users().findOne("{username: #}", name).as(User.class);
    }


    public static Boolean authenticate(String email, String password) {
        return null;
    }
}
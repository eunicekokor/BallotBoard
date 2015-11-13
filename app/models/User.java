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

    /**
     * Creates a new user
     * @param username
     * @param fullname
     * @param email
     * @param password
     */
    public void create(String username, String fullname, String email, String password) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    /**
     * Insert this user in to db collection
     */
    public void insert() {
        users().save(this);
    }

    /**
     * Remove this user from db collection
     */
    public void remove() {
        users().remove(this.id);
    }

    /**
     * Finds user by username
     * @param name,username of user
     * @return matched User if any
     */
    public static User findByUsername(String name) {
        return users().findOne("{username: #}", name).as(User.class);
    }

    /**
     * Checks if user as entered a valid email and password
     * @param email
     * @param password
     * @return true if correct email and password and false otherwise
     */
    public static Boolean authenticate(String email, String password) {
        if (users().findOne("{ $and: [{email: #},{password: #}]}", email, password).as(User.class) == null) {
            return false;
        }
        return true;
    }

    /**
     * Checks if an email or username if already taken
     * @param email
     * @param username
     * @return true if valid, false otherwise
     */
    public static Boolean exists(String email, String username) {
        if (users().findOne("{email: #}", email).as(User.class) == null) {
            return false;
        }
        if (users().findOne("{username: #}", username).as(User.class) == null) {
            return false;
        }
        return true;
    }
}
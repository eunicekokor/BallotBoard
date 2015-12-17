package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.*;
import org.mindrot.jbcrypt.BCrypt;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Arrays;

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
    public ArrayList<String> voteHistory;
    public ArrayList<String> favoriteHistory;
    public ArrayList<String> favoriteHistoryWithVotes;
    public ArrayList<String> notifications;

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
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.voteHistory = new ArrayList<>();
        this.favoriteHistory = new ArrayList<>();
        this.favoriteHistoryWithVotes = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    /**
     * Insert this user in to db collection
     */
    public void insert() {
        users().save(this);
    }

    /**
     * Remove this user from db collection - Unused
     */
    public void remove() {
        users().remove(this.id);
    }

    /**
     * Finds user by username
     * @param name,username of user
     * @return matched User if any
     */
    public static User findByEmail(String name) {
        User result = new User();
        result = users().findOne("{email: #}", name).as(User.class);
        if (result == null){
          System.out.println("that email was a dud");
        }
        return result;
    }

    public static User findByUsername(String name) {
        User result = new User();
        result = users().findOne("{username: #}", name).as(User.class);
        if (result == null){
          System.out.println("that username was a dud");
        }
        return result;
    }

    /**
     * Checks if user as entered a valid email and password
     * @param email
     * @param password
     * @return true if correct email and password and false otherwise
     */
    public static Boolean authenticate(String email, String password) {

        User temp = users().findOne("{email: #}", email).as(User.class);

        if (temp == null) {
            return false;
        }

        return BCrypt.checkpw(password, temp.password);
    }

    public static void add(User userObjId, String ballotid){
        // DBObject listItem = new BasicDBObject("voteHistory", new BasicDBObject("id",ballotid));
        // DBObject updateQuery = new BasicDBObject("$push", listItem);
        // DBObject find = new BasicDBObject("_id", userObjId);
        // users().update("{_id}", updateQuery.toString());
        users().update("{_id: #}", userObjId.id).with("{$push:{voteHistory: #}}", ballotid);
        //{$addToSet:{bodyParameters:#}}
    }

    public static void addFavorite(User userObjId, String ballotid){
        // DBObject listItem = new BasicDBObject("voteHistory", new BasicDBObject("id",ballotid));
        // DBObject updateQuery = new BasicDBObject("$push", listItem);
        // DBObject find = new BasicDBObject("_id", userObjId);
        // users().update("{_id}", updateQuery.toString());
        users().update("{_id: #}", userObjId.id).with("{$push:{favoriteHistory: #}}", ballotid);
        //{$addToSet:{bodyParameters:#}}
    }

    public static void removeFavorite(User userObjId, String ballotid){
        // DBObject listItem = new BasicDBObject("voteHistory", new BasicDBObject("id",ballotid));
        // DBObject updateQuery = new BasicDBObject("$push", listItem);
        // DBObject find = new BasicDBObject("_id", userObjId);
        // users().update("{_id}", updateQuery.toString());
        users().update("{_id: #}", userObjId.id).with("{$pull:{favoriteHistory: #}}", ballotid);
        //{$addToSet:{bodyParameters:#}}
    }

    public static void addFavoriteHistoryVotes(User userObjId, String id, String up, String down){
        String ballotinfo = id + "?up=" + up + "+down=" + down;
        users().update("{_id: #}", userObjId.id).with("{$push:{favoriteHistoryWithVotes: #}}", ballotinfo);

    }

    public static void removeFavoriteHistoryVotes(User userObjId, String id, String up, String down){
      String ballotinfo = id + "?up=" + up + "+down=" + down;
      users().update("{_id: #}", userObjId.id).with("{$pull:{favoriteHistoryWithVotes: #}}", ballotinfo);
    }

    public static Boolean hasUpdates(int upvotes, int downvotes, String favString){
        String downstuff = favString.substring(favString.lastIndexOf('+') + 1);
        String final_down = downstuff.split("=")[1];
        String upstuff = favString.substring(favString.lastIndexOf('?') +1, favString.lastIndexOf('+'));
        String final_up = upstuff.split("=")[1];
        if (Integer.parseInt(final_up) == upvotes && Integer.parseInt(final_down) == downvotes){
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
        if (users().findOne("{email: #}", email).as(User.class) != null || users().findOne("{username: #}", username).as(User.class) != null) {
            return true;
        } else {
          return false;
        }
    }

    /**
     * Checks if a user has voted on a ballot
     * @param ballotId id of the ballot
     * @return true if the user has voted, false otherwise
     */
    public Boolean voted(String ballotId) {
      if (this == null) {
          return false;
        }
        return (this.voteHistory.contains(ballotId));
    }

    public Boolean isFavorite(String ballotId) {
        if (this == null) {
          return false;
        }
        return (this.favoriteHistory.contains(ballotId));
    }
}

package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import uk.co.panaxiom.playjongo.*;

import java.util.ArrayList;
import java.util.List;

public class Ballot {

    public static MongoCollection ballots() {
        return PlayJongo.getCollection("ballots");
    }

    @JsonProperty("_id")
    public ObjectId id;

    public String ballotName;
    public String description;
    public String ownerUsername;
    public int voteCount;
    public int upvotes;
    public int downvotes;

    /**
     * Creates a new Ballot
     * @param ballotName name of ballot
     * @param description textual description
     * @param ownerUsername username of the owner of the ballot
     */
    public void create(String ballotName, String description, String ownerUsername) {
        this.ballotName = ballotName;
        this.description = description;
        this.ownerUsername = ownerUsername;
        voteCount = upvotes = downvotes = 0;
    }

    /**
     * Increases or decreases the vote count of a Ballot
     * @param ballotId id of the Ballot to upvote/downvote
     * @param upvote true for upvote, false for downvote
     */
    public static Boolean vote(ObjectId ballotId, Boolean upVote) {
        if (upVote) {
            ballots().update("{_id: #}", ballotId).with("{$inc: {voteCount: 1}}");
            ballots().update("{_id: #}", ballotId).with("{$inc: {upvotes: 1}}");
            return true;
        } else {
            ballots().update("{_id: #}", ballotId).with("{$inc: {voteCount: -1}}");
            ballots().update("{_id: #}", ballotId).with("{$inc: {downvotes: 1}}");
            return false;
        }
    }

    /**
     * Inserts this ballot into the database
     */
    public void insert() {
        ballots().save(this);
    }

    /**
     * Removes this ballot from database
     */
    public void remove() {
        ballots().remove(this.id);
    }

    /**
     * Finds ballot matching the provided id
     * @param id, id of the ballot to query
     * @return Ballot class if a match is found
     */
    public static Ballot findById(ObjectId id) {
        return ballots().findOne("{_id: #}", id).as(Ballot.class);
    }

    /**
     * Retrieves all the ballots stored in the database
     * @return MongoCursor containing the ballots in the collection
     */
    public static List<Ballot> findAll() {
        MongoCursor<Ballot> cursor = ballots().find("{}").as(Ballot.class);

        List<Ballot> ballotCollection = new ArrayList<>();

        for (Ballot b: cursor) {
            ballotCollection.add(b);
        }

        return ballotCollection;
    }

    /**
     * Checks if a ballot has already been created
     * @param name
     * @param desc
     * @return true if a matching ballot is found, false otherwise
     */
    public static Boolean exists(String name, String desc) {
        if (ballots().findOne("{ $and: [{ballotName: #},{description: #}]}", name, desc).as(Ballot.class) == null) {
            return false;
        }
        return true;
    }
}

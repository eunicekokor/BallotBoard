package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.*;

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

    public void insert() {
        ballots().save(this);
    }

    public void remove() {
        ballots().remove(this.id);
    }

    public static Ballot findByName(String name) {
        return ballots().findOne("{ballotName: #}", name).as(Ballot.class);
    }

}
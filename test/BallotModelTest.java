import java.util.*;

import org.junit.*;

import play.Application;
import play.GlobalSettings;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

import models.*;

import play.api.Play;
import play.api.test.FakeApplication;

import org.jongo.MongoCollection;

public class BallotModelTest {

  private User _user;
  private Ballot _ballot;
  private Ballot _ballot2;
  private Ballot _ballot3;
  private String ballotName;
  private String description;
  private String ownerUsername;

  @Test
  public void create(){
    running(fakeApplication(), new Runnable(){
      public void run(){
        _ballot = new Ballot();
        _ballot.create("Example", "testing ballots", "ek");
        _ballot.insert();
        assertEquals(_ballot.ballotName, "Example");
      }
    });
  }

  @Test
  public void vote(){
    running(fakeApplication(), new Runnable(){
      public void run(){
        _ballot = new Ballot();
        _ballot.create("Example", "testing ballots", "ek");
        _ballot.insert();

        // _ballot.vote(_ballot.id, true);  // Upvote
        assertTrue(_ballot.vote(_ballot.id, true));

        // _ballot.vote(_ballot.id, true);  // Upvote
        assertFalse(_ballot.vote(_ballot.id, false));

      }
    });
  }

  @Test
  public void findById(){
    running(fakeApplication(), new Runnable(){
      public void run(){
        _ballot = new Ballot();
        _ballot.create("Example", "testing ballots", "ek");
        _ballot.insert();
        _ballot2 = new Ballot();

        _ballot2 = Ballot.findById(_ballot.id);
        assertEquals(_ballot2.id, _ballot.id);
      }
    });
  }

  @Test
  public void exists(){
    running(fakeApplication(), new Runnable(){
      public void run(){
        _ballot = new Ballot();
        _ballot.create("Example", "testing ballots", "ek");
        _ballot.insert();

        Boolean nameExists = Ballot.exists(_ballot.ballotName, "blah");
        Boolean descExists = Ballot.exists("blah", _ballot.description);
        Boolean nameAndDesc = Ballot.exists(_ballot.ballotName, _ballot.description);

        assertFalse(nameExists);  // Has to be name and descr
        assertFalse(descExists);  // Same as above
        assertTrue(nameAndDesc);
      }
    });
  }
}

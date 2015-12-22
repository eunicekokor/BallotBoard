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

public class UserModelTest {

  private User _user;
  private Ballot _ballot;
  private User _user2;
  private User _user3;

  @Test
  public void create(){
    running(fakeApplication(), new Runnable(){
      public void run() {
          _user = new User();
          _user.create("test", "tester", "test@test.com", "pw");
          _user.insert();
          assertEquals(_user.username, "test");
          _user2 = new User();
          _user2.create("","", "", "");
          assertEquals(_user2.username, "");

      }
    });
  }

  @Test
  public void findEmail(){
    running(fakeApplication(), new Runnable(){
      public void run() {
        User _user = new User();
        _user.create("a", "a", "a@a.com", "a");
        _user.insert();
        _user2 = User.findByEmail("a@a.com");
        _user3 = User.findByEmail("a@a.com");
        assertEquals(_user2.email, _user3.email);
      }
    });
  }

  @Test
  public void findUsername(){
    running(fakeApplication(), new Runnable(){
      public void run() {
        User _user = new User();
        _user.create("b", "b", "b@b.com", "b");
        _user.insert();
        _user2 = User.findByUsername("b");
        _user3 = User.findByUsername("b");
        assertEquals(_user2.username, _user3.username);
      }
    });
  }

  @Test
  public void authenticate(){
    running(fakeApplication(), new Runnable(){
      public void run() {
        User _user = new User();
        _user.create("c", "c", "c@c.com", "right_pw");
        _user.insert();
        Boolean wrong_pw = User.authenticate("c@c.com", "wrong_pw");
        Boolean right_pw = User.authenticate("c@c.com", "right_pw");
        assertTrue(right_pw);
        assertFalse(wrong_pw);
      }
    });
  }

  @Test
  public void exists(){
    running(fakeApplication(), new Runnable(){
      public void run(){
        User _user = new User();
        _user.create("d", "d", "d@d.com", "ddddd");
        _user.insert();
        _user2 = User.findByEmail("d@d.com");

        Boolean existing_email = User.exists(_user2.email, "fake");

        Boolean existing_username = User.exists("fake@fake.com", _user2.username);

        Boolean email_and_user = User.exists(_user2.email, _user2.username);

        Boolean not_exist = User.exists("fake@fake.com", "fake");

        assertTrue(existing_email);
        assertTrue(existing_username);
        assertTrue(email_and_user);
        assertFalse(not_exist);
      }
    });
  }

  @Test
  public void voted(){
    running(fakeApplication(), new Runnable(){
      public void run(){
        User _user = new User();
        _user.create("e", "e", "e@e.com", "eeeee");
        _user.insert();
        String fakeBallotId = "12345";
        String realBallotId = "77777";
        _user.voteHistory.add(realBallotId);
        Boolean not_voted = _user.voted(fakeBallotId);
        Boolean has_voted = _user.voted(realBallotId);
        assertFalse(not_voted);
        assertTrue(has_voted);
      }
    });
  }

   @Test
  public void favoriteTest(){
    running(fakeApplication(), new Runnable(){
      public void run(){
        User _user = new User();
        _user.create("e", "e", "e@e.com", "eeeee");
        _user.insert();
        String fakeBallotId = "12345";
        String realBallotId = "77777";
        _user.favoriteHistory.add(realBallotId);
        _user.favoriteHistoryWithVotes.add(realBallotId+"?up=7+down=6");

        // Check if Favorite
        Boolean not_favorited = _user.isFavorite(fakeBallotId);
        Boolean has_favorited = _user.isFavorite(realBallotId);
        assertFalse(not_favorited);
        assertTrue(has_favorited);
      }
    });
  }



}

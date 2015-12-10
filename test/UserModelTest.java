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
        User _user = new User();
          _user.create("test", "tester", "test@test.com", "pw");
            _user.insert();
          assertEquals(_user.username, "test");
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





}

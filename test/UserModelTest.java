import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
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

public class UserModelTest {

  private User _user;
  private JsonNode _notificationJsonNode;
  private Ballot _ballot;

  // @Before
  // public void setup() {

  //   _user = new User("test", "tester", "test@test.com", "pw");
  //   // _user.create("test", "tester", "test@test.com", "pw");
  //   // _user.insert();
  // }

  @Test
  public void checkUser() {
        // User _user = new User("test", "tester", "test@test.com", "pw");
        //_user = User("test", "tester", "test@test.com", "pw");
        // Assert.assertEquals(_user.password, "pw");
        // User testUser = User.findByEmail("test@test.com");

        // Assert.assertNotNull(testUser);

        // boolean matches = User.authenticate(testUser.email, testUser.password);
        // System.out.println(testUser);

        // Assert.assertTrue("Password does not match but should match", matches);
        // SignUp.validate
        //Assert.assertFalse("Password matches but should not match", badPassword);
  }

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
  public void remove(){

  }





}

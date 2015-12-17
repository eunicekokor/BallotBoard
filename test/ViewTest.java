import java.util.*;

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

public class ViewTest {

    private User _user = new User();


    private Ballot _ballot1 = new Ballot();
    private Ballot _ballot2 = new Ballot();

    List<Ballot> ballotList = new ArrayList<Ballot>();

    @Test
    public void renderBallot() {
        Content html = views.html.ballot.render(ballotList, "BALLOT", _user);
        assertEquals(contentType(html), "text/html");
        String content= Helpers.contentAsString(html);
        Assert.assertTrue(content.contains("BALLOT"));
    }


    @Test
    public void renderIndexView() {
        Content html = views.html.index.render();
        assertEquals(contentType(html), "text/html");
        String content= Helpers.contentAsString(html);
        Assert.assertTrue(content.contains("Welcome to BallotBoard"));
     }


    @Test
    public void renderLoginView(){

        running(fakeApplication(), new Runnable(){
            public void run() {
                Result result = route(controllers.routes.Application.login());
                assertEquals("text/html", result.contentType());
                assertTrue(contentAsString(result).contains("View All Ballots"));
            }
        });
    }


    @Test
    public void renderSignUpView(){
        running(fakeApplication(), new Runnable(){
            public void run() {
                Result result = route(controllers.routes.Application.signup());
                assertEquals("text/html", result.contentType());
                assertTrue(contentAsString(result).contains("Register"));
            }
        });
    }
}

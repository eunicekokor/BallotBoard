import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import java.util.ArrayList;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;

import controllers.*;
import models.*;


public class ApplicationTest extends WithApplication {
    
    @Test
    public void testIndex() {
        Result result = new Application().index();
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType());
        assertEquals("utf-8", result.charset());
        assertTrue(contentAsString(result).contains("Create and vote on Ballots"));
    }

    @Test
    public void testLoginRoute() {
        Result result = route(controllers.routes.Application.login());
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType());
        assertEquals("utf-8", result.charset());
        assertTrue(contentAsString(result).contains("Create Account"));
    }

    @Test
    public void testLogoutRoute() {
        Result result = route(controllers.routes.Application.logout());
        assertEquals(303, result.status()); // Redirects user to login page (status code 303)
        assertEquals(null, result.contentType());
        assertEquals(null, result.charset());
    }

    @Test
    public void testSignupRoute() {
        Result result = route(controllers.routes.Application.signup());
        assertEquals(OK, result.status());
    }

    @Test
    public void testBallotRoute() {
        Result result = route(controllers.routes.Application.ballot());
        assertEquals(OK, result.status());
    }

    /**
     * Route requires user authentication, should redirect user to login page
     * thus expected status code is 303
     */
    @Test
    public void testUserRoute() {
        Result result = route(controllers.routes.Application.user());
        assertEquals(303, result.status());
    }

    @Test
    public void testBallotViewRoute() {
        Result result = route(controllers.routes.Application.ballotView("InvalidString"));
        System.out.println(result.status());
        assertEquals(303, result.status());
    }

}


/*
GET     /                  controllers.Application.index()                 DONE

GET     /login             controllers.Application.login()                 DONE

POST    /login             controllers.Application.authenticate()

GET     /signup            controllers.Application.signup()                DONE

POST    /signup            controllers.Application.register()

GET     /user              controllers.Application.user()                  DONE

GET     /ballot            controllers.Application.ballot()                DONE

GET     /ballot/:id        controllers.Application.ballotView(id: String)  DONE

GET     /create            controllers.Application.ballotForm()

POST    /create            controllers.Application.create()

GET     /logout            controllers.Application.logout()                DONE

GET     /ballot/:id/:vote  controllers.Application.ballotVote(id: String, vote: Boolean)


TODO create fake posts requests
     try non-existent routes




*/
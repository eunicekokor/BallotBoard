import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import org.junit.Test;
import org.jongo.MongoCollection;

import play.Application;
import play.mvc.Result;
import play.mvc.Http;
import play.test.FakeApplication;
import play.twirl.api.Content;

import controllers.*;
import models.*;

public class ApplicationTest {

    @Test
    public void indexRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.index());
                assertEquals(OK, result.status());
                assertEquals("text/html", result.contentType());
                assertEquals("utf-8", result.charset());
                assertTrue(contentAsString(result).contains("Create and vote on Ballots"));
            }
        });
    }

    @Test
    public void loginRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.login());
                assertEquals(OK, result.status());
                assertEquals("text/html", result.contentType());
                assertEquals("utf-8", result.charset());
                assertTrue(contentAsString(result).contains("Create Account"));
            }
        });
    }

    @Test
    public void logoutRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.logout());
                assertEquals(303, result.status());
                assertEquals(null, result.contentType());
                assertEquals(null, result.charset());
                assertEquals("/login", result.redirectLocation());
            }
        });
    }

    @Test
    public void signupRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.signup());
                assertEquals(OK, result.status());
                assertEquals("text/html", result.contentType());
                assertEquals("utf-8", result.charset());
                assertTrue(contentAsString(result).contains("Register"));
            }
        });
    }

    @Test
    public void ballotRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.ballot());
                assertEquals(OK, result.status());
                assertEquals("text/html", result.contentType());
                assertEquals("utf-8", result.charset());
                assertTrue(contentAsString(result).contains("All Ballots"));
            }
        });
    }

    /**
     * Route requires user authentication, should redirect user to login page
     * thus expected status code is 303
     */
    @Test
    public void userRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.user());
                assertEquals(303, result.status());
                assertEquals("/login", result.redirectLocation());
            }
        });
    }

    @Test
    public void ballotViewRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.ballotView("Invalid-Ballot-ID"));
                assertEquals(303, result.status());
                Result result2 = route(controllers.routes.Application.ballotView("5648eabc066905a0761e2EUNI"));
                assertEquals(303, result2.status());
                assertEquals("/login", result.redirectLocation());
            }
        });
    }

    @Test
    public void ballotCreationRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(controllers.routes.Application.ballotForm());
                assertEquals(303, result.status());
                assertEquals("/login", result.redirectLocation());
            }
        });
    }

    /**
     * Any undefined route should be redirected to the index page
     */
    @Test
    public void undefinedRoute() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Http.RequestBuilder request = new Http.RequestBuilder();
                request.method("GET");
                request.uri("/invalidroute");
                Result result = route(request);
                assertEquals(303, result.status());
                assertEquals("/", result.redirectLocation());
            }
        });
    }


    @Test
    public void userLogin() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                User user = new User();
                user.create("Fake", "fake", "fake@test.com", "123");
                user.insert();

                Map<String, String> login = new HashMap();
                login.put("email", "fake@test.com");
                login.put("password", "123");

                Http.RequestBuilder request = new Http.RequestBuilder();
                request.method("POST");
                request.uri("/login");
                request.bodyForm(login);

                Result result = route(request);
                assertEquals(303, result.status());
                assertEquals("/ballot", result.redirectLocation());
            }
        });
    }
}

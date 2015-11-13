package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import static play.data.Form.*;
import views.html.index;
import views.html.profile;
import views.html.ballot;
import views.html.login;
import views.html.signup;
import models.User;

public class Application extends Controller {
    /**
     * Handles requests to /login route
     * @return login form
     */
    public Result login() {
        return ok(login.render(form(Login.class)));
    }

    /**
     * Authenticates a user
     * @return
     */
    public Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.Application.ballot()
            );
        }
    }

    /**
     * Creates a new user
     * @return
     */
    public Result register() {
        return ok();
    }

    /**
     * Handles request to the main page /index
     * @return
     */
    public Result index () {
        return ok(index.render("Welcome to BallotBoard"));
    }

    /**
     * Handles requests to /signup for creation of new accounts
     * @return
     */
    public Result signup() {
        return ok(signup.render(""));
    }


    /**
     * Handles /user route
     * @return
     */
    public Result user () {
        return ok(profile.render("Welcome to your profile"));
    }

    /**
     *
     * @return
     */
    public Result ballot() {
        return ok(ballot.render("No ballots to view right now"));
    }

    /**
     * Form that holds login email and password
     */
    public static class Login {

        public String email;
        public String password;
        //public static User loginUser;
        /**
         * Validates user's input on sign in
         * @return
         */
        public String validate() {
            //loginUser = new User();
            if (User.authenticate(email, password) == null) {
                return "Invalid Email and/or Password";
            }
            return null;
        }

    }
}

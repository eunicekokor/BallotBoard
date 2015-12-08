package controllers;

import models.Ballot;
import models.User;
import org.bson.types.ObjectId;

import play.mvc.Controller;
import play.mvc.Result;

import play.data.*;
import static play.data.Form.*;

import play.mvc.Security;
import views.html.*;

public class Application extends Controller {

    private String currentUser = "";
    private User userObject = null;

    /**
     * Handles requests to /login route
     * @return login form
     */
    public Result login() {
        return ok(login.render(form(Login.class)));
    }

    /**
     * Logs out current user
     */
    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        currentUser = "";
        return redirect(
            routes.Application.login()
        );
    }

    /**
     * Authenticates a user
     */
    public Result authenticate() {

        Form<Login> loginForm = form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            currentUser = User.findByEmail(loginForm.get().email).username;
            session("username", User.findByEmail(loginForm.get().email).username);
            return redirect(
                routes.Application.ballot()
            );
        }
    }

    /**
     * Creates a new user
     */
    public Result register() {
        Form<SignUp> signUpForm = form(SignUp.class).bindFromRequest();

        if (signUpForm.hasErrors()) {
            return badRequest(signup.render(signUpForm));
        } else {
            User newUser = new User();
            newUser.create(signUpForm.get().username, signUpForm.get().fullname,
                    signUpForm.get().email, signUpForm.get().password);
            newUser.insert();
            userObject = User.findByUsername(signUpForm.get().username);
            session().clear();
            session("username", signUpForm.get().username);
            currentUser = signUpForm.get().username;
            return redirect(
                    routes.Application.ballot()
            );
        }
    }

    /**
     * Handles request to the main page /index
     */
    public Result index () {
        return ok(index.render());
    }

    /**
     * Handles requests to /signup for creation of new accounts
     */
    public Result signup() {
        return ok(signup.render(form(SignUp.class)));
    }

    /**
     * Create a new ballot
     */
    @Security.Authenticated(Secured.class)
    public Result create() {
        Form<BallotForm> bForm = form(BallotForm.class).bindFromRequest();

        if (bForm.hasErrors()) {
            currentUser = session("username");
            return badRequest(ballotForm.render(bForm, currentUser));
        } else {
            Ballot tmp = new Ballot();
            tmp.create(bForm.get().ballotName, bForm.get().description, request().username());
            tmp.insert();
            Ballot tmp2 = Ballot.findAll().get((Ballot.findAll()).size() - 1);
            return redirect(
                    routes.Application.ballotView(tmp2.id.toString())
            );
        }
    }

    /**
     * Renders form for creating ballots
     */
    @Security.Authenticated(Secured.class)
    public Result ballotForm() {
        currentUser = session("username");
        return ok(ballotForm.render(form(BallotForm.class), currentUser));
    }

    /**
     * Handles requests to /user route. Requires authentication
     */
    @Security.Authenticated(Secured.class)
    public Result user() {
        currentUser = session("username");
        userObject = User.findByUsername(currentUser);
        return ok(profile.render(Ballot.findAll(), Ballot.findAll(), currentUser, userObject));
    }

    /**
     * Handles requests to /ballot route
     */
    public Result ballot() {
        currentUser = session("username");
        if (currentUser == null){
            currentUser = "";
        }
        return ok(ballot.render(Ballot.findAll(), currentUser));
    }

    @Security.Authenticated(Secured.class)
    public Result ballotView(String id) {
        currentUser = session("username");
        ObjectId ballotId = new ObjectId(id);
        return ok(ballotView.render(Ballot.findById(ballotId), currentUser, ""));
    }

    @Security.Authenticated(Secured.class)
    public Result ballotVote(String id, Boolean vote) {

        userObject = User.findByUsername(session("username"));
        ObjectId ballotId = new ObjectId(id);

        if (!userObject.voted(id)) {
            Ballot.vote(ballotId, vote);
            User.add(userObject, id);
        }
        else {
            currentUser = session("username");
            return ok(ballotView.render(Ballot.findById(ballotId), currentUser, "Sorry. You have already voted. " +
                    "Check out other Ballots."));
        }

        return redirect(
            routes.Application.ballotView(id));
    }

    /**
     * Form to hold login details (user email and password)
     */
    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password)) {
                return null;
            }
            return "Invalid Email and/or Password";
        }
    }

    /**
     * Form to store information entered by a new user
     */
    public static class SignUp {

        public String email;
        public String fullname;
        public String username;
        public String password;

        /**
         * Checks if an email or username is already taken
         */
        public String validate() {
            if (!email.matches(".+@.+\\..+")) {
                return "Invalid email address";
            }
            if (username == null || fullname == null || password == null) {
                return "Incomplete form";
            }
            if (User.exists(email, username)) {
                return "Email and/or Username already taken";
            }
            return null;
        }
    }

    /**
     * Form for a creating a new Ballot
     */
    public static class BallotForm {

        public String ballotName;
        public String description;

        public String validate() {
            if (Ballot.exists(ballotName, description)) {
                return "It appears you are trying to create a ballot that already exists!";
            }
            return null;
        }
    }

}

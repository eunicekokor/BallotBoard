package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.profile;
import views.html.ballot;
import models.User;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Welcome to BallotBoard"));
    }

    public Result profile() {
        return ok(profile.render("Welcome to your profile"));
    }

    public Result ballot() {
        return ok(ballot.render("No ballots to view right now"));
    }
}
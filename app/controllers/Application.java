package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
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

    public Result newballot() {
        return ok(newballot.render("No ballots to view right now"));
    }

    public Result newprofile() {
        return ok(newprofile.render("No ballots to view right now"));
    }
}
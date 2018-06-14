package controllers;

import play.mvc.*;

import views.html.*;


public class HomeController extends Controller {


    public Result index() {
        return ok(views.html.Home.index.render());
    }

    public Result getSignup() {
        return ok(views.html.Home.signup.render());
    }

    public Result getLogin() {
        return ok(views.html.Home.login.render());
    }

}

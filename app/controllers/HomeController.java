package controllers;

import play.mvc.*;

import views.html.*;


public class HomeController extends Controller {


    public Result index() {
        return ok(views.html.Home.index.render());
    }

    public Result about() {
        return ok(views.html.Home.about.render());
    }

    public Result contact() {
        return ok(views.html.Home.contact.render());
    }

}

package controllers;

import play.mvc.*;

import views.html.*;


public class HomeController extends Controller {


    public Result index() {
        return ok(views.html.Home.index.render());
    }

    public Result contact() {
        return ok(views.html.Home.contact.render());
    }

    public Result getTicketSent(){return ok(views.html.CustomerTicket.ticketsent.render());}

}

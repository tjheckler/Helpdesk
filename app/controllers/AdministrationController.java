package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class AdministrationController extends Controller
{
    public Result getAdministration()
    {
        return ok(views.html.Administration.admin.render());
    }

    public Result getSignUp() {
        return ok(views.html.Administration.signup.render());
    }

    public Result getLogin() {
        return ok(views.html.Administration.login.render());
    }
}

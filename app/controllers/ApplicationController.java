package controllers;

import play.mvc.Controller;

public class ApplicationController extends Controller
{
    public void login(int siteAdminId, String siteRole)
    {
        session().put("loggedin", "" + siteAdminId);
        session().put("role", "" + siteRole);
    }

    public void logout()
    {
        session().clear();
    }

    public boolean isLoggedIn()
    {
        boolean loggedIn = false;

        if ((session().get("loggedin") != null))
        {
            loggedIn = true;
        }
        return loggedIn;
    }



    public int getLoggedInSiteAdminId()
    {
        return Integer.parseInt(session().get("loggedin"));
    }

    public String getLoggedInSiteAdminRole()
    {
        return session().get("role");
    }
}

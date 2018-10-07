package controllers;

import play.mvc.Controller;

public class ApplicationController extends Controller
{
    public void login(int siteAdminId, String siteRole, String siteAdminName)
    {
        session().put("loggedin", "" + siteAdminId);
        session().put("role", "" + siteRole);
        session().put("name", "" + siteAdminName);
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

    public String getLoggedInSiteAdmin() {return session().get("name");}
}

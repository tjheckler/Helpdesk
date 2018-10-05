package models;

public class SiteRolesValues
{
    private String admin = "1";
    private String technician = "2";
    private String helpdesk = "3";
    private String manager = "4";
    private String noAccess = "5";

    public String getAdmin()
    {
        return admin;
    }

    public String getTechnician()
    {
        return technician;
    }

    public String getHelpdesk()
    {
        return helpdesk;
    }

    public String getManager()
    {
        return manager;
    }

    public String getNoAccess()
    {
        return noAccess;
    }
}

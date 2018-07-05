package models;

import java.util.ArrayList;
import java.util.List;

public class SiteAdminFormValues
{
    private String adminSiteAdminName;
    private String adminEmailAddress;
    private String adminUsername;
    private String adminLocationId;
    private String adminPhoneNumber;
    private String adminSiteRole;
    private String adminPassword;

    public String getAdminPassword()
    {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword)
    {
        this.adminPassword = adminPassword;
    }

    public String getAdminSiteAdminName()
    {
        return adminSiteAdminName;
    }

    public void setAdminSiteAdminName(String adminSiteAdminName)
    {
        this.adminSiteAdminName = adminSiteAdminName;
    }

    public String getAdminEmailAddress()
    {
        return adminEmailAddress;
    }

    public void setAdminEmailAddress(String adminEmailAddress)
    {
        this.adminEmailAddress = adminEmailAddress;
    }

    public String getAdminUsername()
    {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername)
    {
        this.adminUsername = adminUsername;
    }

    public String getAdminLocationId()
    {
        return adminLocationId;
    }

    public void setAdminLocationId(String adminLocationId)
    {
        this.adminLocationId = adminLocationId;
    }

    public String getAdminPhoneNumber()
    {
        return adminPhoneNumber;
    }

    public void setAdminPhoneNumber(String adminPhoneNumber)
    {
        this.adminPhoneNumber = adminPhoneNumber;
    }

    public String getAdminSiteRole()
    {
        return adminSiteRole;
    }

    public void setAdminSiteRole(String adminSiteRole)
    {
        this.adminSiteRole = adminSiteRole;
    }

    public boolean isValid()
    {
        boolean valid = true;

        if (getAdminEmailAddress() == null || getAdminEmailAddress().length() == 0 || !getAdminEmailAddress().contains("@"))
        {
            valid = false;
        }

        if (getAdminPhoneNumber() == null || getAdminPhoneNumber().length() < 10)
        {
            valid = false;
        }

        if(getAdminSiteAdminName() == null || getAdminSiteAdminName().length() < 4)
        {
            valid = false;
        }
        if(getAdminUsername() == null)
        {
            valid = false;
        }

        if(getAdminSiteRole() == null || (!getAdminSiteRole().equals("Admin") && !getAdminSiteRole().equals("User")))
        {
            valid = false;
        }

        if(getAdminLocationId() == null || getAdminLocationId().equals("0"))
        {
            valid = false;
        }

        if(getAdminPassword() == null || getAdminPassword().length() < 8 )
        {
            valid = false;
        }

        return valid;
    }

    public List<String> getMessages()
    {
        List<String> messages = new ArrayList<>();

        if (getAdminEmailAddress() == null || getAdminEmailAddress().length() == 0)
        {
            messages.add(" Please Enter a Valid Email Address. ");
        }
        else if (!getAdminEmailAddress().contains("@"))
        {
            messages.add(" Email Must Contain an @ Symbol. ");
        }

        if (getAdminPhoneNumber() == null || getAdminPhoneNumber().length() < 10)
        {
            messages.add(" Please enter 10 Digit Phone Number Including Area Code. ");
        }

        if(getAdminSiteAdminName() == null || getAdminSiteAdminName().length() < 4)
        {
            messages.add(" Please Enter First and Last Name. ");
        }
        if(getAdminUsername() == null)
        {
            messages.add(" Please Enter a Unique Username. ");
        }

        if(getAdminSiteRole() == null || (!getAdminSiteRole().equals("Admin") && !getAdminSiteRole().equals("User")))
        {
            messages.add(" Please Enter a Role, Either 'User' or 'Admin'. ");
        }

        if(getAdminLocationId() == null || getAdminLocationId().equals("0"))
        {
           messages.add(" Please Select a Home Base Location Within Appropriate Region. ");
        }

        if(getAdminPassword() == null || getAdminPassword().length() < 8 )
        {
            messages.add(" Password Needs Minimum of 8 Characters");
        }

        return messages;
    }
}

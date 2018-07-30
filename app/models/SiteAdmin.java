package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SiteAdmin
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int siteAdminId;
    private String siteAdminName;
    private String emailAddress;
    private String username;
    private byte[] password;
    private byte[] passwordSalt;
    private int locationId;
    private String phoneNumber;
    private int siteRole;  ///change to ID
    private String flag;
    private byte[] picture;

    public byte[] getPicture()
    {
        return picture;
    }

    public void setPicture(byte[] picture)
    {
        this.picture = picture;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public int getSiteAdminId()
    {
        return siteAdminId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public byte[] getPassword()
    {
        return password;
    }

    public void setPassword(byte[] password)
    {
        this.password = password;
    }

    public byte[] getPasswordSalt()
    {
        return passwordSalt;
    }

    public void setPasswordSalt(byte[] passwordSalt)
    {
        this.passwordSalt = passwordSalt;
    }

    public String getSiteAdminName()
    {
        return siteAdminName;
    }

    public void setSiteAdminName(String siteAdminName)
    {
        this.siteAdminName = siteAdminName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }


    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public int getSiteRole()
    {
        return siteRole;
    }

    public void setSiteRole(int siteRole)
    {
        this.siteRole = siteRole;
    }

    public int getLocationId()
    {
        return locationId;
    }

    public void setLocationId(int locationId)
    {
        this.locationId = locationId;
    }
}

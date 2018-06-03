package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class SiteAdmin
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int siteAdminId;
    private String siteAdminName;
    private String emailAddress;
    private int regionId;
    private int locationId;
    private int phoneNumber;
    private String siteRole;

    public SiteAdmin(int siteAdminId,int locationId, String siteAdminName, String emailAddress, int regionId, int phoneNumber, String siteRole)
    {
        this.siteAdminId = siteAdminId;
        this.siteAdminName = siteAdminName;
        this.emailAddress = emailAddress;
        this.regionId = regionId;
        this.phoneNumber = phoneNumber;
        this.siteRole = siteRole;
        this.locationId = locationId;
    }

    public int getSiteAdminId()
    {
        return siteAdminId;
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

    public int getRegionId()
    {
        return regionId;
    }

    public void setRegionId(int regionId)
    {
        this.regionId = regionId;
    }

    public long getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getSiteRole()
    {
        return siteRole;
    }

    public void setSiteRole(String siteRole)
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

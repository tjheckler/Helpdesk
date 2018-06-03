package models;

public class SiteAdmin
{
    private int siteAdminId;
    private String siteAdminName;
    private String emailAddress;
    private int regionId;
    private long phoneNumber;
    private String siteRole;

    public SiteAdmin(int siteAdminId, String siteAdminName, String emailAddress, int regionId, long phoneNumber, String siteRole)
    {
        this.siteAdminId = siteAdminId;
        this.siteAdminName = siteAdminName;
        this.emailAddress = emailAddress;
        this.regionId = regionId;
        this.phoneNumber = phoneNumber;
        this.siteRole = siteRole;
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

    public void setPhoneNumber(long phoneNumber)
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
}

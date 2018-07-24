package models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SiteRole
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int siteRoleId;

    private String siteRoleName;

    public int getSiteRoleId()
    {
        return siteRoleId;
    }

    public void setSiteRoleId(int siteRoleId)
    {
        this.siteRoleId = siteRoleId;
    }

    public String getSiteRoleName()
    {
        return siteRoleName;
    }

    public void setSiteRoleName(String siteRoleName)
    {
        this.siteRoleName = siteRoleName;
    }
}

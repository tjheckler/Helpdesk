package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketSiteAdminCount
{
    @Id
    private int siteAdminId;

    private String siteAdminName;
    private long count;
    public  TicketSiteAdminCount(int siteAdminId, String siteAdminName, long count)
    {


        this.siteAdminId = siteAdminId;
        this.siteAdminName = siteAdminName;
        this.count = count;

    }
        public int getSiteAdminId()
        {
            return siteAdminId;
        }

        public String getSiteAdminName()
        {
            return siteAdminName;
        }

        public long getCount()
        {
            return count;
        }
    }




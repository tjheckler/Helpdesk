package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketRegionCount
{
    @Id
    private int regionId;

    private String regionName;
    private long count;
    public  TicketRegionCount(int regionId, String regionName, long count)
    {


        this.regionId = regionId;
        this.regionName = regionName;
        this.count = count;

    }
    public int getRegionId()
    {
        return regionId;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public long getCount()
    {
        return count;
    }
}

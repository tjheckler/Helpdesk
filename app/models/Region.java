package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Region
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regionId;
    private String regionName;

    public Region(int regionId, String regionName)
    {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public int getRegionId()
    {
        return regionId;
    }


    public String getRegionName()
    {
        return regionName;
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }
}

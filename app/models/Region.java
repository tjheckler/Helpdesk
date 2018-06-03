package models;

public class Region
{
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

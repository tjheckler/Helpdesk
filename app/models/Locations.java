package models;

public class Locations
{
    private int locationsId;
    private String locationsName;

    public Locations(int locationsId, String locationsName)
    {
        this.locationsId = locationsId;
        this.locationsName = locationsName;
    }

    public int getLocationsId()
    {
        return locationsId;
    }

    public void setLocationsId(int locationsId)
    {
        this.locationsId = locationsId;
    }

    public String getLocationsName()
    {
        return locationsName;
    }

    public void setLocationsName(String locationsName)
    {
        this.locationsName = locationsName;
    }
}

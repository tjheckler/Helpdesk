package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationsId;
    private String locationsName;

    public Location(int locationsId, String locationsName)
    {
        this.locationsId = locationsId;
        this.locationsName = locationsName;
    }

    public int getLocationsId()
    {
        return locationsId;
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

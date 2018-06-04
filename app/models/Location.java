package models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;
    private String locationName;


    public int getLocationId()
    {
        return locationId;
    }


    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }
}

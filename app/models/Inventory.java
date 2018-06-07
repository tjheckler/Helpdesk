package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Inventory
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventoryId;
    private int assetTagNumber;
    private String computerName;
    private int locationId;
    private String currentUser;
    private String buildingLocation;


    public int getAssetTagNumber()
    {
        return assetTagNumber;
    }

    public void setAssetTagNumber(int assetTagNumber)
    {
        this.assetTagNumber = assetTagNumber;
    }

    public int getInventoryId()
    {
        return inventoryId;
    }


    public String getComputerName()
    {
        return computerName;
    }

    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

    public int getLocationId()
    {
        return locationId;
    }

    public void setLocationId(int locationId)
    {
        this.locationId = locationId;
    }

    public String getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(String currentUser)
    {
        this.currentUser = currentUser;
    }

    public String getBuildingLocation()
    {
        return buildingLocation;
    }

    public void setBuildingLocation(String buildingLocation)
    {
        this.buildingLocation = buildingLocation;
    }


}

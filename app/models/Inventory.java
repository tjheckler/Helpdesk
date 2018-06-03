package models;

public class Inventory
{
    private int inventoryId;
    private String computerName;
    private String location;
    private String currentUser;
    private String buildingLocation;

    public Inventory(int inventoryId, String computerName, String location,
                     String currentUser, String buildingLocation)
    {
        this.inventoryId = inventoryId;
        this.computerName = computerName;
        this.location = location;
        this.currentUser = currentUser;
        this.buildingLocation = buildingLocation;
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

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
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

package models;

import java.util.ArrayList;
import java.util.List;

public class InventoryFormValues
{
    private String inventoryAssetTagNumber;
    private String inventoryComputerName;
    private String inventoryLocationId;
    private String inventoryCurrentUser;
    private String inventoryBuildingLocation;

    public String getInventoryAssetTagNumber()
    {
        return inventoryAssetTagNumber;
    }

    public void setInventoryAssetTagNumber(String inventoryAssetTagNumber)
    {
        this.inventoryAssetTagNumber = inventoryAssetTagNumber;
    }

    public String getInventoryComputerName()
    {
        return inventoryComputerName;
    }

    public void setInventoryComputerName(String inventoryComputerName)
    {
        this.inventoryComputerName = inventoryComputerName;
    }

    public String getInventoryLocationId()
    {
        return inventoryLocationId;
    }

    public void setInventoryLocationId(String inventoryLocationId)
    {
        this.inventoryLocationId = inventoryLocationId;
    }

    public String getInventoryCurrentUser()
    {
        return inventoryCurrentUser;
    }

    public void setInventoryCurrentUser(String inventoryCurrentUser)
    {
        this.inventoryCurrentUser = inventoryCurrentUser;
    }

    public String getInventoryBuildingLocation()
    {
        return inventoryBuildingLocation;
    }

    public void setInventoryBuildingLocation(String inventoryBuildingLocation)
    {
        this.inventoryBuildingLocation = inventoryBuildingLocation;
    }

    public boolean isValid()
    {
        boolean valid = true;

        if (getInventoryComputerName() == null)
        {
            valid = false;
        }

        if (getInventoryAssetTagNumber() == null )
        {
            valid = false;
        }

        if (getInventoryBuildingLocation() == null)
        {
            valid = false;
        }

        if (getInventoryCurrentUser() == null)
        {
            valid = false;
        }

        if (getInventoryLocationId() == null || getInventoryLocationId().equals("0"))
        {
            valid = false;
        }

        return valid;
    }

    public List<String> getMessages()
    {
        List<String> messages = new ArrayList<>();

        if (getInventoryComputerName() == null)
        {
            messages.add(" Please Enter a Computer Name. ");
        }


        if (getInventoryAssetTagNumber() == null )
        {
            messages.add(" Please Enter an Asset Tag Number. ");
        }

        if (getInventoryBuildingLocation() == null)
        {
            messages.add(" Please Enter a Building Location. ");
        }

        if (getInventoryCurrentUser() == null)
        {
            messages.add(" Please Enter the Current User. ");
        }

        if (getInventoryLocationId() == null || getInventoryLocationId().equals("0"))
        {
            messages.add(" Please Select a Location. ");
        }

        return messages;
    }
}

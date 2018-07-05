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

        if (getInventoryComputerName() == null || (getInventoryComputerName().length( )==0 || getInventoryComputerName().length() > 50))
        {
            messages.add(" Please Enter a Computer Name less than 50 Characters. ");
        }


        if (getInventoryAssetTagNumber() == null || getInventoryAssetTagNumber().length( )== 0 || getInventoryAssetTagNumber().length() > 50)
        {
            messages.add(" Please Enter an Asset Tag Number less than 50 Characters. ");
        }

        if (getInventoryBuildingLocation() == null || (getInventoryBuildingLocation().length( )==0 || getInventoryBuildingLocation().length() > 50))
        {
            messages.add(" Please Enter a Building Location Less than 50 Characters. ");
        }

        if (getInventoryCurrentUser() == null || (getInventoryCurrentUser().length( )==0 || getInventoryCurrentUser().length() > 50))
        {
            messages.add(" Please Enter the Current User less than 50 Characters. ");
        }

        if (getInventoryLocationId() == null || getInventoryLocationId().equals("0"))
        {
            messages.add(" Please Select a Location. ");
        }

        return messages;
    }
}

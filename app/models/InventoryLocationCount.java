package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InventoryLocationCount
{
    @Id
    private int inventoryId;

    private String locationName;
    private long count;

    public InventoryLocationCount(int inventoryId, String locationName, long count)
    {
        this.inventoryId = inventoryId;
        this.locationName = locationName;
        this.count = count;
    }

    public int getInventoryId()
    {
        return inventoryId;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public long getCount()
    {
        return count;
    }
}

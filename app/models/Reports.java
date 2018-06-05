package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Reports
{
    @Id
    private int reportsId;
    private int ticketId;
    private int categoryId;
    private int siteAdminId;
    private int inventoryId;
    private int locationId;
    private int regionId;
    private int replyId;
    private int fileDetailId;
    private int priorityId;



    public int getReportsId()
    {
        return reportsId;
    }

    public int getTicketId()
    {
        return ticketId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public int getSiteAdminId()
    {
        return siteAdminId;
    }

    public int getInventoryId()
    {
        return inventoryId;
    }

    public int getLocationId()
    {
        return locationId;
    }

    public int getRegionId()
    {
        return regionId;
    }

    public int getReplyId()
    {
        return replyId;
    }

    public int getFileDetailId()
    {
        return fileDetailId;
    }

    public int getPriorityId()
    {
        return priorityId;
    }
}

package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketLocationCount
{
    @Id
    private int ticketId;

    private String locationName;
    private long count;
    public  TicketLocationCount(int ticketId, String locationName, long count)
    {


        this.ticketId = ticketId;
        this.locationName = locationName;
        this.count = count;

    }
    public int getTicketId()
    {
        return ticketId;
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

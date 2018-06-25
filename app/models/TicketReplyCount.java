package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketReplyCount
{
    @Id
    private int ticketsId;

    private String computerName;
    private long count;

    public TicketReplyCount(int ticketsId, String computerName, long count)
    {


        this.ticketsId = ticketsId;

        this.computerName = computerName;
        this.count = count;

    }

    public int getTicketsId()
    {
        return ticketsId;
    }

    public String getComputerName()
    {
        return computerName;
    }

    public long getCount()
    {
        return count;
    }
}

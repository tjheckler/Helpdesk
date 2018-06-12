package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketPriorityCount
{
    @Id
    private int priorityId;

    private String priorityName;
    private long count;
    public  TicketPriorityCount(int priorityId, String priorityName, long count)
    {


        this.priorityId= priorityId;
        this.priorityName = priorityName;
        this.count = count;

    }
    public int getPriorityId()
    {
        return priorityId;
    }

    public String getPriorityName()
    {
        return priorityName;
    }

    public long getCount()
    {
        return count;
    }
}

package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TicketStatus
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    private String statusName;

    public int getStatusId()
    {
        return statusId;
    }

    public String getStatusName()
    {
        return statusName;
    }

    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }
}

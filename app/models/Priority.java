package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Priority
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priorityId;
    private String priorityName;


    public int getPriorityId()
    {
        return priorityId;
    }


    public String getPriorityName()
    {
        return priorityName;
    }

    public void setPriorityName(String priorityName)
    {
        this.priorityName = priorityName;
    }
}

package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.StringBufferInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Ticket
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketsId;
    private int categoryId;
    private int siteAdminId;
    private int priorityId;
    private Date statusDateChanged;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private int locationId;
    private String description;
    private String computerName;
    private String assetTagNumber;
    private String subjectTitle;
    private int statusId;


    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getStatusDateChanged()
    {
        return statusDateChanged;
    }

    public void setStatusDateChanged(Date statusDateChanged)
    {
        this.statusDateChanged = statusDateChanged;
    }

    public int getStatusId()
    {
        return statusId;
    }

    public void setStatusId(int statusId)
    {
        this.statusId = statusId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public int getSiteAdminId()
    {
        return siteAdminId;
    }

    public int getPriorityId()
    {
        return priorityId;
    }

    public int getLocationId()
    {
        return locationId;
    }

    public int getTicketsId()
    {
        return ticketsId;
    }


    public int getCategory()
    {
        return categoryId;
    }

    public void setCategory(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getSiteAdmin()
    {
        return siteAdminId;
    }

    public void setSiteAdmin(int siteAdminId)
    {
        this.siteAdminId = siteAdminId;
    }

    public int getPriority()
    {
        return priorityId;
    }

    public void setPriority(int priorityId)
    {
        this.priorityId = priorityId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddres)
    {
        this.emailAddress = emailAddres;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public int getLocation()
    {
        return locationId;
    }

    public void setLocation(int locationId)
    {
        this.locationId = locationId;
    }

    public String getComputerName()
    {
        return computerName;
    }

    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

    public String getAssetTagNumber()
    {
        return assetTagNumber;
    }

    public void setAssetTagNumber(String assetTagNumber)
    {
        this.assetTagNumber = assetTagNumber;
    }

    public String getSubjectTitle()
    {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle)
    {
        this.subjectTitle = subjectTitle;
    }


}

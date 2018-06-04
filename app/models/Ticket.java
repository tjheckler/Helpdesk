package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.StringBufferInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
public class Ticket
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketsId;
    private int categoryId;
    private int siteAdminId;
    private int priorityId;
    private String date;
    private String firstName;
    private String lastName;
    private String emailAddres;
    private long phoneNumber;
    private int locationId;
    private int regionId;
    private String computerName;
    private int assetTagNumber;
    private String subjectTitle;
    private String description;
    private int replyId;
    private int fileDetailsId;



    public int getReply()
    {
        return replyId;
    }

    public void setReply(int reply)
    {
        this.replyId = reply;
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

    public void setPriority(int priority)
    {
        this.priorityId = priorityId;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmailAddres()
    {
        return emailAddres;
    }

    public void setEmailAddres(String emailAddres)
    {
        this.emailAddres = emailAddres;
    }

    public long getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber)
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

    public int getRegion()
    {
        return regionId;
    }

    public void setRegion(int regionId)
    {
        this.regionId = regionId;
    }

    public String getComputerName()
    {
        return computerName;
    }

    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

    public int getAssetTagNumber()
    {
        return assetTagNumber;
    }

    public void setAssetTagNumber(int assetTagNumber)
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getFileDetailsId()
    {
        return fileDetailsId;
    }

    public void setFileDetailsId(int fileDetailsId)
    {
        this.fileDetailsId = fileDetailsId;
    }
}

package models;

import java.io.StringBufferInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ticket
{
    private int ticketsId;
    private int categoryId;
    private int siteAdminId;
    private int priorityId;
    private String date;
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
    private int locationId;
    private int regionId;
    private String computerName;
    private long assetTagNumber;
    private String subjectTitle;
    private String description;
    private int replyId;
    private int fileDetailsId;

    public Ticket(int ticketsId, int categoryId, int siteAdminId, int priorityId, String date,
                  String firstName, String lastName, String email, long phoneNumber, int locationId,
                  int regionId, String computerName, long assetTagNumber, String subjectTitle,
                  String description, int replyId, int fileDetailsId)
    {
        this.ticketsId = ticketsId;
        this.categoryId = categoryId;
        this.siteAdminId = siteAdminId;
        this.priorityId = priorityId;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.locationId = locationId;
        this.regionId = regionId;
        this.computerName = computerName;
        this.assetTagNumber = assetTagNumber;
        this.subjectTitle = subjectTitle;
        this.description = description;
        this.replyId = replyId;
        this.fileDetailsId = fileDetailsId;
    }

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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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

    public long getAssetTagNumber()
    {
        return assetTagNumber;
    }

    public void setAssetTagNumber(long assetTagNumber)
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

package models;

import java.io.StringBufferInputStream;
import java.time.LocalDateTime;

public class Ticket
{
    private int ticketsId;
    private String category;
    private String siteAdmin;
    private String priority;
    private LocalDateTime date;
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
    private String location;
    private String region;
    private String computerName;
    private long assetTagNumber;
    private String subjectTitle;
    private String description;
    private String reply;

    public Ticket(int ticketsId, String category, String siteAdmin, String priority, LocalDateTime date,
                  String firstName, String lastName, String email, long phoneNumber, String location,
                  String region, String computerName, long assetTagNumber, String subjectTitle,
                  String description, String reply)
    {
        this.ticketsId = ticketsId;
        this.category = category;
        this.siteAdmin = siteAdmin;
        this.priority = priority;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.region = region;
        this.computerName = computerName;
        this.assetTagNumber = assetTagNumber;
        this.subjectTitle = subjectTitle;
        this.description = description;
        this.reply = reply;
    }

    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public int getTicketsId()
    {
        return ticketsId;
    }

    public void setTicketsId(int ticketsId)
    {
        this.ticketsId = ticketsId;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getSiteAdmin()
    {
        return siteAdmin;
    }

    public void setSiteAdmin(String siteAdmin)
    {
        this.siteAdmin = siteAdmin;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
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

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
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
}

package models;

import java.util.ArrayList;
import java.util.List;

public class TicketFormValues
{

    private String ticketCategoryId;
    private String ticketSiteAdminId;
    private String ticketPriorityId;
    private String ticketLocationId;
    private String ticketStatusId;
    private String ticketName;
    private String ticketEmailAddress;
    private String ticketPhoneNumber;
    private String ticketDescription;
    private String ticketComputerName;
    private String ticketAssetTagNumber;
    private String ticketSubjectTitle;

    public String getTicketCategoryId()
    {
        return ticketCategoryId;
    }

    public void setTicketCategoryId(String ticketCategoryId)
    {
        this.ticketCategoryId = ticketCategoryId;
    }

    public String getTicketSiteAdminId()
    {
        return ticketSiteAdminId;
    }

    public void setTicketSiteAdminId(String ticketSiteAdminId)
    {
        this.ticketSiteAdminId = ticketSiteAdminId;
    }

    public String getTicketPriorityId()
    {
        return ticketPriorityId;
    }

    public void setTicketPriorityId(String ticketPriorityId)
    {
        this.ticketPriorityId = ticketPriorityId;
    }

    public String getTicketLocationId()
    {
        return ticketLocationId;
    }

    public void setTicketLocationId(String ticketLocationId)
    {
        this.ticketLocationId = ticketLocationId;
    }

    public String getTicketStatusId()
    {
        return ticketStatusId;
    }

    public void setTicketStatusId(String ticketStatusId)
    {
        this.ticketStatusId = ticketStatusId;
    }

    public String getTicketName()
    {
        return ticketName;
    }

    public void setTicketName(String ticketName)
    {
        this.ticketName = ticketName;
    }

    public String getTicketEmailAddress()
    {
        return ticketEmailAddress;
    }

    public void setTicketEmailAddress(String ticketEmailAddress)
    {
        this.ticketEmailAddress = ticketEmailAddress;
    }

    public String getTicketPhoneNumber()
    {
        return ticketPhoneNumber;
    }

    public void setTicketPhoneNumber(String ticketPhoneNumber)
    {
        this.ticketPhoneNumber = ticketPhoneNumber;
    }

    public String getTicketDescription()
    {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription)
    {
        this.ticketDescription = ticketDescription;
    }

    public String getTicketComputerName()
    {
        return ticketComputerName;
    }

    public void setTicketComputerName(String ticketComputerName)
    {
        this.ticketComputerName = ticketComputerName;
    }

    public String getTicketAssetTagNumber()
    {
        return ticketAssetTagNumber;
    }

    public void setTicketAssetTagNumber(String ticketAssetTagNumber)
    {
        this.ticketAssetTagNumber = ticketAssetTagNumber;
    }

    public String getTicketSubjectTitle()
    {
        return ticketSubjectTitle;
    }

    public void setTicketSubjectTitle(String ticketSubjectTitle)
    {
        this.ticketSubjectTitle = ticketSubjectTitle;
    }

    public boolean isValid()
    {
        boolean valid = true;

        if(getTicketCategoryId() == null || getTicketCategoryId().equals("0"))
        {
            valid = false;
        }

        if(getTicketPriorityId() == null || getTicketPriorityId().equals("0"))
        {
            valid = false;
        }

        if(getTicketSiteAdminId() == null || getTicketSiteAdminId().equals("0"))
        {
            valid = false;
        }

        if(getTicketLocationId() == null || getTicketLocationId().equals("0"))
        {
            valid = false;
        }

        if(getTicketStatusId() == null || getTicketStatusId().equals("0"))
        {
            valid = false;
        }

        if(getTicketName() == null || (getTicketName().length() == 0 || getTicketName().length() >50 ))
        {
            valid = false;
        }

        if(getTicketSubjectTitle() == null || (getTicketSubjectTitle().length() == 0 || getTicketSubjectTitle().length() > 50))
        {
            valid = false;
        }

        if(getTicketDescription() ==null || getTicketDescription().length() == 0 || getTicketDescription().length() > 255)
        {
            valid = false;
        }

        if (getTicketEmailAddress() == null || (getTicketEmailAddress().length() == 0 || getTicketEmailAddress().length() > 50)|| !getTicketEmailAddress().contains("@"))
        {
            valid = false;
        }

        if (getTicketPhoneNumber() == null || (getTicketPhoneNumber().length() < 10 || getTicketPhoneNumber().length() > 15))
        {
            valid = false;
        }

        return valid;
    }



    public List<String> getMessages()
    {
        List<String> messages = new ArrayList<>();
        messages.add("* Indicates Require Field");

        if (getTicketEmailAddress() == null || getTicketEmailAddress().length() == 0)
        {
            messages.add(" Please Enter a Valid Email Address. ");
        }
        else if (!getTicketEmailAddress().contains("@"))
        {
            messages.add(" Email Must Contain an @ Symbol. ");
        }

        if (getTicketPhoneNumber() == null || getTicketPhoneNumber().length() < 10)
        {
            messages.add(" Please Enter a Valid 10 Digit Phone Number Including Area Code. ");
        }
        else if (getTicketPhoneNumber().length() > 15)
        {
            messages.add(" Phone Number Length is too Long. ");
        }

        if(getTicketDescription() ==null || getTicketDescription().length() == 0 || getTicketDescription().length() > 255)
        {
            messages.add(" Please Enter a Description Between 1 and 255 Characters Long. ");
        }

        if(getTicketSubjectTitle() == null || getTicketSubjectTitle().length() == 0)
        {
            messages.add(" Please Enter a Subject for the Ticket. ");
        }

        if(getTicketName() == null ||getTicketName().length() < 4 )
        {
            messages.add(" Please Enter Your First and Last Name. ");
        }

        if(getTicketCategoryId() == null || getTicketCategoryId().equals("0"))
        {
            messages.add(" Please Select a Category. ");
        }

        if(getTicketPriorityId() == null || getTicketPriorityId().equals("0"))
        {
            messages.add(" Please Select a Priority. ");
        }

        if(getTicketSiteAdminId() == null || getTicketSiteAdminId().equals("0"))
        {
            messages.add(" Please Select the Assignee for the Ticket. ");
        }

        if(getTicketLocationId() == null || getTicketLocationId().equals("0"))
        {
           messages.add(" Please Select Your Current Location. ");
        }

        if(getTicketStatusId() == null || getTicketStatusId().equals("0"))
        {
            messages.add(" Please Select Appropriate Status. ");
        }

        return messages;
    }

}

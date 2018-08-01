package models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TechNotes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int techNoteId;

    private String techNote;
    private int ticketsId;
    private int siteAdminId;

    public int getTechNotesId()
    {
        return techNoteId;
    }

    public String getTechNotes()
    {
        return techNote;
    }

    public void setTechNotes(String techNotes)
    {
        this.techNote = techNotes;
    }

    public int getTicketsId()
    {
        return ticketsId;
    }

    public void setTicketsId(int ticketsId)
    {
        this.ticketsId = ticketsId;
    }

    public int getSiteAdminId()
    {
        return siteAdminId;
    }

    public void setSiteAdminId(int siteAdminId)
    {
        this.siteAdminId = siteAdminId;
    }
}

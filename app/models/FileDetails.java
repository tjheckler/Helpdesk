package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class FileDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileDetailId;
    private int ticketsId;
    private String fileExtension;
    private byte[] addedFiles;



    public int getTicketId()
    {
        return ticketsId;
    }

    public void setTicketId(int ticketId)
    {
        this.ticketsId = ticketId;
    }

    public int getFileDetailId()
    {
        return fileDetailId;
    }


    public byte[] getAddedFiles()
    {
        return addedFiles;
    }

    public void setAddedFiles(byte[] addedFiles)
    {
        this.addedFiles = addedFiles;
    }

    public String getExtension()
    {
        return fileExtension;
    }

    public void setExtension(String extension)
    {
        this.fileExtension = extension;
    }
}

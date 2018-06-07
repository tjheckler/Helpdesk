package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class FileDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileDetailsId;
    private int ticketId;
    private String extension;
    private byte[] addedFiles;

    public FileDetail(int fileDetailsId,int ticketId, String extension,byte[] addedFiles)
    {
        this.fileDetailsId = fileDetailsId;
        this.ticketId = ticketId;
        this.addedFiles = addedFiles;
        this.extension = extension;
    }

    public int getTicketId()
    {
        return ticketId;
    }

    public void setTicketId(int ticketId)
    {
        this.ticketId = ticketId;
    }

    public int getFileDetailsId()
    {
        return fileDetailsId;
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
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }
}

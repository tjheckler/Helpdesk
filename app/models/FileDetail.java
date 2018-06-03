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
    private String fileName;
    private String extension;

    public FileDetail(int fileDetailsId, String fileName, String extension)
    {
        this.fileDetailsId = fileDetailsId;
        this.fileName = fileName;
        this.extension = extension;
    }

    public int getFileDetailsId()
    {
        return fileDetailsId;
    }


    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
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

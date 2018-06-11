package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reply
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int replyId;
    private String reply;
    private int ticketsId;


    public int getReplyId()
    {
        return replyId;
    }

    public int getTicketsId()
    {
        return ticketsId;
    }

    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public void setTicketsId(int ticketsId)
    {
        this.ticketsId = ticketsId;
    }
}

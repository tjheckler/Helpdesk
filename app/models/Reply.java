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

    public Reply(int replyId, String reply)
    {
        this.replyId = replyId;
        this.reply = reply;
    }

    public int getReplyId()
    {
        return replyId;
    }


    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }
}

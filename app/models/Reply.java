package models;

public class Reply
{
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

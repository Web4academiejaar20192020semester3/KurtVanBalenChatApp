package domain;

import java.util.Date;

public class Message {
    private String text;
    private Person sender;
    private Person receiver;
    private Date date;

    public Message(String message, Person sender, Person receiver)
    {
        setText(message);
        setSender(sender);
        setReceiver(receiver);
        this.date = new Date();
    }

    private void setReceiver(Person receiver) {
        // TODO Auto-generated method stub
        this.receiver = receiver;
    }
    private void setSender(Person sender) {
        // TODO Auto-generated method stub
        this.sender = sender;
    }
    private void setText(String text)
    {
        this.text = text;
    }
    public Person getSender()
    {
        return sender;
    }
    public Person getReceiver()
    {
        return receiver;
    }
    public String getText()
    {
        return text;
    }
    public Date getDate()
    {
        return date;
    }
    public boolean equals(Object o){
        if (!(o instanceof Message)){
            return false;
        }
        Message message2 = (Message) o;
        if (this.getDate().equals(message2.getDate())){
            return true;
        }
        return false;
    }
}

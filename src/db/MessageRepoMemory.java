package db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Message;
import domain.Person;

public class MessageRepoMemory {
    List<Message> oldMessages;
    List<Message> newMessages;

    public MessageRepoMemory()
    {
        oldMessages = new ArrayList<Message>();
        newMessages = new ArrayList<Message>();
    }
    public void addNewMessage(Message m) {
        // TODO Auto-generated method stub
        newMessages.add(m);
    }

    public void addOldMessage(Message m) {
        // TODO Auto-generated method stub
        oldMessages.add(m);
    }
    public String getMessagesAsJSON()
    {
        return getMessagesAsJSON(this.oldMessages);
    }

    public String getMessagesFriendAsJSON(Person friend) {
        // TODO Auto-generated method stub

        Iterator iterator = oldMessages.iterator();
        List<Message> matchedMessages = new ArrayList<Message>();
        ArrayList<Message> oldMessages2 = new ArrayList<Message>();
        while (iterator.hasNext()){
            Message m = (Message) iterator.next();

            if (m.getSender().equals(friend) || m.getReceiver().equals(friend)){
                oldMessages2.add(m);
                matchedMessages.add(m);
            }
        }
        oldMessages = oldMessages2;
        return getMessagesAsJSON(matchedMessages);
    }

    public String getNewMessagesJSON(Person friend, Person me) {
        List<Message> newMessagesS = this.newMessages;
        this.newMessages = new ArrayList<Message>();
        return getMessagesAsJSON(newMessagesS);
    }
    public String getNewMessagesPartnerAsJSON(Person friend, Person me){
        if (newMessages.size() == 0){
            return getMessagesAsJSON(new ArrayList<Message>());
        }
        Iterator iterator = newMessages.iterator();
        List<Message> matchedMessages = new ArrayList<Message>();
        while (iterator.hasNext()){
            Message m = (Message) iterator.next();
            if (m.getSender().equals(friend) || m.getReceiver().equals(friend)){
                oldMessages.add(m);
                matchedMessages.add(m);
            }
        }
        newMessages.removeAll(matchedMessages);
        return getMessagesAsJSON(matchedMessages);
    }

    public String getMessagesAsJSON(List<Message> messages) {
        // TODO Auto-generated method stub
        StringBuffer stringBuffer = new StringBuffer();
        Iterator iterator = messages.iterator();
        stringBuffer.append("{ \"Message\":[");
        while (iterator.hasNext()) {
            Message m = (Message) iterator.next();
            stringBuffer.append("{");
            stringBuffer.append("\"sender\": \"" + m.getSender().getName() + "\",");
            stringBuffer.append("\"receiver\": \"" + m.getReceiver().getName() + "\"," );
            stringBuffer.append("\"message\": \"" + m.getText() + "\"" );
            stringBuffer.append("}");
            if (iterator.hasNext()){
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("]");
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

}

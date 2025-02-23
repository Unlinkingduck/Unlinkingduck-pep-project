package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;
import java.util.ArrayList;

public class MessageService {
    public MessageDAO messageDAO; 

    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public Message insertMessage(Message message)
    {
        //check stuff to make sure its legal
        String body = message.getMessage_text();
        if (body.length() > 0 && body.length() <= 255)  //needs to check if account posting is real
        {
            
        }

        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages()
    {
        return null;
    }
    
}

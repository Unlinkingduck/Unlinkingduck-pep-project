package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

import java.util.List;
import java.util.ArrayList;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO; 

    public MessageService()
    {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public Message insertMessage(Message message)
    {
        //check stuff to make sure its legal
        String body = message.getMessage_text();
        if (body.length() > 0 && body.length() <= 255 && accountDAO.validateAccountById(message.posted_by))
        {
            return messageDAO.insertMessage(message);
        }

        return null;
    }

    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }
    
}

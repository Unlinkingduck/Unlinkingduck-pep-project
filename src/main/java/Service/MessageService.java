package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

import java.util.List;

import static org.mockito.ArgumentMatchers.nullable;

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
        //check to make sure its legal
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

    public Message getMessageById(int id)
    {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id)
    {
        Message deletedMessage = getMessageById(id);

        messageDAO.deleteMessage(id);

        return deletedMessage;
    }

    public Message patchMessageById(int id, String body)
    {
        Message retrievedMessage = getMessageById(id);
        if (body.length() > 0 && body.length() <= 255 && retrievedMessage != null)
        {
            return messageDAO.updateMessage(retrievedMessage, body);
        }

        return null;
    }

    public List<Message> getAllMessagesByUser(int userId)
    {
        return messageDAO.getAllMessagesByUser(userId);
    }
    
}

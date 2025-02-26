package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

import java.util.List;

import static org.mockito.ArgumentMatchers.nullable;

/***
 * The middle-manager for connecting controller to DAOs, handles Message operations
 */
public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO; 

    /***
     * Default constructor 
     */
    public MessageService()
    {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    /***
     * Handler for inserting new message into database
     * @param message The message object to be input, does not contain message_id
     * @return If successful, returns the input message including message_id, if unsuccessful, returns null
     */
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

    /**
     * Handler for retrieving all messages from database
     * @return Returns list containing all messages, if there are no messages List will be empty
     */
    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    /**
     * Handler for retrieving specific message by provided id
     * @param id The id of the desired message
     * @return The desired message if successful, null if unsuccessful
     */
    public Message getMessageById(int id)
    {
        return messageDAO.getMessageById(id);
    }

    /**
     * Handler for deleting specific message by provided id
     * @param id The id of the desired message
     * @return The desired message if successful, null if unsuccessful
     */
    public Message deleteMessageById(int id)
    {
        Message deletedMessage = getMessageById(id);

        messageDAO.deleteMessage(id);

        return deletedMessage;
    }

    /**
     * Handler for updating specific message with a new body
     * @param id The id of the desired message
     * @param body The new message body to alter the message with
     * @return The desired message if successful, null if unsuccessful
     */
    public Message patchMessageById(int id, String body)
    {
        Message retrievedMessage = getMessageById(id);
        if (body.length() > 0 && body.length() <= 255 && retrievedMessage != null)
        {
            return messageDAO.updateMessage(retrievedMessage, body);
        }

        return null;
    }

    /**
     * Handler for retrieving all messages posted by a specific user
     * @param userId The id of the desired user
     * @return A list of all messages posted by specified user, empty if no such messages exist
     */
    public List<Message> getAllMessagesByUser(int userId)
    {
        return messageDAO.getAllMessagesByUser(userId);
    }
    
}

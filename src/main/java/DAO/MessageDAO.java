package DAO;

import Model.Message;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Class handling database operations involving the Message table
 */
public class MessageDAO {

    /**
     * Inserts provided Message object into database
     * @param message The message to be input, lacks message_id
     * @return The message including message_id, null if unsuccessful
     */
    public Message insertMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch)" +
                            " VALUES (?, ?, ?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            prep.setInt(1, message.getPosted_by());
            prep.setString(2, message.getMessage_text());
            prep.setLong(3, message.getTime_posted_epoch());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next())
            {
                return new Message(rs.getInt(1), message.getPosted_by(), message.getMessage_text(), 
                    message.getTime_posted_epoch());
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves all existing messages from Message table
     * @return A list containing all messages, empty if there are none
     */
    public List<Message> getAllMessages()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try 
        {
            String sql = "SELECT * FROM Message";
            PreparedStatement prep = connection.prepareStatement(sql);

            ResultSet rs = prep.executeQuery();
            while (rs.next())
            {
                Message message = new Message(rs.getInt("message_id"),
                                        rs.getInt("posted_by"),
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /**
     * Retrieves specific message by provided id
     * @param id The message_id of the message in question
     * @return The desired message, or null if unsuccessful
     */
    public Message getMessageById(int id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            
            prep.setInt(1, id);

            ResultSet rs = prep.executeQuery();
            if (rs.next())
            {
                return new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Deletes desired message from Message table
     * @param id The id of the message to be deleted
     * @return The deleted message, or null if unsuccessful
     */
    public Message deleteMessage(int id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement prep = connection.prepareStatement(sql);

            prep.setInt(1, id);

            prep.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Updates desired message with new value for message_text
     * @param message The message to be updated
     * @param body The new message_text
     * @return The newly updated message object, or null if unsuccessful
     */
    public Message updateMessage(Message message, String body)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement prep = connection.prepareStatement(sql);

            prep.setString(1, body);
            prep.setInt(2, message.getMessage_id());

            int changedRows = prep.executeUpdate();

            if (changedRows > 0)    //ensures update took place
            {
                return new Message(message.getMessage_id(), message.getPosted_by(), body, message.getTime_posted_epoch());
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves all messages with the specified posted_by foreign key
     * @param userId The posted_by foreign key
     * @return List containing all messages retrieved, empty if none exist
     */
    public List<Message> getAllMessagesByUser(int userId)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try
        {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement prep = connection.prepareStatement(sql);

            prep.setInt(1, userId);

            ResultSet rs = prep.executeQuery();
            while (rs.next())
            {
                Message message = new Message(rs.getInt("message_id"),
                                        rs.getInt("posted_by"),
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

            return messages;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
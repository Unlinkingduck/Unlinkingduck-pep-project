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

public class MessageDAO {

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
package DAO;

import Model.Message;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    public Message insertMessage(Account user, String messageBody)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch)" +
                            " VALUES (?, ?, ?)";
            PreparedStatement prep = connection.prepareStatement(sql);

            prep.setInt(1, user.getAccount_id());
            prep.setString(2, messageBody);
            //prep.setLong(3, );   //Add date and time
        }
        catch (SQLException e)
        {

        }
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
        return null;
    }

    public Message deleteMessage(int id)
    {
        return null;
    }

    public Message updateMessage(String newMessage, int id)
    {
        return null;
    }

    public List<Message> getAllMessagesByUser(int id)
    {
        return null;
    }

}
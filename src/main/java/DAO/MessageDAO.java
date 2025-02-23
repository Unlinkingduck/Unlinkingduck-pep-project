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

    public Message insertMessage(String messageBody, Account user)
    {
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
        }
        catch(SQLException e)
        {

        }

        return null;
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
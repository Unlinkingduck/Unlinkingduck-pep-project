package DAO;

import Model.Account;
import Util.ConnectionUtil;

import static org.mockito.ArgumentMatchers.nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class AccountDAO {

    public Account insertAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            prep.setString(1, account.getUsername());
            prep.setString(2, account.getPassword());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next())
            {
                return new Account(rs.getInt(1), account.getUsername(), account.getPassword());
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account loginAccount(Account account) 
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement prep = connection.prepareStatement(sql);

            prep.setString(1, account.getUsername());
            prep.setString(2, account.getPassword());

            ResultSet rs = prep.executeQuery();
            if (rs.next())
            {
                return new Account(rs.getInt(1), account.getUsername(), account.getPassword());
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean validateAccountById(int id)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement prep = connection.prepareStatement(sql);

            prep.setInt(1, id);

            ResultSet rs = prep.executeQuery();

            if (rs.next()){     //account id in question found
                return true;
            }

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean validateAccountByUsername(String username)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement prep = connection.prepareStatement(sql);

            prep.setString(1, username);

            ResultSet rs = prep.executeQuery();

            if (rs.next()){     //account in question found
                return true;
            }

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }

}
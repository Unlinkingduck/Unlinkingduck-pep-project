package DAO;

import Model.Account;
import Util.ConnectionUtil;

import static org.mockito.ArgumentMatchers.nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class handling database operations involving the Account table
 */
public class AccountDAO {

    /**
     * Inserts new Account into table
     * @param account The account to be inserted
     * @return The inserted account or null if unsuccessful
     */
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

    /**
     * Checks for existence of provided Account object, therefore logging in
     * @param account Account containing a username and password
     * @return The existing account if successful or null if unsuccessful
     */
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

    /**
     * Helper method used to validate existence of specific account via account_id
     * @param id The id of the desired account
     * @return True if there is an account with specified id, false if there is not
     */
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

    /**
     * Helper method used to validate the existence of specific account via username
     * @param username The username of the desired account
     * @return True if an account exists with the specified username, false if no such account is found
     */
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
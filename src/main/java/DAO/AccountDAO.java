package DAO;

import Model.Account;
import Util.ConnectionUtil;

import static org.mockito.ArgumentMatchers.nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AccountDAO {

    public Account insertAccount(String username, String password)
    {
        return null;
    }

    public Account loginAccount(String username, String password) 
    {
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
            
            return false;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return false;
    }

}
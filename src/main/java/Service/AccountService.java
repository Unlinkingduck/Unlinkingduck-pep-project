package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;
import java.util.ArrayList;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public Account insertAccount(Account account)
    {
        if (account.getUsername().length() > 0 && account.getPassword().length() >= 4 &&
                !accountDAO.validateAccountByUsername(account.getUsername()))      //check that an account with this name doesn't exist
        {
            return accountDAO.insertAccount(account);
        }

        return null;
    }

    public Account loginAccount(Account account)
    {
        return accountDAO.loginAccount(account);
    }

}

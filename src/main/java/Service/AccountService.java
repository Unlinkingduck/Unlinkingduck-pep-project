package Service;

import DAO.AccountDAO;
import Model.Account;

/***
 * The middle-manager for connecting controller to DAOs, handles Account operations
 */
public class AccountService {
    public AccountDAO accountDAO;

    /**
     * Default constructor
     */
    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    /**
     * Handler for inserting new account into database
     * @param account The account to be input, lacks account_id
     * @return The account input, includes account_id, null if unsuccessful
     */
    public Account insertAccount(Account account)
    {
        if (account.getUsername().length() > 0 && account.getPassword().length() >= 4 &&
                !accountDAO.validateAccountByUsername(account.getUsername()))      //check that an account with this name doesn't exist
        {
            return accountDAO.insertAccount(account);
        }

        return null;
    }

    /**
     * Handler for loging into existing account in database
     * @param account The account in question, lacks account_id
     * @return The account including account_id, null if unsuccessful
     */
    public Account loginAccount(Account account)
    {
        return accountDAO.loginAccount(account);
    }

}

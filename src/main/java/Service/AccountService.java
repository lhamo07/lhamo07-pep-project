package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
  public AccountDAO accountDao;

  public AccountService() {
    accountDao = new AccountDAO();
  }

  public Account addAccount(Account account) {

    if (account.getUsername().isBlank()) {
      System.out.println("Error: Username cannot be blank");
      return null;
    }
    if (account.getPassword().length() <= 4) {
      System.out.println("Error: Password must be at least 4 characters long");
      return null;
    }

    return accountDao.insertUser(account);

  }

  public Account loginAccount(String username, String password) {
    return accountDao.getAccountByUsernameAndPassword(username, password);
  }

}
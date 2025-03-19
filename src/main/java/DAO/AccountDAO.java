package DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

  public Account insertUser(Account account) {

    Connection connection = ConnectionUtil.getConnection();
    try {

      String sql = "INSERT INTO account(username,password) VALUES (?,?)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, account.getUsername());
      preparedStatement.setString(2, account.getPassword());
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        int generated_account_id = (int) resultSet.getLong(1);
        return new Account(generated_account_id, account.getUsername(), account.getPassword());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;

  }
  public Account getAccountByUsernameAndPassword(String username, String password) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Account account = new Account(resultSet.getInt("account_id"),
            resultSet.getString("username"),
            resultSet.getString("password"));
        return account;
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

}
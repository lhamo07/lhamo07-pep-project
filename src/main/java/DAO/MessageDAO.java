package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message insertMessage(Message message) {
    Connection connection = ConnectionUtil.getConnection();
    try {

      String sql = "INSERT INTO message(posted_by,message_text,time_posted_epoch) VALUES (?,?,?)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, message.getPosted_by());
      preparedStatement.setString(2, message.getMessage_text());
      preparedStatement.setLong(3, message.getTime_posted_epoch());
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        int generated_message_id = (int) resultSet.getLong(1);
        System.out.println("generated_message_id: " + generated_message_id);
        return new Message(generated_message_id, message.posted_by, message.getMessage_text(),
            message.getTime_posted_epoch());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;

  }
  public List<Message> getAllMessages() {
    Connection connection = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>();
    try {
      String sql = "SELECT * FROM message";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Message message = new Message(resultSet.getInt("message_id"),
            resultSet.getInt("posted_by"),
            resultSet.getString("message_text"),
            resultSet.getLong("time_posted_epoch"));
        messages.add(message);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return messages;

  }
  public Message getAllMessagesById(int message_id) {
    Connection connection = ConnectionUtil.getConnection();

    try {
      String sql = "SELECT * FROM message WHERE message_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, message_id);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Message message = new Message(
            resultSet.getInt("message_id"),
            resultSet.getInt("posted_by"),
            resultSet.getString("message_text"),
            resultSet.getLong("time_posted_epoch"));
        return message;
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;

  }
  public Message deleteMessageByMessageId(int message_id) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      Message message = getAllMessagesById(message_id);
      if (message == null) {
        return null;
      }
      String sql = "DELETE FROM message WHERE message_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, message_id);
      preparedStatement.executeUpdate();
      return message;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
  public void updateMessageByMessageId(int id, Message message) {
    Connection connectionn = ConnectionUtil.getConnection();
    try {
      String sql = "UPDATE message SET message_text=? WHERE message_id=?";
      PreparedStatement preparedStatement = connectionn.prepareStatement(sql);
      preparedStatement.setString(1, message.getMessage_text());
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public List<Message> getAllMessagesByAccount(int account_id) {
    Connection connection = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>();
    try {
      String sql = "SELECT * FROM message WHERE posted_by = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, account_id);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Message message = new Message(resultSet.getInt("message_id"),
            resultSet.getInt("posted_by"),
            resultSet.getString("message_text"),
            resultSet.getLong("time_posted_epoch"));
        messages.add(message);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return messages;

  }
    
}

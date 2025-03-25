package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
    messageDAO = new MessageDAO();
  }
    public Message createMessage(Message message) {
        if (message.getMessage_text().isBlank()) {
          System.out.println("Error: Message cannot be blank");
          return null;
        }
        if (message.message_text.length() > 255) {
          System.out.println("Error: Message cannot be more than 255 characters");
          return null;
        }
        return messageDAO.insertMessage(message);
    
      }

  public List<Message> getAllMessages() {
    return messageDAO.getAllMessages();
  }
  public Message getAllMessagesById(int message_id) {
    Message message = messageDAO.getAllMessagesById(message_id);
    return message;

  }
  public Message deleteMessageByMessageId(int message_id) {
    Message message = messageDAO.deleteMessageByMessageId(message_id);
    return message;
  }
  public Message updateMessageByMessageId(int message_id, Message message) {
    if (message.getMessage_text().isBlank()) {
      System.out.println("Error: Message cannot be blank");
      return null;
    }
    if (message.message_text.length() > 255) {
      System.out.println("Error: Message cannot be more than 255 characters");
      return null;
    }
    if (messageDAO.getAllMessagesById(message_id) != null) {
      messageDAO.updateMessageByMessageId(message_id, message);
      return messageDAO.getAllMessagesById(message_id);
    }

    return null;

  }
  public List<Message> getAllMessagesByAccount(int account_id) {
    return messageDAO.getAllMessagesByAccount(account_id);

  }
    
}

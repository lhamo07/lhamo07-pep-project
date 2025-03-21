package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService=new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);

        Account registeredAccount = accountService.addAccount(account);
        if (registeredAccount == null) {
            context.status(400);

        } else {
            context.json(mapper.writeValueAsString(registeredAccount));
        }

    }
    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginRequest = mapper.readValue(context.body(), Account.class);
        Account loggedInAccount = accountService.loginAccount(loginRequest.getUsername(),
                loginRequest.getPassword());
        if (loggedInAccount == null) {
            context.status(401);
        } else {
            context.json(mapper.writeValueAsString(loggedInAccount));
        }

    }

    private void postMessageHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        System.out.println("createdMessage" + createdMessage);
        if (createdMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(createdMessage));
        }

    }

}
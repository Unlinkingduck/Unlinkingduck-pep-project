package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    MessageService messageService;
    AccountService accountService;

    public SocialMediaController()
    {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() 
    {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        
        return app;
    }

    /**
     * Handler to post a new Account
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.insertAccount(account);
        if (addedAccount != null)
        {
            ctx.status(200);
            ctx.json(addedAccount);
        }
        else
        {
            ctx.status(400);
        }
    }


    private void loginAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account retrieved = accountService.loginAccount(account);
        if (retrieved != null)
        {
            ctx.status(200);
            ctx.json(retrieved);
        }
        else
        {
            ctx.status(401);
        }
    }


    private void postMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.insertMessage(message);
        if (addedMessage != null)
        {
            ctx.status(200);
            ctx.json(addedMessage);
        }
        else
        {
            ctx.status(400);
        }
    }


    private void getMessageHandler(Context ctx)
    {
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200);
        ctx.json(messages);
    }


    private void getMessageByIdHandler(Context ctx)
    {
        String id = ctx.pathParam("message_id");
        Message message = messageService.getMessageById(Integer.parseInt(id));

        ctx.status(200);
        if (message != null)
        {
            ctx.json(message);
        }
    }


    private void deleteMessageByIdHandler(Context ctx)
    {
        String id = ctx.pathParam("message_id");
        Message message = messageService.deleteMessageById(Integer.parseInt(id));

        ctx.status(200);
        if (message != null)
        {
            ctx.json(message);
        }
    }


    private void patchMessageByIdHandler(Context ctx) throws JsonProcessingException
    {
        String id = ctx.pathParam("message_id");

        ObjectMapper mapper = new ObjectMapper();
        Message bodyHolder = mapper.readValue(ctx.body(), Message.class);

        Message updatedMessage = messageService.patchMessageById(Integer.parseInt(id), bodyHolder.getMessage_text());
        if (updatedMessage != null)
        {
            ctx.status(200);
            ctx.json(updatedMessage);
        }
        else
        {
            ctx.status(400);
        }
    }


    private void getAllMessagesByUserHandler(Context ctx)
    {
        String userId = ctx.pathParam("account_id");
        List<Message> messages = messageService.getAllMessagesByUser(Integer.parseInt(userId));

        ctx.status(200);
        ctx.json(messages);
    }


}
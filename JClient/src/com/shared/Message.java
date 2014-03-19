package com.shared;


import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{
    public int position;
    public int type;
    public String recipient;
    public String content;
    public String username;
    public ArrayList<String> usernames;
    public final static int MESSAGE = 0,MESSAGE_ALL = 1, LOGON = 2, LOGOUT = 3, SERVER_SHUTDOWN = 4;
    /**
     *
     * @param position The position of the client.
     * @param type The type of message.
     * @param recipient Who the message is going to in the server.
     * @param content String content of message.
     * @param username The username of the sender.
     */
    //Message.MESSAGE
    public Message(int type, String recipient, String content, String username)
    {
        this.type = type;
        this.recipient = recipient;
        this.content = content;
        this.username = username;
    }
    //Message.LOGON to client
    public Message(int type, ArrayList<String> usernames)
    {
        this.type = type;
        this.usernames = usernames;
    }
    //Message.LOGON from client
    public Message(int type, String username)
    {
        this.type = type;
        this.username = username;
    }
    //Message.ALL
    public Message(int type, String username, String content)
    {
        this.type = type;
        this.username = username;
        this.content = content;
    }
    //Message.SERVER_SHUTDOWN
    public Message(int type)
    {
        this.type = type;
    }
    public int getPosition()
    {
        return position;
    }
    public int getType()
    {
        return type;
    }
    public String getRecipient()
    {
        return recipient;
    }
    public String getContent()
    {
        return content;
    }
    @Override
    public String toString()
    {
        return (username) + ": " + content;
    }
}

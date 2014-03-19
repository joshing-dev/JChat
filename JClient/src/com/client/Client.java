package com.client;
import com.shared.Message;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client
{
    public ObjectOutputStream out;
    public ObjectInputStream in;
    private static Thread readThread;
    private static Thread outputThread;
    public GUI gui;
    public String ip;
    public int position;
    public boolean connected = false;
    
    public Socket clientSocket;
    
    public Client(GUI gui, String ip)
    {
        this.gui = gui;
        this.ip = ip;
        startClient();
        
    }
    
    public void startClient()
    {
        
        
        
        try {
            clientSocket = new Socket(ip, 44444);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            connected = true;
            out.writeObject(new Message(Message.LOGON, gui.usernameField.getText()));
            gui.username = gui.usernameField.getText();
            gui.usernameField.setEditable(false);
            readThread = new Thread(new Runnable()
            {
                String text;
                public void run()
                {
                    try 
                    {
                        while(connected)
                        {    
                            Message message = (Message) in.readObject();
                            System.out.println("Received message.");
                            switch(message.type)
                            {
                                case Message.MESSAGE_ALL:
                                    gui.jTextArea1.append(message.toString() + "\n");
                                    break;
                                case Message.MESSAGE:
                                    gui.jTextArea1.append(message.toString() + "\n");
                                    break;
                                case Message.LOGON:                                   
                                    //System.out.println(message.usernames.size());
                                    //gui.listModel.addElement(message.username);
                                    gui.listModel.clear();
                                    gui.listModel.ensureCapacity(message.usernames.size() + 1);
                                    
                                    for(int i = 0; i < message.usernames.size(); i++)
                                    {
                                        if(i == 0)
                                            gui.listModel.add(i, "All");
                                        gui.listModel.add(i + 1, message.usernames.get(i));
                                        System.out.println(message.usernames.get(i));
                                    }
                                    gui.userList.setSelectedIndex(0);
                                       
                                    break;
                                case Message.LOGOUT:
                                    /*for(int i = 0; i < message.usernames.size(); i++)
                                    {
                                        gui.listModel.set(i + 1, message.usernames.get(i));
                                    }*/
                                    gui.listModel.removeElement(message.username);
                                    break;
                                case Message.SERVER_SHUTDOWN:
                                    closeClient();
                                    break;
                                    
                            }
                        }
                    } 
                    catch(IOException ex)
                    {
                        System.out.println("Could not read message.");
                    } 
                    catch (ClassNotFoundException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    finally
                    {
                        try {
                            out.flush();
                            clientSocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            readThread.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public void closeClient() throws IOException
    {

        clientSocket.close();
        connected = false;
        gui.client = null;
        gui.connected = false;
        gui.listModel.clear();
    }
}
        
                    
                    
package com.server;

import com.shared.Message;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joseldridge15
 */
public class ServerThread extends Thread {
    public ThreadHandler server;
    public Thread serverThread;
    public Socket clientSocket;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public GUI gui;
    public boolean connected;
    public int position;
    public String username;
    public ServerThread(GUI gui, Socket clientSocket, ThreadHandler server)
    {
        this.gui = gui;
        this.clientSocket = clientSocket;
        this.server = server;
    }
    public void run()
    {

        try 
        {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            connected = true;
            while(connected) 
            {
                try
                {
                    Message message = (Message)in.readObject();
                    System.out.println("Received Message");
                    switch(message.type)
                    {
                        case Message.MESSAGE:
                            for(int i = 0; i < ThreadHandler.serverThreads.size(); i++)
                            {
                                if(ThreadHandler.serverThreads.get(i).username.equals(message.recipient))
                                        {
                                            ThreadHandler.serverThreads.get(i).out.writeObject(message);
                                        }    
                            }
                            
                            break;
                        case Message.MESSAGE_ALL:
                            for(int i = 0; i < ThreadHandler.serverThreads.size(); i++)
                            {
                                if(ThreadHandler.serverThreads.get(i) != null)
                                {
                                    
                                            {
                                                if(ThreadHandler.serverThreads.get(i) != this)
                                                ThreadHandler.serverThreads.get(i).out.writeObject(message);
                                            }
                                }
                            }
                            System.out.println("Sent message to users.");
                            break;
                        case Message.LOGON:
                            username = message.username;
                            ThreadHandler.usernames.add(username);
                            gui.serverList.addElement(username);
                            for(int x = 0; x < ThreadHandler.usernames.size(); x++)
                                System.out.println(ThreadHandler.usernames.get(x));          
                            for(int i = 0; i < ThreadHandler.serverThreads.size(); i++)
                            {
                                    ThreadHandler.serverThreads.get(i).out.reset();
                                    //ThreadHandler.serverThreads.get(i).out.flush();
                                    ThreadHandler.serverThreads.get(i).out.writeObject(new Message(Message.LOGON, ThreadHandler.usernames));
                                    
                            
                                
                            }
                            
                            break;
                        case Message.LOGOUT:
                            for(int i= 0; i < ThreadHandler.usernames.size(); i++)
                            {
                                if(ThreadHandler.usernames.get(i).equals(message.username))
                                    ThreadHandler.removeUser(i);
                            }
                            for(int i = 0; i < ThreadHandler.serverThreads.size(); i++)
                            {
                                if(ThreadHandler.serverThreads.indexOf(this) != i)
                                {
                                    ThreadHandler.serverThreads.get(i).out.reset();
                                    ThreadHandler.serverThreads.get(i).out.flush();
                                    ThreadHandler.serverThreads.get(i).out.writeObject(new Message(Message.LOGOUT, message.username));
                                    
                                }
                                    
                            
                                
                            }
                            connected = false;
                            ThreadHandler.removeThread(this);
                            gui.serverList.removeElement(message.username);
                            break;
                    }
                }//End of try inside While Loop
                catch(ClassNotFoundException ex)
                {
                    ex.printStackTrace();
                }   
                
            }//End of While Loop
        }//End of run try block 
        catch (IOException ex) 
        {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally 
        {
            close();
        }
        
    }
    public void close()
    {
        try
        {
            
            if(clientSocket != null)
            {
                out.flush();
                clientSocket.close();
                clientSocket = null;
            } 
        }
        catch(IOException ex)
        {
            System.out.println(ex);
        }
        
    }
}
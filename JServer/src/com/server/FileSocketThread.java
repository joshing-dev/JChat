package com.server;

import com.shared.FileMessage;
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
public class FileSocketThread extends Thread {
    public FileSocketHandler server;
    public Thread serverThread;
    public Socket clientSocket;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public GUI gui;
    public boolean connected;
    public int position;
    public String username;
    public FileSocketThread(GUI gui, Socket clientSocket, FileSocketHandler server)
    {
        this.gui = gui;
        this.clientSocket = clientSocket;
        this.server = server;
    }
    public void sendMessage(Message message) throws IOException
    {
        out.writeObject(message);
        out.reset();
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
                    switch(message.type)
                    {
                        case Message.FILE:
                            for(int i = 0; i < FileSocketHandler.serverThreads.size(); i++)
                                {
                                    if(FileSocketHandler.usernames.get(i).equals(message.recipient))
                                    {
                                        FileSocketHandler.serverThreads.get(i).sendMessage(message);
                                    }    
                                }  
                            break;
                        case Message.LOGON:
                            FileSocketHandler.usernames.add(message.username);
                            break;
                        case Message.LOGOUT:
                            for(int i= 0; i < FileSocketHandler.usernames.size(); i++)
                            {
                                if(FileSocketHandler.usernames.get(i).equals(message.username))
                                    FileSocketHandler.removeUser(i);
                            }
                            connected = false;
                            FileSocketHandler.removeThread(this);
                            break;
                    }                                     
                    
                }//End of try inside While Loop
                catch(ClassNotFoundException ex)
                {
                    System.err.println(ex);
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
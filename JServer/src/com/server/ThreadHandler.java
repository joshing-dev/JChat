package com.server;


import com.shared.Message;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;




public class ThreadHandler
{
    public ServerSocket serverSocket;
    public boolean running = true;
    public static ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();
    public static ArrayList<String> usernames = new ArrayList<String>();
    public static GUI gui;
    public Thread thread;
    public ThreadHandler server = this;
    public ThreadHandler(GUI theGui)
    {
        try {
            gui = theGui;
            serverSocket = new ServerSocket(44444);
            thread = new Thread(new Runnable()
            {
                public void run()
                {
                    while(running)
                    {
                        try 
                        {
                            ServerThread serverThread = new ServerThread(gui, serverSocket.accept(), server);
                            serverThreads.add(serverThread);
                            serverThread.start();
                        } catch (IOException ex) {
                            Logger.getLogger(ThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            });
           thread.start();
        
    }
        catch(IOException ex)
        {
            System.out.println("Could not start ServerThread.");
        }
    }
    public static synchronized void removeUser(int i)
    {
        ThreadHandler.usernames.remove(i);
    }
    public static synchronized void removeThread(ServerThread serverThread)
    {
        ThreadHandler.serverThreads.remove(serverThread);
    }
    
}
    
 
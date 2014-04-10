package com.server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;




public class FileSocketHandler
{
    public ServerSocket serverSocket;
    public boolean running = true;
    public static ArrayList<FileSocketThread> serverThreads = new ArrayList<FileSocketThread>();
    public static ArrayList<String> usernames = new ArrayList<String>();
    public static GUI gui;
    public Thread thread;
    public FileSocketHandler server = this;
    public FileSocketHandler(GUI theGui)
    {
        try {
            gui = theGui;
            serverSocket = new ServerSocket(44445);
            thread = new Thread(new Runnable()
            {
                public void run()
                {
                    while(running)
                    {
                        try 
                        {
                            FileSocketThread serverThread = new FileSocketThread(gui, serverSocket.accept(), server);
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
        FileSocketHandler.usernames.remove(i);
    }
    public static synchronized void removeThread(FileSocketThread serverThread)
    {
        FileSocketHandler.serverThreads.remove(serverThread);
    }
    
}
    
 
package com.client;

import com.shared.FileMessage;
import com.shared.Message;
import java.io.*;;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;

public class Upload implements Runnable{

    protected String ip;
    protected int port;
    protected Socket socket;
    public FileInputStream fileIn;
    public ObjectOutputStream out;
    public File file;
    public GUI gui;
    public String recipient;
    public int byteCounter;
    public String username;
    
    public Upload(String username, String recipient, File file, GUI gui){
            this.username = username;
            this.recipient = recipient;
            this.file = file;
            this.gui = gui;
    }
    
    @Override
    public void run() {
        try {       
            socket = new Socket(gui.client.ip, 44445);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Message(Message.LOGON, username));
            fileIn = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 100];
            int count;
            System.out.println(file.length());
            gui.progressBar.setMaximum((int)file.length());
            gui.progressBar.setString(null);
            byteCounter = 0;
            gui.messageTextArea.append("Sending File...\n");
            while((count = fileIn.read(buffer)) != -1){
                System.out.println("This is count: " + count);       
                    out.writeObject(new Message(Message.FILE, username, recipient, buffer, count));
                    out.reset();
                    byteCounter += count;
                    gui.progressBar.setValue(byteCounter);
                
            }
            out.flush();
            gui.progressBar.setString("Done");
            gui.messageTextArea.append("[File Upload Complete]\n");
            gui.fileTextField.setEditable(true);
            gui.chooseDownFileButton.setEnabled(true);
            gui.sendFileButton.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if(fileIn != null)
            { 
                try { 
                    fileIn.close();
                } catch (IOException ex) {
                    Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(socket != null)
            {
                try {
                    out.writeObject(new Message(Message.LOGOUT, username));
                    out.flush();
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
    }

}
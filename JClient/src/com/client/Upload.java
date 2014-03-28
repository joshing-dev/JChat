package com.client;

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

public class Upload implements Runnable{

    public String ip;
    public int port;
    public Socket socket;
    public FileInputStream fileIn;
    public ObjectOutputStream out;
    public File file;
    public GUI gui;
    
    public Upload(File filepath, GUI gui, ObjectOutputStream out){
        try {
            file = filepath;
            this.gui = gui;
            this.out = out;
            fileIn = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public void run() {
        try {       
            byte[] buffer = new byte[1024 * 1024];
            int count;
            
            while((count = fileIn.read(buffer)) != -1){
                out.writeObject(new Message(Message.FILE, gui.recipient, buffer, gui.username));
            }
            out.flush();            
            gui.messageTextArea.append("[Applcation > Me] : File upload complete\n");           
            if(fileIn != null){ fileIn.close(); }
            if(out != null){ out.close(); }
            if(socket != null){ socket.close(); }
        } catch (IOException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
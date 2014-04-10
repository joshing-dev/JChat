package com.client;

import com.shared.FileMessage;
import com.shared.Message;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Download implements Runnable{
    
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected File file;
    protected long fileLength;
    protected long byteCounter = 0;
    protected Socket socket;
    protected FileOutputStream fileOut;
    protected GUI gui;
    protected Client client;
    /**
     * The Download class handles a download from another client.
     * @param file The path of the file.
     * @param fileLength The length of the file.
     * @param gui Reference to the GUI.
     * @param client Reference to the Client.
     */
    public Download(File file, long fileLength, GUI gui, Client client){
        this.file = file;
        this.fileLength = fileLength;
        this.gui = gui;  
        this.client = client;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(client.ip, 44445);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Message(Message.LOGON, gui.username));
            System.out.println(fileLength);
            gui.progressBar.setValue(0);
            gui.progressBar.setString(null);
            fileOut = new FileOutputStream(file);
            gui.messageTextArea.append("Downloading file.\n");
            client.downloading = true;
            while(byteCounter != fileLength){
                Message message = (Message) in.readObject();
                fileOut.write(message.fileData, 0, message.bytesSent);
                byteCounter += message.bytesSent;
                gui.progressBar.setValue((int)byteCounter);
            }
            
            fileOut.flush();
            gui.progressBar.setString("Done");
            gui.messageTextArea.append("Download complete.\n");
            client.downloading = false;
            
            
        } 
        catch (IOException ex) {        
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }        
        finally
        {
            if(fileOut != null){try {
                    fileOut.close();
                } catch (IOException ex) {
                    Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
                }
 }
            if(out != null){ 
                try {
                    out.writeObject(new Message(Message.LOGOUT, gui.username));
                    out.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
 }
            if(socket != null){ 
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }



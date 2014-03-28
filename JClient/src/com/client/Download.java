package com.client;

import java.io.*;
import java.net.*;

public class Download implements Runnable{
    
    protected ObjectInputStream in;
    protected File file;
    public FileOutputStream fileOut;
    protected FileInputStream fileIn;
    public GUI gui;
    
    public Download(File file, GUI gui, ObjectInputStream in){
        this.file = file;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            file.createNewFile();
            fileOut = new FileOutputStream(file);
            
            byte[] buffer = new byte[1024 * 1024];
            
            int count;
            while((count = in.read(buffer)) >= 0){
                fileOut.write(buffer, 0, count);
            }
            
            fileOut.flush();
            
            gui.messageTextArea.append("Download complete\n");
            
            if(fileOut != null){ fileOut.close(); }
            if(in != null){ in.close(); }
        } 
        catch (Exception ex) {
            System.out.println("Exception [Download : run(...)]");
        }
    }
}


package com.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;


public class MP3 {
    public String filename;
    public File file;
    private Player player; 
    private static boolean playing = false;
    private Thread musicThread;

    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
        
            this.filename = filename;
    } 
    public MP3()
    {
        
    }
    public void close() { if (player != null) player.close(); }

    // play the MP3 file to the sound card
    public void play() {
        try {
            //URL url = MP3.class.getResource(filename);
            
            //File file = new File(url.toURI());
            FileInputStream fis = new FileInputStream(file);
            
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            new Thread(){
            @Override
            public void run() {
                
                    
                
                try 
                { 
                    player.play(); 
                }
                catch (Exception e) { System.out.println("Issue with thread. " + e); }
                }
                
        }.start();
            
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println("Issue with play " + e);
        }

        // run in new thread to play in background
        
            
         




    }


    // test client
    

}
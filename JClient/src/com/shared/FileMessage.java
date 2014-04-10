/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shared;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author joseldridge15
 */
public class FileMessage implements Serializable {
    public long byteLength;
    public byte[] byteData;
    public String fileName;
    public String username;
    public String recipient;
    public int byteSent;
    /**
     * This is the message that contains the file data.
     * @param username The username of the sender.
     * @param recipient The username of the receiver. 
     * @param byteData The piece of data from the file.
     */
    public FileMessage(String username, String recipient, byte[] byteData, int byteSent)
    {
        this.username = username;
        this.recipient = recipient;
        this.byteData = byteData;
        this.byteSent = byteSent;
    }
}

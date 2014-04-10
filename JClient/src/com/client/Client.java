package com.client;
import com.shared.Message;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Client
{
    public volatile ObjectOutputStream out;
    public ObjectInputStream in;
    private static Thread readThread;
    private static Thread outputThread;
    public GUI gui;
    public String ip;
    public int position;
    public boolean connected = false;
    public InetSocketAddress socketAddress;
    public boolean downloading = false;
    public File fileToSaveTo;
    public ArrayList<byte[]> byteList = new ArrayList<byte[]>();
    public long byteCounter = 0;
    public long byteLength = 0;
    public Socket clientSocket;
    
    public Client(GUI gui, String ip)
    {
        this.gui = gui;
        this.ip = ip;
        
    }
    
    public void startClient()
    {
        
        
        
        try {
            clientSocket = new Socket();
            socketAddress = new InetSocketAddress(ip, 44444);
            if(socketAddress.isUnresolved())
            {
                throw new IllegalArgumentException();
            }
            clientSocket.connect(socketAddress, 2500);
            gui.messageTextArea.append("Connected to server." + "\n");
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            connected = true;
            out.writeObject(new Message(Message.LOGON, gui.usernameField.getText()));
            gui.username = gui.usernameField.getText();
            gui.usernameField.setEditable(false);
            readThread = new Thread(new Runnable()
            {
                String text;
                public void run()
                {
                    try 
                    {
                        while(connected)
                        {    
                            Message message = (Message) in.readObject();
                            System.out.println("Received message.");
                            switch(message.type)
                            {
                                case Message.MESSAGE_ALL:
                                    gui.messageTextArea.append(message.toString() + "\n");
                                    break;
                                case Message.MESSAGE:
                                    gui.messageTextArea.append(message.toString() + "\n");
                                    break;
                                case Message.LOGON:                                   
                                    //System.out.println(message.usernames.size());
                                    //gui.listModel.addElement(message.username);
                                    gui.listModel.clear();
                                    gui.listModel.ensureCapacity(message.usernames.size() + 1);
                                    
                                    for(int i = 0; i < message.usernames.size(); i++)
                                    {
                                        if(i == 0)
                                            gui.listModel.add(i, "All");
                                        gui.listModel.add(i + 1, message.usernames.get(i));
                                        System.out.println(message.usernames.get(i));
                                    }
                                    gui.userList.setSelectedIndex(0);
                                       
                                    break;
                                case Message.LOGOUT:
                                    /*for(int i = 0; i < message.usernames.size(); i++)
                                    {
                                        gui.listModel.set(i + 1, message.usernames.get(i));
                                    }*/
                                    gui.listModel.removeElement(message.username);
                                    break;
                                case Message.SERVER_SHUTDOWN:
                                    closeClient();
                                    break;
                                case Message.UPLOAD_REQ:
                                    System.out.println("Received upload request");
                                    if(downloading)
                                    {
                                       out.writeObject(new Message(Message.UPLOAD_DENY, message.username, "", gui.username));
                                       break;
                                    }
                                    int x = JOptionPane.showConfirmDialog(gui, message.username + " would like to send you " + message.content +" (" + (message.getFileLength()/1024/1024) + "MB)" , "Download Request", JOptionPane.YES_NO_OPTION);
                                    if(x == JOptionPane.YES_OPTION)
                                    {
                                        JFileChooser fileChooser = new JFileChooser();
                                        fileChooser.setSelectedFile(new File(message.getContent()));
                                        int i = fileChooser.showSaveDialog(gui);                                                                        if(i == JFileChooser.APPROVE_OPTION)
                                        {
                                            fileToSaveTo = fileChooser.getSelectedFile();
                                            System.out.println(fileToSaveTo.toString());
                                            System.out.println(message.dataLength);
                                            byteLength = message.getFileLength();
                                            gui.progressBar.setMaximum((int)byteLength);
                                            System.out.println("Attempting to send out upload accept");
                                            Download download = new Download(fileToSaveTo, byteLength, gui, gui.client);
                                            Thread downloadThread = new Thread(download, "Download Thread");
                                            downloadThread.start();
                                            out.reset();
                                            out.writeObject(new Message(Message.UPLOAD_ACCEPT, message.username, "", gui.username));
                                            
                                            System.out.println("Sent out upload accept");
                                        }
                                        else if(i == JFileChooser.CANCEL_OPTION)
                                        {
                                            out.reset();
                                            out.writeObject(new Message(Message.UPLOAD_DENY, message.username, "", gui.username));
                                        System.out.println("No sent to user.");
                                        }
                                        break;
                                    }
                                    else if(x == JOptionPane.NO_OPTION)
                                    {                           
                                        out.reset();
                                        out.writeObject(new Message(Message.UPLOAD_DENY, message.username, "", gui.username));
                                        System.out.println("No sent to user.");
                                    }
                                    break;
                                case Message.UPLOAD_ACCEPT:
                                    System.out.println("Received upload accept");
                                    gui.fileTextField.setEditable(false);
                                    gui.chooseDownFileButton.setEnabled(false);
                                    gui.sendFileButton.setEnabled(false);
                                    File file = new File(gui.fileTextField.getText());
                                    Upload upload = new Upload(gui.username, message.username, file, gui);
                                    Thread thread = new Thread(upload, "Upload Thread");
                                    thread.start();
                                    System.out.println("Upload thread started.");
                                    break;
                                case Message.UPLOAD_DENY:
                                    System.out.println("Received upload deny.");
                                    gui.messageTextArea.append(message.username + " denied file transfer.\n");
                                    break;
                                
                                    
                            }
                        }
                    } 
                    catch(IOException ex)
                    {
                        System.out.println("Could not read message.");
                    } 
                    catch (ClassNotFoundException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    finally
                    {
                        try {
                            out.flush();
                            clientSocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }, "Client Read Thread");
            readThread.start();
        } 
        catch(SocketTimeoutException ex)
        {
            System.out.println(ex);
            gui.messageTextArea.append("Connection timed out." + "\n");
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println(ex);
            gui.messageTextArea.append("Given address is not reachable." + "\n" );                   
        }
        catch (IOException ex) 
        {
            System.out.println(ex);
            gui.messageTextArea.append("Could not connect to server." + "\n");
        } 
        
        
    }
    public void closeClient() throws IOException
    {

        clientSocket.close();
        connected = false;
        gui.listModel.clear();
    }
}
        
                    
                    
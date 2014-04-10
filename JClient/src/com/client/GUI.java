/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client;

import static com.client.GUI.createImage;
import com.shared.Message;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
public class GUI extends javax.swing.JFrame {

    //The Client instance
    Client client;
    //File chooser for our MP3 file.
    JFileChooser fileChooser;
    //MP3 player instance.
    MP3 mp3;
    //Clients username
    String username;
    //Determines if MP3 is playing music.
    private boolean musicPlaying = false;
    //Determines if the client shlic boolean connected = false;ould receive notifications of messsages.
    private boolean getNotifications = false;
    //Determines if the JFrame is floating.
    private boolean isFloating = false;
    //Receving user's name
    public String recipient;
    //Length of the file as a long value
    public long fileLength;
    //The IP Address to connect to
    String ip = "";
    //Path of the file to send to another user.
    String fileToSend = "";
    //The property file
    File propFile;
    public static final TrayIcon trayIcon = new TrayIcon(createImage("images/alien.gif", "Tray Icon"),"JChat");
    public static final SystemTray tray = SystemTray.getSystemTray();
    Properties prop;
    DefaultListModel listModel;
    public GUI() {
        listModel = new DefaultListModel();
        mp3 = new MP3();
        initComponents();
        extraWindowSetup();
        setupPropertiesFile();
        ShutDownHook shutDownHook = new ShutDownHook();
        Runtime.getRuntime().addShutdownHook(shutDownHook);           
        usernameField.setText(prop.getProperty("USERNAME"));
        hostField.setText(prop.getProperty("IP"));
        client = new Client(this, ip);
        this.addWindowListener(new WindowListener() {

            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosing(WindowEvent e) 
            {
                try 
                {
                    if(client.connected)
                    {
                        client.out.flush();
                        client.out.writeObject(new Message(Message.LOGOUT, username));
                        client.connected = false;
                    }  
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {
                getNotifications = false;}
            @Override public void windowDeactivated(WindowEvent e) {
                getNotifications = true;}
        });
        messageTextArea.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e){
                
            }
            @Override
            public void removeUpdate(DocumentEvent e){
                
            }
            @Override
            public void insertUpdate(DocumentEvent e){
               if(getNotifications)trayIcon.displayMessage("Message Received",null, TrayIcon.MessageType.INFO); 
            }
        });
    }
    protected static Image createImage(String path, String description) {
        URL imageURL = GUI.class.getClassLoader().getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
    private void extraWindowSetup()
    {
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        DefaultCaret caret = (DefaultCaret)messageTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        SmartScroller smartScroller = new SmartScroller(textAreaScrollPane);
        for(int i = 0; i < listModel.capacity(); i++)
                listModel.add(i, "");
        listModel.set(0, "All");
        userList.setSelectedIndex(0);
        try
        {
            tray.add(trayIcon);
        }
        catch(AWTException e)
        {
            System.out.println("System Tray was not present.");
        }
        this.setIconImage(createImage("images/alien.gif", "Application Icon"));
    }
    private void setupPropertiesFile()
    {
        propFile = new File("clientproperties.properties");
        prop = new Properties();
        FileInputStream fileInput;
        try {
            propFile.createNewFile();
            fileInput = new FileInputStream(propFile);
            prop.load(fileInput);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private class ShutDownHook extends Thread
    {
        @Override
        public void run()
        {
            if(client.connected == true)
            {
                try 
                {
                client.out.writeObject(new Message(Message.LOGOUT, username));
                } catch (IOException ex) 
                {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }          
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        messagePanel = new javax.swing.JPanel();
        textAreaScrollPane = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        messageField = new javax.swing.JTextField();
        userListPane = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        sendMessageButton = new javax.swing.JButton();
        inputPanel = new javax.swing.JPanel();
        hostLabel = new javax.swing.JLabel();
        hostField = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        fileTextField = new javax.swing.JTextField();
        discButton = new javax.swing.JButton();
        connectButton = new javax.swing.JButton();
        chooseDownFileButton = new javax.swing.JButton();
        sendFileButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        mp3Container = new javax.swing.JPanel();
        mp3Label = new javax.swing.JLabel();
        mp3Panel = new javax.swing.JPanel();
        stopMusicButton = new javax.swing.JButton();
        startMusicButton = new javax.swing.JButton();
        chooseMusicButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JClient");
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(838, 324));
        setPreferredSize(new java.awt.Dimension(838, 324));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        messagePanel.setBackground(new java.awt.Color(0, 0, 0));
        messagePanel.setLayout(new java.awt.GridBagLayout());

        messageTextArea.setEditable(false);
        messageTextArea.setBackground(new java.awt.Color(51, 51, 51));
        messageTextArea.setColumns(20);
        messageTextArea.setForeground(new java.awt.Color(102, 255, 0));
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(5);
        messageTextArea.setWrapStyleWord(true);
        textAreaScrollPane.setViewportView(messageTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        messagePanel.add(textAreaScrollPane, gridBagConstraints);

        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        messagePanel.add(messageField, gridBagConstraints);

        userListPane.setPreferredSize(new java.awt.Dimension(57, 130));

        userList.setModel(listModel);
        userListPane.setViewportView(userList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        messagePanel.add(userListPane, gridBagConstraints);

        sendMessageButton.setText("Send");
        sendMessageButton.setPreferredSize(new java.awt.Dimension(57, 28));
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        messagePanel.add(sendMessageButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(messagePanel, gridBagConstraints);

        inputPanel.setBackground(new java.awt.Color(0, 0, 0));
        inputPanel.setLayout(new java.awt.GridBagLayout());

        hostLabel.setForeground(new java.awt.Color(255, 255, 0));
        hostLabel.setText("Host:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        inputPanel.add(hostLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 350;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(hostField, gridBagConstraints);

        usernameLabel.setForeground(new java.awt.Color(255, 255, 0));
        usernameLabel.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        inputPanel.add(usernameLabel, gridBagConstraints);

        fileLabel.setForeground(new java.awt.Color(255, 255, 0));
        fileLabel.setText("File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        inputPanel.add(fileLabel, gridBagConstraints);

        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 350;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(usernameField, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 350;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(fileTextField, gridBagConstraints);

        discButton.setText("Disconnect");
        discButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(discButton, gridBagConstraints);

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(connectButton, gridBagConstraints);

        chooseDownFileButton.setText("...");
        chooseDownFileButton.setPreferredSize(new java.awt.Dimension(55, 20));
        chooseDownFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDownFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(chooseDownFileButton, gridBagConstraints);

        sendFileButton.setText("Send File");
        sendFileButton.setPreferredSize(new java.awt.Dimension(75, 20));
        sendFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        inputPanel.add(sendFileButton, gridBagConstraints);

        progressBar.setString(null);
        progressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(progressBar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(inputPanel, gridBagConstraints);

        mp3Container.setLayout(new java.awt.GridBagLayout());

        mp3Label.setBackground(new java.awt.Color(255, 153, 0));
        mp3Label.setFont(new java.awt.Font("Segoe Script", 0, 18)); // NOI18N
        mp3Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mp3Label.setText("The MP3 Player");
        mp3Label.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 155, 60), 1, true));
        mp3Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mp3Label.setOpaque(true);
        mp3Label.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        mp3Container.add(mp3Label, gridBagConstraints);

        mp3Panel.setBackground(new java.awt.Color(255, 0, 0));
        mp3Panel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        stopMusicButton.setText("Stop");
        stopMusicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopMusicButtonActionPerformed(evt);
            }
        });

        startMusicButton.setText("Play");
        startMusicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startMusicButtonActionPerformed(evt);
            }
        });

        chooseMusicButton.setText("Choose File");
        chooseMusicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseMusicButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mp3PanelLayout = new javax.swing.GroupLayout(mp3Panel);
        mp3Panel.setLayout(mp3PanelLayout);
        mp3PanelLayout.setHorizontalGroup(
            mp3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mp3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mp3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chooseMusicButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mp3PanelLayout.createSequentialGroup()
                        .addComponent(startMusicButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopMusicButton)))
                .addContainerGap())
        );

        mp3PanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {startMusicButton, stopMusicButton});

        mp3PanelLayout.setVerticalGroup(
            mp3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mp3PanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(mp3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startMusicButton)
                    .addComponent(stopMusicButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chooseMusicButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        mp3Container.add(mp3Panel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        getContentPane().add(mp3Container, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopMusicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopMusicButtonActionPerformed
        if(musicPlaying == true)
        {
            mp3.close();
            musicPlaying = false;
        }
    }//GEN-LAST:event_stopMusicButtonActionPerformed

    private void startMusicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startMusicButtonActionPerformed
        if(musicPlaying == false)
        {
            mp3.play();
            musicPlaying = true;
        }
    }//GEN-LAST:event_startMusicButtonActionPerformed

    private void chooseMusicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseMusicButtonActionPerformed
        fileChooser = new JFileChooser();
        int x = fileChooser.showDialog(this, "Select");
        if(x == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            mp3.file = file.getAbsoluteFile();
        }
    }//GEN-LAST:event_chooseMusicButtonActionPerformed

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageFieldActionPerformed
        sendMessageButton.doClick();
    }//GEN-LAST:event_messageFieldActionPerformed

    private void discButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discButtonActionPerformed
        if(client.connected)
        {
            try {
            client.out.writeObject(new Message(Message.LOGOUT, username));
            client.closeClient();
            usernameField.setEditable(true);
            } catch (IOException ex) {
            System.out.println("Issue closing client socket.");
            }
        }
    }//GEN-LAST:event_discButtonActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        if(!client.connected)
        {
            if(usernameField.getText().isEmpty())
            {
                messageTextArea.append("Please type in a username first.\n");
                return;
            }
            client.ip = hostField.getText();
            messageTextArea.append("Connecting to server..." + "\n");
            client.startClient();
            if(client.clientSocket.isConnected())
            {
                client.connected = true;
                prop.setProperty("USERNAME", username);
                prop.setProperty("IP", client.ip);
                try {
                    FileOutputStream fos = new FileOutputStream(propFile);
                    prop.store(fos, "Client Properties");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        connectButton.doClick();
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButtonActionPerformed

       if(messageField.getText().isEmpty())
       {
           return;
       }       
       if(messageField.getText().equals("/float") && isFloating)
       {
           messageTextArea.append("Float turned off.\n");
           messageField.setText("");
           this.setAlwaysOnTop(false);
           isFloating = false;
       }
       else if(messageField.getText().equals("/float") && (isFloating == false))
       {
           messageTextArea.append("Float turned on.\n");
           messageField.setText("");
           this.setAlwaysOnTop(true);
           isFloating = true;
       }
       else
       {
           try 
           {
            if(client.out != null)
            {
                if(userList.getSelectedIndex() == 0)
                {
                    synchronized(client.out)
                    {
                        client.out.writeObject(new Message(Message.MESSAGE_ALL , username, messageField.getText()));
                        messageTextArea.append("[" + username + "] " + messageField.getText() + "\n");
                    }
                    
                }
                else if(listModel.get(userList.getSelectedIndex()).equals(username))
                {
                 //Do nothing          
                }
                else 
                {
                    recipient = (String) listModel.getElementAt(userList.getSelectedIndex());
                    synchronized(client.out)
                    {
                       client.out.writeObject(new Message(Message.MESSAGE, recipient, messageField.getText(), username)); 
                    }
                    
                    messageTextArea.append("[" + username + "] " + messageField.getText() + "\n");
                }
                
                System.out.println("Sent message");
                messageField.setText("");
            }
                               
          }  
           catch (IOException ex) 
           {
               System.out.println("Could not send Client Message.");
           }
           
       }
    }//GEN-LAST:event_sendMessageButtonActionPerformed

    private void chooseDownFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDownFileButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int x = fileChooser.showDialog(this, "Choose");
        if(x == JFileChooser.APPROVE_OPTION)
        {
            fileTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            fileToSend = fileChooser.getSelectedFile().getName();
            fileLength = (fileChooser.getSelectedFile().length());
        }
    }//GEN-LAST:event_chooseDownFileButtonActionPerformed

    private void sendFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileButtonActionPerformed
        if(userList.getSelectedIndex() == 0)
        {
            messageTextArea.append("Sending files to all users not allowed.\n");
            return;
        }
        else if(listModel.getElementAt(userList.getSelectedIndex()).equals(username))
        {
            messageTextArea.append("You may not send a file to yourself.\n");
            return;
        }
        recipient = (String) listModel.getElementAt(userList.getSelectedIndex());
        try {
            client.out.writeObject(new Message(Message.UPLOAD_REQ, recipient, fileToSend, fileLength, username));
            messageTextArea.append("Sent out upload request.\n");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendFileButtonActionPerformed

    
    public static void main(String args[]) {
        //Uses system look and feel.
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton chooseDownFileButton;
    private javax.swing.JButton chooseMusicButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton discButton;
    private javax.swing.JLabel fileLabel;
    public javax.swing.JTextField fileTextField;
    private javax.swing.JTextField hostField;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JTextField messageField;
    private javax.swing.JPanel messagePanel;
    public javax.swing.JTextArea messageTextArea;
    private javax.swing.JPanel mp3Container;
    private javax.swing.JLabel mp3Label;
    private javax.swing.JPanel mp3Panel;
    public javax.swing.JProgressBar progressBar;
    public javax.swing.JButton sendFileButton;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JButton startMusicButton;
    private javax.swing.JButton stopMusicButton;
    private javax.swing.JScrollPane textAreaScrollPane;
    public javax.swing.JList userList;
    private javax.swing.JScrollPane userListPane;
    public javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}

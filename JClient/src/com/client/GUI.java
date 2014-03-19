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
import java.awt.event.ItemEvent;
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
import javax.swing.text.DefaultCaret;
public class GUI extends javax.swing.JFrame {

    
    Client client;
    JFileChooser fileChooser;
    MP3 mp3 = new MP3();
    String username;
    private boolean musicPlaying = false;
    public boolean connected = false;
    private boolean getNotifications = false;
    private boolean isFloating = false;
    String ip;
    File propFile = new File("clientproperties.properties");
    final TrayIcon trayIcon = new TrayIcon(createImage("images/bulb.gif", "Tray Icon"),"JChat");
    final SystemTray tray = SystemTray.getSystemTray();
    Properties prop = new Properties();
    DefaultListModel listModel = new DefaultListModel();
    public GUI() {
        initComponents();
        ShutDownHook shutDownHook = new ShutDownHook();
        Runtime.getRuntime().addShutdownHook(shutDownHook);
        this.getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        
        System.out.println(listModel.capacity());
        for(int i = 0; i < listModel.capacity(); i++)
                listModel.add(i, "");
        listModel.set(0, "All");
        userList.setSelectedIndex(0);
        DefaultCaret caret = (DefaultCaret)jTextArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
        
        usernameField.setText(prop.getProperty("USERNAME"));
        hostField.setText(prop.getProperty("IP"));
        this.addWindowListener(new WindowListener() {

            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosing(WindowEvent e) 
            {
                try 
                {
                    if(connected)
                    {
                        client.out.flush();
                        client.out.writeObject(new Message(Message.LOGOUT, username));
                        connected = false;
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
        try
        {
            tray.add(trayIcon);
        }
        catch(AWTException e)
        {
            System.out.println("System Tray was not present.");
        }
        jTextArea1.getDocument().addDocumentListener(new DocumentListener(){
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
    private class ShutDownHook extends Thread
    {
        @Override
        public void run()
        {
            if(connected == true)
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        hostField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        discButton = new javax.swing.JButton();
        connectButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        stopMusicButton = new javax.swing.JButton();
        startMusicButton = new javax.swing.JButton();
        chooseMusicButton = new javax.swing.JButton();
        usernameField = new javax.swing.JTextField();
        messageField = new javax.swing.JTextField();
        sendMessageButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JClient");
        setBackground(new java.awt.Color(0, 0, 0));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Host:");

        discButton.setText("Disconnect");
        discButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discButtonActionPerformed(evt);
            }
        });

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setText("Username:");

        userList.setModel(listModel);
        jScrollPane2.setViewportView(userList);

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chooseMusicButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(startMusicButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopMusicButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startMusicButton)
                    .addComponent(stopMusicButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chooseMusicButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        sendMessageButton.setText("Send");
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 153, 0));
        jLabel3.setFont(new java.awt.Font("Segoe Script", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("The MP3 Player");
        jLabel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 155, 60), 1, true));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setOpaque(true);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hostField))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(discButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameField)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(messageField)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendMessageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {connectButton, discButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendMessageButton)
                    .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(discButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {connectButton, discButton});

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
        int x = fileChooser.showDialog(this, "Test");
        if(x == fileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            mp3.file = file.getAbsoluteFile();
        }
    }//GEN-LAST:event_chooseMusicButtonActionPerformed

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageFieldActionPerformed
        sendMessageButton.doClick();
    }//GEN-LAST:event_messageFieldActionPerformed

    private void discButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discButtonActionPerformed
        try {
            client.out.writeObject(new Message(Message.LOGOUT, username));
            client.connected = false;
            client = null;
            connected = false;
            listModel.clear();
            usernameField.setEditable(true);
        } catch (IOException ex) {
            System.out.println("Issue closing client socket.");
        }
    }//GEN-LAST:event_discButtonActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        if(connected == false)
        {
            ip = hostField.getText();
            System.out.println(ip);
            client = new Client(this, ip);
            connected = true;
            prop.setProperty("USERNAME", username);
            prop.setProperty("IP", ip);
            try {
                FileOutputStream fos = new FileOutputStream(propFile);
                prop.store(fos, "Client Properties");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        connectButton.doClick();
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButtonActionPerformed
       if(messageField.getText().equals("/float") && isFloating)
       {
           jTextArea1.append("Float turned off.\n");
           messageField.setText("");
           this.setAlwaysOnTop(false);
           isFloating = false;
       }
       else if(messageField.getText().equals("/float") && (isFloating == false))
       {
           jTextArea1.append("Float turned on.\n");
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
                    client.out.writeObject(new Message(Message.MESSAGE_ALL , username, messageField.getText()));
                    jTextArea1.append(username + ": " + messageField.getText() + "\n");
                }
                else
                {
                    client.out.writeObject(new Message(Message.MESSAGE, (String) listModel.getElementAt(userList.getSelectedIndex()) , messageField.getText(), username));
                    jTextArea1.append(username + ": " + messageField.getText() + "\n");
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
    private javax.swing.JButton chooseMusicButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton discButton;
    private javax.swing.JTextField hostField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField messageField;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JButton startMusicButton;
    private javax.swing.JButton stopMusicButton;
    public javax.swing.JList userList;
    public javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}

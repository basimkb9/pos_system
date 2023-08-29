package org.possystem.ui;

import org.possystem.domain.User;
import org.possystem.service.AuthenticationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginUI{

    private final AuthenticationService authenticationService = new AuthenticationService();

    public LoginUI(){

        //region MainFrame
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("POS System | Login");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 0));
        //endregion

        //region row 1
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        JTextField usernameTF = new JTextField(15);
        row1.add(usernameLabel);
        row1.add(usernameTF);
        //endregion

        //region row 2
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        JTextField passwordTF = new JPasswordField(15);
        row2.add(passwordLabel);
        row2.add(passwordTF);
        //endregion row2

        //region row3
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100,30));
        row3.add(loginButton,BorderLayout.CENTER);
        //endregion row3

        //region Action/Key Listeners
        usernameTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    User user = authenticationService.checkLogin(usernameTF.getText(),passwordTF.getText());
                    if(user!=null){
                        mainFrame.dispose();
                        new NavUI(user.getRole());
                    }
                    else{
                        JOptionPane.showMessageDialog(mainFrame,"Incorrect Username/Password");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        passwordTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    User user = authenticationService.checkLogin(usernameTF.getText(),passwordTF.getText());
                    if(user!=null){
                        mainFrame.dispose();
                        new NavUI(user.getRole());
                    }
                    else{
                        JOptionPane.showMessageDialog(mainFrame,"Incorrect Username/Password");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        loginButton.addActionListener( (event)-> {
            User user = authenticationService.checkLogin(usernameTF.getText(),passwordTF.getText());
            if(user!=null){
                mainFrame.dispose();
                new NavUI(user.getRole());
            }
            else{
                JOptionPane.showMessageDialog(mainFrame,"Incorrect Username/Password");
            }
        });
        //endregion

        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);

        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(400,300);
        mainFrame.setVisible(true);

//        JFrame frame = new JFrame("Point of Sale System");
//
//        JLabel userTextLabel = new JLabel("Username:");
//        userTextLabel.setBounds(150,100,250,30);
//        userTextLabel.setFont(userTextLabel.getFont().deriveFont(12f));
//
//        JTextField usernametf = new JTextField();
//        usernametf.setBounds(150,130,200,30);
//
//        JLabel passwordTextLabel = new JLabel("Password");
//        passwordTextLabel.setBounds(150,170,250,30);
//        passwordTextLabel.setFont(passwordTextLabel.getFont().deriveFont(12f));
//
//        JTextField passwordtf = new JPasswordField();
//        passwordtf.setBounds(150,200,200,30);
//
//        JButton loginButton = new JButton("Login");
//        loginButton.setBounds(225,240,70,20);
//
//        loginButton.addActionListener( (event)-> {
//            if(authenticationService.checkLogin(usernametf.getText(),passwordtf.getText())){
//                dispose();
//                new HomeUI();
//            }
//            else{
//                JOptionPane.showMessageDialog(frame,"Incorrect Username/Password");
////                label.setText("Username/Password doesn't match");
////                label.setForeground(Color.red);
//            }
//        });
//
//        add(userTextLabel);
//        add(passwordTextLabel);
//        add(usernametf);
//        add(passwordtf);
//        add(loginButton);
//
//        setTitle("POS System");
//        setLayout(null);
//        setLocationRelativeTo(null);
//        setSize(500,400);
//        setVisible(true);
//
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

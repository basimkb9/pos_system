package org.possystem.ui;

import javax.swing.*;
import java.awt.*;

public class NavUI {
    public NavUI(String role){
        JFrame navigationScreen = new JFrame("Welcome | POS System");
        navigationScreen.setLayout(new BorderLayout());
        navigationScreen.setBackground(Color.darkGray);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //Heading
        JLabel welcomeText = new JLabel();
        welcomeText.setText("Welcome to Point of Sale System");
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 30));
        welcomeText.setForeground(Color.BLACK);
        labelPanel.add(welcomeText);

        navigationScreen.add(labelPanel,BorderLayout.NORTH);

        //Navigation Buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        int buttonWidth = 150;
        JButton mainScreenBtn = new JButton("Home");
        JButton productsBtn = new JButton("Products");
        JButton categoryBtn = new JButton("Categories");
        JButton transactionsBtn = new JButton("Transactions");
        JButton saleDetailsBtn = new JButton("Sale Details");
        JButton logoutBtn = new JButton("Logout");

        // Set preferred width for each button
        mainScreenBtn.setPreferredSize(new Dimension(150,150));
        Dimension buttonDimension = new Dimension(buttonWidth, mainScreenBtn.getPreferredSize().height);
        mainScreenBtn.setPreferredSize(buttonDimension);
        logoutBtn.setPreferredSize(buttonDimension);
        productsBtn.setPreferredSize(buttonDimension);
        categoryBtn.setPreferredSize(buttonDimension);
        transactionsBtn.setPreferredSize(buttonDimension);
        saleDetailsBtn.setPreferredSize(buttonDimension);

        mainPanel.add(mainScreenBtn);
        if(role.equalsIgnoreCase("administrator")){
            mainPanel.add(productsBtn);
            mainPanel.add(categoryBtn);
            mainPanel.add(transactionsBtn);
            mainPanel.add(saleDetailsBtn);

        }
        mainPanel.add(logoutBtn);


        mainScreenBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new MainScreenUI(role);
        });
        logoutBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new LoginUI();
        });
        productsBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new ProductUI();
        });
        categoryBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new CategoryUI();
        });
        transactionsBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new TransactionsUI();
        });
        saleDetailsBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new SaleDetailsUI();
        });

        navigationScreen.add(mainPanel);
        navigationScreen.pack();
        navigationScreen.setVisible(true);
        navigationScreen.setSize(800,500);
        navigationScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        navigationScreen.setLocationRelativeTo(null);
    }

    public void salesmanUI(JFrame navigationScreen) {
        //Navigation Buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        int buttonWidth = 150;
        JButton mainScreenBtn = new JButton("Home");
        JButton logoutBtn = new JButton("Logout");
        mainScreenBtn.setPreferredSize(new Dimension(150,0));
        Dimension buttonDimension = new Dimension(buttonWidth, mainScreenBtn.getPreferredSize().height);
        mainPanel.add(mainScreenBtn);
        mainPanel.add(logoutBtn);

        // Set preferred width for each button
        mainScreenBtn.setPreferredSize(buttonDimension);
        logoutBtn.setPreferredSize(buttonDimension);

        mainScreenBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new MainScreenUI("salesman");
        });
        logoutBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new LoginUI();
        });


        JButton productsBtn = new JButton("Products");
        JButton categoryBtn = new JButton("Categories");
        JButton transactionsBtn = new JButton("Transactions");
        JButton saleDetailsBtn = new JButton("Sale Details");

        mainPanel.add(productsBtn);
        mainPanel.add(categoryBtn);
        mainPanel.add(transactionsBtn);
        mainPanel.add(saleDetailsBtn);

        productsBtn.setPreferredSize(buttonDimension);
        categoryBtn.setPreferredSize(buttonDimension);
        transactionsBtn.setPreferredSize(buttonDimension);
        saleDetailsBtn.setPreferredSize(buttonDimension);


        productsBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new ProductUI();
        });
        categoryBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new CategoryUI();
        });
        transactionsBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new TransactionsUI();
        });
        saleDetailsBtn.addActionListener(e -> {
            navigationScreen.dispose();
            new SaleDetailsUI();
        });

        navigationScreen.add(mainPanel);
        navigationScreen.pack();
        navigationScreen.setVisible(true);
        navigationScreen.setSize(800,500);
        navigationScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        navigationScreen.setLocationRelativeTo(null);

    }

    public void adminUI(JFrame navigationScreen) {

    }

}
